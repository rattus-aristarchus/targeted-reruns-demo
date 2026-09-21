#!/usr/bin/env python3
"""Attempt-aware targeted test rerun wrapper for GitHub Actions.

Plans which Test tasks/classes need to run for this attempt of the job, runs
Gradle, records the outcome into a cumulative state.json, writes a job
summary, and exits with Gradle's exit code.

Uses only the Python 3 standard library.
"""
from __future__ import annotations

import argparse
import json
import os
import re
import signal
import subprocess
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

SCHEMA = 1
SCRIPT_DIR = Path(__file__).resolve().parent
INIT_SCRIPT = SCRIPT_DIR / "outcomes.init.gradle.kts"

SUCCESSFUL_OUTCOMES = {"SUCCESS", "UP_TO_DATE", "FROM_CACHE", "NO_SOURCE", "SKIPPED"}

_IDENTIFIER = r"[A-Za-z_$][A-Za-z0-9_$]*"
_CLASSNAME_RE = re.compile(rf"^{_IDENTIFIER}(\.{_IDENTIFIER})*$")


def is_valid_classname(name: str) -> bool:
    return bool(name) and bool(_CLASSNAME_RE.match(name))


def top_level_class(classname: str) -> str:
    return classname.split("$", 1)[0]


# --------------------------------------------------------------------------
# Reading previous attempts' state
# --------------------------------------------------------------------------

def find_state_files(previous_dir: Path):
    if not previous_dir.is_dir():
        return []
    return sorted(previous_dir.rglob("state.json"))


def load_previous_states(previous_dir: Path, current_attempt: int, sha: str) -> dict:
    """Return {attempt_number: state_dict} for every earlier attempt of this
    sha with a parseable, schema-matching state.json. Invalid/foreign/future
    states are silently skipped (the safety rule: fall back to full)."""
    states = {}
    for state_path in find_state_files(previous_dir):
        try:
            data = json.loads(state_path.read_text())
        except (OSError, UnicodeDecodeError, json.JSONDecodeError):
            continue
        if not isinstance(data, dict):
            continue
        if data.get("schema") != SCHEMA:
            continue
        if data.get("sha") != sha:
            continue
        attempt = data.get("attempt")
        if not isinstance(attempt, int) or attempt >= current_attempt:
            continue
        tasks = data.get("tasks")
        if not isinstance(tasks, dict):
            continue
        # Keep the highest attempt number if the same attempt appears twice
        # (e.g. duplicate artifacts) - later one wins by sort order below.
        if attempt not in states or True:
            states[attempt] = data
    return states


# --------------------------------------------------------------------------
# Planning
# --------------------------------------------------------------------------

def plan_mode(attempt: int, targeted_rerun_enabled: bool, previous_states: dict):
    """Returns (mode, based_on_attempt, base_state)."""
    if attempt == 1 or not targeted_rerun_enabled:
        return "full", None, None
    if not previous_states:
        return "full", None, None
    based_on = max(previous_states)
    base_state = previous_states[based_on]
    if base_state.get("fullRunIncomplete"):
        return "full", based_on, base_state
    tasks = base_state.get("tasks", {})
    if all(info.get("status") == "passed" for info in tasks.values()):
        return "full", based_on, base_state
    return "targeted", based_on, base_state


def build_targeted_plan(base_tasks: dict):
    """Returns {task_path: classes_or_None}, skipping tasks already passed.
    None means 'run the task without --tests filters'."""
    plan = {}
    for path, info in base_tasks.items():
        status = info.get("status")
        if status == "passed":
            continue
        elif status == "pending":
            plan[path] = list(info.get("classes", []))
        else:  # unknown, or any unrecognized status - run in full, safest
            plan[path] = None
    return plan


# --------------------------------------------------------------------------
# Building the Gradle command
# --------------------------------------------------------------------------

def build_gradle_argv(gradle_cmd, mode, tasks_full, targeted_plan, outcomes_file):
    argv = list(gradle_cmd)
    argv += ["--continue", "--init-script", str(INIT_SCRIPT),
             f"-DtargetedRerun.outcomesFile={outcomes_file}"]
    if mode == "full":
        argv += list(tasks_full)
    else:
        for path, classes in targeted_plan.items():
            argv.append(path)
            if classes:
                for cls in classes:
                    argv += ["--tests", cls]
    return argv


# --------------------------------------------------------------------------
# Reading the init script's output and JUnit XML
# --------------------------------------------------------------------------

def read_outcomes(outcomes_file: Path) -> dict:
    try:
        data = json.loads(outcomes_file.read_text())
    except (OSError, UnicodeDecodeError, json.JSONDecodeError):
        return {}
    tasks = data.get("tasks")
    return tasks if isinstance(tasks, dict) else {}


def scan_report_dir(reports_dir: str):
    """Parse every TEST-*.xml in reports_dir.

    Returns (per_top_class_failed, invalid):
      per_top_class_failed: {top_level_classname: bool_has_failure_or_error}
      invalid: True if any classname was not a valid Java class name, or any
               XML file failed to parse.
    """
    per_class_failed = {}
    invalid = False
    if not reports_dir:
        return per_class_failed, invalid
    reports_path = Path(reports_dir)
    if not reports_path.is_dir():
        return per_class_failed, invalid
    for xml_file in sorted(reports_path.glob("TEST-*.xml")):
        try:
            root = ET.parse(xml_file).getroot()
        except ET.ParseError:
            invalid = True
            continue
        testcases = root.findall(".//testcase")
        for testcase in testcases:
            classname = testcase.get("classname")
            if not classname or not is_valid_classname(classname):
                invalid = True
                continue
            top = top_level_class(classname)
            failed = testcase.find("failure") is not None or testcase.find("error") is not None
            per_class_failed[top] = per_class_failed.get(top, False) or failed
    return per_class_failed, invalid


def classes_still_failing(reports_dir: str, candidate_classes):
    """Of candidate_classes, return the subset that still have a failure or
    error in this attempt's XML, or that have no trustworthy result at all
    (missing, or the report directory is invalid) - both count as 'keep
    pending', per the safety rule."""
    per_class_failed, invalid = scan_report_dir(reports_dir)
    if invalid:
        return set(candidate_classes)
    still_failing = set()
    for cls in candidate_classes:
        if per_class_failed.get(cls, True):  # not present -> no clean result -> keep
            still_failing.add(cls)
    return still_failing


# --------------------------------------------------------------------------
# Recording
# --------------------------------------------------------------------------

def record_full_task_failure(reports_dir: str):
    """A Test task failed because of test failures, in a full (unfiltered)
    run of that task. Returns a status dict."""
    per_class_failed, invalid = scan_report_dir(reports_dir)
    if invalid:
        return {"status": "unknown"}
    failing = sorted(cls for cls, failed in per_class_failed.items() if failed)
    if not failing:
        return {"status": "unknown"}
    return {"status": "pending", "classes": failing}


def record_tasks(mode: str, outcomes: dict, base_tasks: dict, targeted_plan: dict) -> dict:
    new_tasks = {}
    all_paths = set(base_tasks.keys()) | {
        path for path, info in outcomes.items() if info.get("isTest")
    }
    for path in all_paths:
        info = outcomes.get(path)
        prior = base_tasks.get(path, {"status": "unknown"})
        ran = info is not None and info.get("outcome") != "NOT_RUN"
        if not ran:
            new_tasks[path] = prior
            continue

        outcome = info.get("outcome")
        if outcome in SUCCESSFUL_OUTCOMES:
            new_tasks[path] = {"status": "passed"}
            continue

        # outcome == FAILED (or unrecognized -> treated as failed/unknown below)
        test_failure = bool(info.get("testFailure"))
        reports_dir = info.get("reportsDir")

        if mode == "full":
            if test_failure:
                new_tasks[path] = record_full_task_failure(reports_dir)
            else:
                new_tasks[path] = {"status": "unknown"}
            continue

        # mode == "targeted"
        requested = targeted_plan.get(path)
        if not test_failure:
            new_tasks[path] = prior
            continue
        if requested is None:
            # Was "unknown", reran in full this attempt - same rule as a full run.
            new_tasks[path] = record_full_task_failure(reports_dir)
        else:
            # Was "pending" with `requested` classes.
            remaining = sorted(classes_still_failing(reports_dir, requested))
            new_tasks[path] = (
                {"status": "pending", "classes": remaining} if remaining
                else {"status": "passed"}
            )
    return new_tasks


def compute_full_run_incomplete(outcomes: dict) -> bool:
    for info in outcomes.values():
        if info.get("isTest"):
            continue
        if info.get("outcome") in (None, "FAILED", "NOT_RUN", "OTHER"):
            return True
    return False


def build_state(sha, attempt, mode, based_on, full_run_incomplete, tasks) -> dict:
    return {
        "schema": SCHEMA,
        "sha": sha,
        "attempt": attempt,
        "mode": mode,
        "basedOnAttempt": based_on,
        "fullRunIncomplete": full_run_incomplete,
        "tasks": tasks,
    }


# --------------------------------------------------------------------------
# Summary
# --------------------------------------------------------------------------

def render_summary(attempt, mode, based_on, tasks, targeted_plan, base_tasks) -> str:
    lines = []
    if mode == "full":
        if based_on is None:
            lines.append(f"Attempt {attempt}: full test run.")
        else:
            lines.append(f"Attempt {attempt}: full, based on attempt {based_on} "
                          "(no usable partial result - see below).")
    else:
        lines.append(f"Attempt {attempt}: targeted, based on attempt {based_on}.")

    if mode == "targeted":
        passed_earlier = [p for p, info in base_tasks.items() if info.get("status") == "passed"]
        if passed_earlier:
            lines.append(f"Skipped (passed earlier): {len(passed_earlier)} test task(s): "
                          + ", ".join(sorted(passed_earlier)))
        for path, classes in sorted(targeted_plan.items()):
            if classes is None:
                lines.append(f"Ran in full: `{path}` (no reliable result from attempt {based_on})")
            else:
                lines.append(f"Re-ran: `{path}`, {len(classes)} class(es): "
                              + ", ".join(classes))
    else:
        lines.append(f"Ran {len(tasks)} test task(s) in full: " + ", ".join(sorted(tasks)))

    return "\n".join(lines)


def write_step_summary(text: str):
    summary_path = os.environ.get("GITHUB_STEP_SUMMARY")
    if summary_path:
        with open(summary_path, "a", encoding="utf-8") as fh:
            fh.write(text + "\n")


def write_outputs(mode: str, based_on):
    output_path = os.environ.get("GITHUB_OUTPUT")
    if not output_path:
        return
    with open(output_path, "a", encoding="utf-8") as fh:
        fh.write(f"mode={mode}\n")
        fh.write(f"based_on_attempt={based_on if based_on is not None else ''}\n")


# --------------------------------------------------------------------------
# Running Gradle, forwarding cancellation signals
# --------------------------------------------------------------------------

def run_gradle(argv):
    process = subprocess.Popen(argv)
    received = {"signum": None}

    def forward(signum, _frame):
        received["signum"] = signum
        try:
            process.send_signal(signum)
        except ProcessLookupError:
            pass

    previous_handlers = {
        sig: signal.signal(sig, forward) for sig in (signal.SIGTERM, signal.SIGINT)
    }
    try:
        returncode = process.wait()
    finally:
        for sig, handler in previous_handlers.items():
            signal.signal(sig, handler)
    return returncode, received["signum"]


# --------------------------------------------------------------------------
# CLI
# --------------------------------------------------------------------------

def parse_args(argv):
    if "--" in argv:
        split = argv.index("--")
        own_args, gradle_cmd = argv[:split], argv[split + 1:]
    else:
        own_args, gradle_cmd = argv, []

    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--previous", required=True, type=Path,
                         help="Directory containing downloaded state artifacts from earlier attempts")
    parser.add_argument("--out", required=True, type=Path,
                         help="Directory to write this attempt's state.json into")
    parser.add_argument("--tasks", nargs="+", required=True,
                         help="Task names to run in full mode, e.g. clean test")
    args = parser.parse_args(own_args)
    if not gradle_cmd:
        parser.error("expected the Gradle command after '--', e.g. -- ./gradlew")
    return args, gradle_cmd


def main(argv=None) -> int:
    args, gradle_cmd = parse_args(sys.argv[1:] if argv is None else argv)

    sha = os.environ.get("GITHUB_SHA")
    if not sha:
        print("run_tests.py: GITHUB_SHA is not set", file=sys.stderr)
        return 2
    attempt = int(os.environ.get("GITHUB_RUN_ATTEMPT", "1"))
    targeted_rerun_enabled = os.environ.get("TARGETED_RERUN", "true").lower() != "false"

    previous_states = load_previous_states(args.previous, attempt, sha)
    mode, based_on, base_state = plan_mode(attempt, targeted_rerun_enabled, previous_states)
    base_tasks = base_state.get("tasks", {}) if base_state else {}
    targeted_plan = build_targeted_plan(base_tasks) if mode == "targeted" else {}

    args.out.mkdir(parents=True, exist_ok=True)
    outcomes_file = args.out / "gradle-outcomes.json"
    gradle_argv = build_gradle_argv(gradle_cmd, mode, args.tasks, targeted_plan, outcomes_file)

    print(f"Attempt {attempt}: mode={mode} based_on={based_on}")
    print("Gradle command:", " ".join(gradle_argv))

    returncode, signum = run_gradle(gradle_argv)

    if signum is not None:
        print(f"run_tests.py: received signal {signum}, not writing state.json", file=sys.stderr)
        return returncode

    outcomes = read_outcomes(outcomes_file)
    full_run_incomplete = (
        compute_full_run_incomplete(outcomes) if mode == "full"
        else bool(base_state.get("fullRunIncomplete")) if base_state else False
    )
    new_tasks = record_tasks(mode, outcomes, base_tasks, targeted_plan)
    state = build_state(sha, attempt, mode, based_on, full_run_incomplete, new_tasks)

    state_path = args.out / "state.json"
    state_path.write_text(json.dumps(state, indent=2, sort_keys=True) + "\n")

    summary = render_summary(attempt, mode, based_on, new_tasks, targeted_plan, base_tasks)
    print(summary)
    write_step_summary(summary)
    write_outputs(mode, based_on)

    return returncode


if __name__ == "__main__":
    sys.exit(main())
