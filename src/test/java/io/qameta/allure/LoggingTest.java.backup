package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

@ExtendWith(AllureJunit5.class)
@Layer("rest")
@Owner("logging-specialist")
@Feature("Logging System")
@TM4J("TM4J-131")
@Microservice("logging-service")
@Story("System logging functionality")
@Tag("logging") @Tag("monitoring") @Tag("rest")
public class LoggingTest {

    private RestSteps steps = new RestSteps();

    @Test
    @DisplayName("Test logging system initialization")
    @Story("Logging initialization")
    @JiraIssues({@JiraIssue("JIRA-464")})
    public void shouldTestLoggingSystemInitialization() {
        steps.createIssueWithTitle("testuser", "testrepo", "Logging System Initialization Test");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Logging System Initialization Test");
    }

    @ParameterizedTest
    @ValueSource(strings = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"})
    @DisplayName("Test different log levels")
    @Story("Log levels")
    public void shouldTestDifferentLogLevels(String level) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Level: " + level);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Level: " + level);
    }

    @ParameterizedTest
    @CsvSource({"Application, app.log", "Access, access.log", "Error, error.log", "Security, security.log"})
    @DisplayName("Test different log categories")
    @Story("Log categories")
    public void shouldTestDifferentLogCategories(String category, String filename) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Category: " + category + " -> " + filename);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Category: " + category + " -> " + filename);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Console", "File", "Database", "Syslog", "Remote Server"})
    @DisplayName("Test different log destinations")
    @Story("Log destinations")
    public void shouldTestDifferentLogDestinations(String destination) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Destination: " + destination);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Destination: " + destination);
    }

    @ParameterizedTest
    @ValueSource(strings = {"JSON", "XML", "Plain Text", "Structured", "Binary"})
    @DisplayName("Test different log formats")
    @Story("Log formats")
    public void shouldTestDifferentLogFormats(String format) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Format: " + format);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Format: " + format);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Timestamp", "Log Level", "Source", "Message", "Stack Trace"})
    @DisplayName("Test different log fields")
    @Story("Log fields")
    public void shouldTestDifferentLogFields(String field) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Field: " + field);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Field: " + field);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rotation", "Compression", "Archiving", "Cleanup", "Retention"})
    @DisplayName("Test different log management features")
    @Story("Log management")
    public void shouldTestDifferentLogManagementFeatures(String feature) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Management: " + feature);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Management: " + feature);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Real-time", "Batch", "Scheduled", "On-demand", "Event-driven"})
    @DisplayName("Test different log processing modes")
    @Story("Log processing")
    public void shouldTestDifferentLogProcessingModes(String mode) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Processing Mode: " + mode);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Processing Mode: " + mode);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Filtering", "Searching", "Aggregation", "Analysis", "Reporting"})
    @DisplayName("Test different log analysis features")
    @Story("Log analysis")
    public void shouldTestDifferentLogAnalysisFeatures(String feature) {
        steps.createIssueWithTitle("testuser", "testrepo", "Log Analysis: " + feature);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Analysis: " + feature);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Performance", "Security", "Compliance", "Audit", "Debugging"})
    @DisplayName("Test different logging use cases")
    @Story("Logging use cases")
    public void shouldTestDifferentLoggingUseCases(String useCase) {
        steps.createIssueWithTitle("testuser", "testrepo", "Logging Use Case: " + useCase);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Logging Use Case: " + useCase);
    }
}
