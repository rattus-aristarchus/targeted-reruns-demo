package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(AllureJunit5.class)
@Layer("web")
@Owner("report-manager")
@Feature("Reporting System")
@TM4J("TM4J-124")
@Microservice("reporting-service")
@Story("Report generation functionality")
@Tag("reporting") @Tag("analytics") @Tag("web")
public class ReportingTest {

    private WebSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new WebSteps();
    }

    @Test
    @DisplayName("Generate sales report")
    @Story("Sales reporting")
    @JiraIssues({@JiraIssue("JIRA-457")})
    public void shouldGenerateSalesReport() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Sales Report Generation");
        steps.shouldSeeIssueWithTitle("Sales Report Generation");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Daily", "Weekly", "Monthly", "Quarterly", "Yearly"})
    @DisplayName("Generate reports for different time periods")
    @Story("Time-based reporting")
    public void shouldGenerateTimeBasedReports(String period) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Time Report: " + period);
        steps.shouldSeeIssueWithTitle("Time Report: " + period);
    }

    @ParameterizedTest
    @CsvSource({"Sales, Revenue", "Inventory, Stock", "Customer, Satisfaction", "Product, Performance"})
    @DisplayName("Generate different report types")
    @Story("Report type generation")
    public void shouldGenerateDifferentReportTypes(String reportType, String metric) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Report Type: " + reportType + " - " + metric);
        steps.shouldSeeIssueWithTitle("Report Type: " + reportType + " - " + metric);
    }

    @ParameterizedTest
    @ValueSource(strings = {"PDF", "Excel", "CSV", "HTML", "JSON"})
    @DisplayName("Export reports in different formats")
    @Story("Report export formats")
    public void shouldExportReportsInDifferentFormats(String format) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Export Format: " + format);
        steps.shouldSeeIssueWithTitle("Export Format: " + format);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chart", "Table", "Graph", "Dashboard", "Summary"})
    @DisplayName("Generate different visualization types")
    @Story("Report visualization")
    public void shouldGenerateDifferentVisualizationTypes(String visualization) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Visualization: " + visualization);
        steps.shouldSeeIssueWithTitle("Visualization: " + visualization);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Admin", "Manager", "Analyst", "Viewer", "Guest"})
    @DisplayName("Generate reports for different user roles")
    @Story("Role-based reporting")
    public void shouldGenerateReportsForDifferentUserRoles(String role) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("User Role: " + role);
        steps.shouldSeeIssueWithTitle("User Role: " + role);
    }

    @ParameterizedTest
    @ValueSource(strings = {"North", "South", "East", "West", "Central"})
    @DisplayName("Generate regional reports")
    @Story("Regional reporting")
    public void shouldGenerateRegionalReports(String region) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Region: " + region);
        steps.shouldSeeIssueWithTitle("Region: " + region);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Real-time", "Scheduled", "On-demand", "Batch", "Streaming"})
    @DisplayName("Generate reports with different delivery methods")
    @Story("Report delivery methods")
    public void shouldGenerateReportsWithDifferentDeliveryMethods(String method) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Delivery Method: " + method);
        steps.shouldSeeIssueWithTitle("Delivery Method: " + method);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Low", "Medium", "High", "Critical", "Info"})
    @DisplayName("Generate reports with different priority levels")
    @Story("Report priority levels")
    public void shouldGenerateReportsWithDifferentPriorityLevels(String priority) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Priority: " + priority);
        steps.shouldSeeIssueWithTitle("Priority: " + priority);
    }

    @ParameterizedTest
    @ValueSource(strings = {"English", "Spanish", "French", "German", "Chinese"})
    @DisplayName("Generate reports in different languages")
    @Story("Multi-language reporting")
    public void shouldGenerateReportsInDifferentLanguages(String language) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Language: " + language);
        steps.shouldSeeIssueWithTitle("Language: " + language);
    }
}
