# Attempt-aware targeted test reruns

`tests-with-reruns.yml` reruns only the tests that haven't passed yet when you
click **"Re-run failed jobs"** on a failed run, instead of rerunning the whole
suite. It works entirely through GitHub's native re-run: same run ID, same
commit, same event — no `workflow_dispatch` inputs, no separate retry
workflow.

The original `run-tests.yml` and `run-single-test.yml` workflows are
untouched and keep running as before; `tests-with-reruns.yml` is an
additional, independent workflow.

## How it works

Each attempt of the job runs `ci/targeted-rerun/run_tests.py`, which:

1. **Plans.** Downloads `state.json` written by earlier attempts of this same
   run (via `actions/download-artifact`, falling back to `gh run download` if
   the primary download can't see them — see "Fallback for artifact
   visibility" below). Based on the most recent usable one, decides whether
   this attempt should be a **full** run or a **targeted** one (see "Decision
   rules").
2. **Runs Gradle**, either with the normal task list (full) or with the
   specific `Test` task paths and `--tests <class>` filters computed from the
   plan (targeted), always with `--continue` so one failing task doesn't
   prevent others from running. A CI-only Gradle init script,
   `ci/targeted-rerun/outcomes.init.gradle.kts`, records each task's outcome
   and (for `Test` tasks) its JUnit XML report directory, using Gradle's
   build-events API. It does not change build behavior and is compatible
   with the configuration cache.
3. **Records.** Reads the JUnit XML for any `Test` task that failed because of
   test failures, and writes a new, cumulative `state.json` describing every
   `Test` task's status: `passed`, `pending` (with the still-failing classes),
   or `unknown` (no trustworthy result — the task must run in full next time).
4. **Writes a summary** of what ran and why to the job log and
   `$GITHUB_STEP_SUMMARY`, and exits with Gradle's exit code.

`state.json` and the JUnit XML from each attempt are uploaded as an artifact
named `test-state-<job>-attempt-<N>`, which stays available to later attempts
of the same run.

## Decision rules

```
if this is attempt 1, or TARGETED_RERUN is "false":            -> FULL
look at state.json from earlier attempts of this run/commit;
pick the newest one that parses, matches schema 1 and this sha  (none -> FULL)
if that state says a full run didn't fully complete:            -> FULL
if every task in it already passed:                             -> FULL  (a deliberate re-run, e.g. "Re-run all jobs")
otherwise, TARGETED:
    passed  tasks -> skipped
    pending tasks -> rerun with --tests <class> for each still-pending class
    unknown tasks -> rerun with no filter (the whole task)
```

**Safety rule:** a later attempt only skips a test if it passed in an earlier
attempt of the *same run*, on the *same commit*. Anything missing, unreadable,
inconsistent, or unexpected falls back to a full run. Running more than
necessary is always fine; running less is a bug.

The unit of selection is a **test class within a specific `Test` task**, not
an individual method — JUnit `@DisplayName` and parameterized tests make
method names in the XML unreliable, so a class is the smallest thing this
system reruns on its own.

## Changes made to the first attempt

Compared to `run-tests.yml`, this workflow's first attempt differs in three
ways, all pre-approved as part of building this feature:

- **`--continue`** is always passed, so one failing task doesn't stop the
  rest of the task graph (needed so a later attempt gets trustworthy
  per-task results instead of a wall of `unknown`).
- **`ignoreFailures` is `false`** for `Test` tasks (it was `true`), so the
  `test` task — and the job — actually fails when tests fail. Without this,
  the job stays green regardless of test outcomes and "Re-run failed jobs"
  never has anything to react to.
- **Allure report generation and the GitHub Pages publish step are skipped
  on targeted attempts.** A targeted attempt only regenerates
  `build/allure-results` for the classes it reran; the ones skipped because
  they already passed in an earlier attempt have no fresh Allure results in
  that attempt's runner, so publishing from a targeted attempt would produce
  an incomplete report. The condition is based on the `mode` step output,
  not the attempt number, because attempt 2 can still be a full run (e.g.
  after "Re-run all jobs", or if the previous state was unusable).

## Fallback for artifact visibility

`actions/download-artifact@v4` is tried first, with `continue-on-error:
true`. If it finds nothing (no `state.json` under `rerun-state/previous`),
a fallback step calls `gh run download "$GITHUB_RUN_ID" --pattern
"test-state-<job>-attempt-*"`, which needs the `actions: read` permission
(granted on this job). Which path actually served a given run is visible in
that run's logs.

## Disabling it

- **For one run:** re-run with `TARGETED_RERUN=false` — every attempt of
  that run becomes a full run from then on. This is read fresh on every
  attempt, so flipping the repository variable and clicking "Re-run failed
  jobs" again picks up the change on an already-started run; you don't need
  a new commit.
- **Permanently:** set the repository/environment variable `TARGETED_RERUN`
  to `false`, or remove the `tests-with-reruns.yml` workflow.

## Known limitations

- **Class-level granularity.** A failed test method reruns its whole class.
- **"Re-run all jobs" looks the same as "Re-run failed jobs" from inside the
  job.** Both just start a new attempt with the same inputs, so a "Re-run
  all jobs" after a *failed* attempt still narrows to whatever's still
  pending. To force a genuinely full run, set `TARGETED_RERUN=false` or push
  a new commit. (A "Re-run all jobs" after every task already *passed* does
  correctly produce a full run — see the decision rules above.)
- **Non-test tasks that depend on test results would force every retry to be
  full.** This repository doesn't have any (verified via `./gradlew clean
  test -m`), but if one is ever added (e.g. `jacocoTestCoverageVerification`),
  the targeted mode would need to be revisited first.
- **Artifact retention.** If a run's state artifacts expire before you
  re-run it (retention is set to 14 days here, comfortably inside GitHub's
  30-day re-run window, but a repository-wide artifact policy could
  override it), the next attempt falls back to a full run.
