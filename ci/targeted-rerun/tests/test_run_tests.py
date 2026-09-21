import json
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

import run_tests  # noqa: E402

FIXTURES = Path(__file__).resolve().parent / "fixtures"
SHA = "a" * 40


def write_state(dir_path: Path, artifact_name: str, state: dict):
    target = dir_path / artifact_name / "rerun-state" / "current"
    target.mkdir(parents=True, exist_ok=True)
    (target / "state.json").write_text(json.dumps(state))


class LoadPreviousStatesTest(unittest.TestCase):
    def test_missing_directory_returns_empty(self):
        self.assertEqual(run_tests.load_previous_states(Path("/no/such/dir"), 2, SHA), {})

    def test_no_artifacts_returns_empty(self, tmp_path=None):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            self.assertEqual(run_tests.load_previous_states(Path(d), 2, SHA), {})

    def test_corrupt_state_is_skipped(self):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            target = base / "art-1" / "rerun-state" / "current"
            target.mkdir(parents=True)
            (target / "state.json").write_text("{not json")
            self.assertEqual(run_tests.load_previous_states(base, 2, SHA), {})

    def test_wrong_sha_is_skipped(self):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            write_state(base, "art-1", {
                "schema": 1, "sha": "b" * 40, "attempt": 1,
                "mode": "full", "basedOnAttempt": None,
                "fullRunIncomplete": False, "tasks": {":test": {"status": "passed"}},
            })
            self.assertEqual(run_tests.load_previous_states(base, 2, SHA), {})

    def test_wrong_schema_is_skipped(self):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            write_state(base, "art-1", {
                "schema": 2, "sha": SHA, "attempt": 1,
                "mode": "full", "basedOnAttempt": None,
                "fullRunIncomplete": False, "tasks": {":test": {"status": "passed"}},
            })
            self.assertEqual(run_tests.load_previous_states(base, 2, SHA), {})

    def test_future_or_current_attempt_is_skipped(self):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            write_state(base, "art-2", {
                "schema": 1, "sha": SHA, "attempt": 2,
                "mode": "full", "basedOnAttempt": None,
                "fullRunIncomplete": False, "tasks": {},
            })
            self.assertEqual(run_tests.load_previous_states(base, 2, SHA), {})

    def test_valid_state_is_loaded(self):
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            state = {
                "schema": 1, "sha": SHA, "attempt": 1,
                "mode": "full", "basedOnAttempt": None,
                "fullRunIncomplete": False, "tasks": {":test": {"status": "passed"}},
            }
            write_state(base, "art-1", state)
            self.assertEqual(run_tests.load_previous_states(base, 2, SHA), {1: state})

    def test_killed_attempt_falls_back_to_most_recent_valid_state(self):
        # attempts 1 (valid, full) -> 2 (killed, no state.json) -> now planning 3
        import tempfile
        with tempfile.TemporaryDirectory() as d:
            base = Path(d)
            state1 = {
                "schema": 1, "sha": SHA, "attempt": 1,
                "mode": "full", "basedOnAttempt": None,
                "fullRunIncomplete": False,
                "tasks": {":test": {"status": "pending", "classes": ["com.acme.FooTest"]}},
            }
            write_state(base, "art-1", state1)
            # no artifact at all for attempt 2 (cancelled attempt wrote nothing)
            previous = run_tests.load_previous_states(base, 3, SHA)
            self.assertEqual(previous, {1: state1})
            mode, based_on, base_state = run_tests.plan_mode(3, True, previous)
            self.assertEqual(mode, "targeted")
            self.assertEqual(based_on, 1)
            self.assertEqual(base_state, state1)


class PlanModeTest(unittest.TestCase):
    def test_first_attempt_is_full(self):
        mode, based_on, base_state = run_tests.plan_mode(1, True, {1: {}})
        self.assertEqual((mode, based_on, base_state), ("full", None, None))

    def test_targeted_rerun_disabled_is_full(self):
        state = {"tasks": {":test": {"status": "pending", "classes": ["x"]}}}
        mode, based_on, base_state = run_tests.plan_mode(2, False, {1: state})
        self.assertEqual((mode, based_on, base_state), ("full", None, None))

    def test_no_previous_state_is_full(self):
        mode, based_on, base_state = run_tests.plan_mode(2, True, {})
        self.assertEqual((mode, based_on, base_state), ("full", None, None))

    def test_full_run_incomplete_forces_full(self):
        state = {"fullRunIncomplete": True, "tasks": {":test": {"status": "passed"}}}
        mode, based_on, base_state = run_tests.plan_mode(2, True, {1: state})
        self.assertEqual(mode, "full")
        self.assertEqual(based_on, 1)

    def test_every_task_passed_forces_full(self):
        state = {"fullRunIncomplete": False, "tasks": {
            ":a:test": {"status": "passed"}, ":b:test": {"status": "passed"},
        }}
        mode, based_on, base_state = run_tests.plan_mode(2, True, {1: state})
        self.assertEqual(mode, "full")

    def test_pending_task_triggers_targeted(self):
        state = {"fullRunIncomplete": False, "tasks": {
            ":a:test": {"status": "passed"},
            ":b:test": {"status": "pending", "classes": ["com.acme.FooTest"]},
        }}
        mode, based_on, base_state = run_tests.plan_mode(2, True, {1: state})
        self.assertEqual(mode, "targeted")

    def test_unknown_task_triggers_targeted(self):
        state = {"fullRunIncomplete": False, "tasks": {
            ":a:test": {"status": "passed"},
            ":b:test": {"status": "unknown"},
        }}
        mode, based_on, base_state = run_tests.plan_mode(2, True, {1: state})
        self.assertEqual(mode, "targeted")

    def test_picks_highest_valid_attempt(self):
        s1 = {"fullRunIncomplete": False, "tasks": {":a:test": {"status": "unknown"}}}
        s2 = {"fullRunIncomplete": False, "tasks": {":a:test": {"status": "pending", "classes": ["X"]}}}
        mode, based_on, base_state = run_tests.plan_mode(3, True, {1: s1, 2: s2})
        self.assertEqual(based_on, 2)
        self.assertEqual(base_state, s2)


class BuildTargetedPlanTest(unittest.TestCase):
    def test_skips_passed_filters_pending_runs_unknown_unfiltered(self):
        tasks = {
            ":a:test": {"status": "passed"},
            ":b:test": {"status": "pending", "classes": ["com.acme.FooTest"]},
            ":c:test": {"status": "unknown"},
        }
        plan = run_tests.build_targeted_plan(tasks)
        self.assertEqual(plan, {
            ":b:test": ["com.acme.FooTest"],
            ":c:test": None,
        })


class ClassnameValidationTest(unittest.TestCase):
    def test_valid_names(self):
        self.assertTrue(run_tests.is_valid_classname("com.acme.FooTest"))
        self.assertTrue(run_tests.is_valid_classname("com.acme.FooTest$Inner"))
        self.assertTrue(run_tests.is_valid_classname("FooTest"))

    def test_invalid_names(self):
        self.assertFalse(run_tests.is_valid_classname("Gradle Test Run :test"))
        self.assertFalse(run_tests.is_valid_classname("Gradle Test Executor 3"))
        self.assertFalse(run_tests.is_valid_classname(""))
        self.assertFalse(run_tests.is_valid_classname(None))

    def test_top_level_class_strips_nested_suffix(self):
        self.assertEqual(run_tests.top_level_class("com.acme.OuterTest$Inner"), "com.acme.OuterTest")
        self.assertEqual(run_tests.top_level_class("com.acme.OuterTest"), "com.acme.OuterTest")


class ScanReportDirTest(unittest.TestCase):
    def test_all_pass(self):
        per_class, invalid = run_tests.scan_report_dir(str(FIXTURES / "all_pass"))
        self.assertFalse(invalid)
        self.assertEqual(per_class, {"com.acme.FooTest": False})

    def test_some_fail(self):
        per_class, invalid = run_tests.scan_report_dir(str(FIXTURES / "some_fail"))
        self.assertFalse(invalid)
        self.assertEqual(per_class, {"com.acme.FooTest": True, "com.acme.BarTest": False})

    def test_nested_class_maps_to_top_level_and_marks_it_failed(self):
        per_class, invalid = run_tests.scan_report_dir(str(FIXTURES / "nested"))
        self.assertFalse(invalid)
        self.assertEqual(per_class, {"com.acme.OuterTest": True})

    def test_invalid_classname_flagged(self):
        per_class, invalid = run_tests.scan_report_dir(str(FIXTURES / "invalid"))
        self.assertTrue(invalid)

    def test_missing_directory_is_not_invalid_just_empty(self):
        per_class, invalid = run_tests.scan_report_dir(str(FIXTURES / "does_not_exist"))
        self.assertFalse(invalid)
        self.assertEqual(per_class, {})


class RecordFullTaskFailureTest(unittest.TestCase):
    def test_failing_classes_become_pending(self):
        result = run_tests.record_full_task_failure(str(FIXTURES / "some_fail"))
        self.assertEqual(result, {"status": "pending", "classes": ["com.acme.FooTest"]})

    def test_nested_failure_reports_top_level_class(self):
        result = run_tests.record_full_task_failure(str(FIXTURES / "nested"))
        self.assertEqual(result, {"status": "pending", "classes": ["com.acme.OuterTest"]})

    def test_invalid_classname_is_unknown(self):
        result = run_tests.record_full_task_failure(str(FIXTURES / "invalid"))
        self.assertEqual(result, {"status": "unknown"})

    def test_no_failures_found_is_unknown(self):
        result = run_tests.record_full_task_failure(str(FIXTURES / "all_pass"))
        self.assertEqual(result, {"status": "unknown"})


class RecordTasksFullModeTest(unittest.TestCase):
    def test_successful_outcome_is_passed(self):
        outcomes = {":test": {"isTest": True, "outcome": "SUCCESS", "testFailure": False, "reportsDir": None}}
        new_tasks = run_tests.record_tasks("full", outcomes, {}, {})
        self.assertEqual(new_tasks, {":test": {"status": "passed"}})

    def test_up_to_date_and_from_cache_are_passed(self):
        outcomes = {
            ":a:test": {"isTest": True, "outcome": "UP_TO_DATE", "testFailure": False, "reportsDir": None},
            ":b:test": {"isTest": True, "outcome": "FROM_CACHE", "testFailure": False, "reportsDir": None},
        }
        new_tasks = run_tests.record_tasks("full", outcomes, {}, {})
        self.assertEqual(new_tasks[":a:test"]["status"], "passed")
        self.assertEqual(new_tasks[":b:test"]["status"], "passed")

    def test_test_failure_extracts_pending_classes(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "FAILED", "testFailure": True,
            "reportsDir": str(FIXTURES / "some_fail"),
        }}
        new_tasks = run_tests.record_tasks("full", outcomes, {}, {})
        self.assertEqual(new_tasks[":test"], {"status": "pending", "classes": ["com.acme.FooTest"]})

    def test_non_test_failure_is_unknown(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "FAILED", "testFailure": False, "reportsDir": None,
        }}
        new_tasks = run_tests.record_tasks("full", outcomes, {}, {})
        self.assertEqual(new_tasks[":test"], {"status": "unknown"})

    def test_task_never_run_defaults_to_unknown_with_no_prior_state(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "NOT_RUN", "testFailure": False, "reportsDir": None,
        }}
        new_tasks = run_tests.record_tasks("full", outcomes, {}, {})
        self.assertEqual(new_tasks[":test"], {"status": "unknown"})

    def test_task_not_run_keeps_prior_status(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "NOT_RUN", "testFailure": False, "reportsDir": None,
        }}
        base_tasks = {":test": {"status": "pending", "classes": ["com.acme.FooTest"]}}
        new_tasks = run_tests.record_tasks("full", outcomes, base_tasks, {})
        self.assertEqual(new_tasks[":test"], base_tasks[":test"])


class RecordTasksTargetedModeTest(unittest.TestCase):
    def test_pending_task_some_classes_now_pass_others_dont(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "FAILED", "testFailure": True,
            "reportsDir": str(FIXTURES / "mixed_targeted"),
        }}
        base_tasks = {":test": {
            "status": "pending",
            "classes": ["com.acme.FooTest", "com.acme.BarTest"],
        }}
        targeted_plan = {":test": ["com.acme.FooTest", "com.acme.BarTest"]}
        new_tasks = run_tests.record_tasks("targeted", outcomes, base_tasks, targeted_plan)
        self.assertEqual(new_tasks[":test"], {"status": "pending", "classes": ["com.acme.BarTest"]})

    def test_pending_task_all_classes_now_pass_becomes_passed(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "SUCCESS", "testFailure": False,
            "reportsDir": str(FIXTURES / "all_pass"),
        }}
        base_tasks = {":test": {"status": "pending", "classes": ["com.acme.FooTest"]}}
        targeted_plan = {":test": ["com.acme.FooTest"]}
        new_tasks = run_tests.record_tasks("targeted", outcomes, base_tasks, targeted_plan)
        self.assertEqual(new_tasks[":test"], {"status": "passed"})

    def test_unknown_task_rerun_in_full_extracts_fresh_failures(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "FAILED", "testFailure": True,
            "reportsDir": str(FIXTURES / "some_fail"),
        }}
        base_tasks = {":test": {"status": "unknown"}}
        targeted_plan = {":test": None}
        new_tasks = run_tests.record_tasks("targeted", outcomes, base_tasks, targeted_plan)
        self.assertEqual(new_tasks[":test"], {"status": "pending", "classes": ["com.acme.FooTest"]})

    def test_failure_for_other_reason_keeps_prior_pending_unchanged(self):
        outcomes = {":test": {
            "isTest": True, "outcome": "FAILED", "testFailure": False,
            "reportsDir": str(FIXTURES / "mixed_targeted"),
        }}
        base_tasks = {":test": {"status": "pending", "classes": ["com.acme.FooTest", "com.acme.BarTest"]}}
        targeted_plan = {":test": ["com.acme.FooTest", "com.acme.BarTest"]}
        new_tasks = run_tests.record_tasks("targeted", outcomes, base_tasks, targeted_plan)
        self.assertEqual(new_tasks[":test"], base_tasks[":test"])

    def test_untouched_task_keeps_prior_status(self):
        outcomes = {}
        base_tasks = {
            ":a:test": {"status": "passed"},
            ":b:test": {"status": "pending", "classes": ["com.acme.FooTest"]},
        }
        new_tasks = run_tests.record_tasks("targeted", outcomes, base_tasks, {":b:test": ["com.acme.FooTest"]})
        self.assertEqual(new_tasks[":a:test"], {"status": "passed"})
        self.assertEqual(new_tasks[":b:test"], base_tasks[":b:test"])


class FullRunIncompleteTest(unittest.TestCase):
    def test_all_successful_is_complete(self):
        outcomes = {
            ":compileJava": {"isTest": False, "outcome": "UP_TO_DATE"},
            ":test": {"isTest": True, "outcome": "SUCCESS"},
        }
        self.assertFalse(run_tests.compute_full_run_incomplete(outcomes))

    def test_failed_non_test_task_is_incomplete(self):
        outcomes = {
            ":jacocoTestCoverageVerification": {"isTest": False, "outcome": "FAILED"},
            ":test": {"isTest": True, "outcome": "SUCCESS"},
        }
        self.assertTrue(run_tests.compute_full_run_incomplete(outcomes))

    def test_not_run_non_test_task_is_incomplete(self):
        outcomes = {
            ":report": {"isTest": False, "outcome": "NOT_RUN"},
            ":test": {"isTest": True, "outcome": "SUCCESS"},
        }
        self.assertTrue(run_tests.compute_full_run_incomplete(outcomes))

    def test_failed_test_task_itself_does_not_count(self):
        outcomes = {":test": {"isTest": True, "outcome": "FAILED"}}
        self.assertFalse(run_tests.compute_full_run_incomplete(outcomes))


class BuildGradleArgvTest(unittest.TestCase):
    def test_full_mode_uses_requested_tasks(self):
        argv = run_tests.build_gradle_argv(["./gradlew"], "full", ["clean", "test"], {}, "/tmp/out.json")
        self.assertEqual(argv[0], "./gradlew")
        self.assertIn("clean", argv)
        self.assertIn("test", argv)
        self.assertIn("--continue", argv)
        self.assertIn("--init-script", argv)
        self.assertNotIn("--tests", argv)

    def test_targeted_mode_builds_per_task_filters(self):
        plan = {":core:test": ["com.acme.FooTest"], ":api:test": None}
        argv = run_tests.build_gradle_argv(["./gradlew"], "targeted", [], plan, "/tmp/out.json")
        self.assertIn(":core:test", argv)
        self.assertIn(":api:test", argv)
        core_idx = argv.index(":core:test")
        self.assertEqual(argv[core_idx + 1:core_idx + 3], ["--tests", "com.acme.FooTest"])
        api_idx = argv.index(":api:test")
        self.assertNotIn("--tests", argv[api_idx:api_idx + 2])
        self.assertNotIn("clean", argv)


if __name__ == "__main__":
    unittest.main()
