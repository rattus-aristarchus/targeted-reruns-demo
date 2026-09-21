package io.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Temporary probe for verifying targeted-rerun behavior end to end. Skipped
 * unless RERUN_PROBE=1. Remove this file and the RERUN_PROBE wiring before
 * marking the PR ready - see docs/ci/targeted-reruns.md.
 */
@EnabledIfEnvironmentVariable(named = "RERUN_PROBE", matches = "1")
class RerunProbeTest {

    @Test
    void failsOnFirstAttemptOnly() {
        String attempt = System.getenv("GITHUB_RUN_ATTEMPT");
        assertNotEquals("1", attempt, "probe: intentionally failing on attempt 1");
    }
}
