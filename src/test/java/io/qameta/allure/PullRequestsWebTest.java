package io.qameta.allure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
@Layer("web")
@Owner("eroshenkoam")
@Feature("Pull Requests")
public class PullRequestsWebTest {

    private static final String OWNER = "allure-framework, not-allure-framework";
    private static final String REPO = "allure2";

    private static final String BRANCH = "new-feature";

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("AE-T6")
    @Microservice("Billing")
    @Story("Create new pull request")
    @Tags({@Tag("web"), @Tag("regress4"), @Tag("smoke")})
    @JiraIssues({@JiraIssue("AE-1"), @JiraIssue("AE-2")})
    @DisplayName("Creating new issue for authorized user")
    public void shouldCreatePullRequest() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Pull Request Configuration (JSON)
        String prConfig = "{\n" +
                "  \"prId\": \"AE-T6\",\n" +
                "  \"branch\": \"" + BRANCH + "\",\n" +
                "  \"owner\": \"" + OWNER + "\",\n" +
                "  \"repo\": \"" + REPO + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"open\"\n" +
                "}";
        
        // Pull Request Report (HTML)
        String prReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Pull Request Report</title></head>\n" +
                "<body><h1>Pull Request Management Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>PR Details</h2>\n" +
                "<ul>\n" +
                "<li>Branch: " + BRANCH + "</li>\n" +
                "<li>Repository: " + OWNER + "/" + REPO + "</li>\n" +
                "<li>Status: Open</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // PR Data (CSV)
        String prData = "pr_id,branch,owner,repo,status,timestamp\n" +
                "AE-T6," + BRANCH + "," + OWNER + "," + REPO + ",open," + timestamp;
        
        // PR Log (Plain Text)
        String prLog = "PULL REQUEST LOG\n" +
                "================\n\n" +
                "PR ID: AE-T6\n" +
                "Branch: " + BRANCH + "\n" +
                "Repository: " + OWNER + "/" + REPO + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Open\n" +
                "Action: Created successfully";
        
        Allure.attachment("PR Configuration (JSON)", prConfig);
        Allure.attachment("PR Report (HTML)", prReport);
        Allure.attachment("PR Data (CSV)", prData);
        Allure.attachment("PR Log (Text)", prLog);
        
        steps.openPullRequestsPage(OWNER, REPO);
        steps.createPullRequestFromBranch(BRANCH);
        steps.shouldSeePullRequestForBranch(BRANCH);
    }

    @Test
    @TM4J("AE-T7")
    @JiraIssue("AE-2")
    @Microservice("Repository")
    @Story("Close existing pull request")
    @Tags({@Tag("web"), @Tag("regress4")})
    @DisplayName("Deleting existing issue for authorized user")
    public void shouldClosePullRequest() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Close PR Report (HTML)
        String closeReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Close PR Report</title></head>\n" +
                "<body><h1>Pull Request Close Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Close Details</h2>\n" +
                "<ul>\n" +
                "<li>Branch: " + BRANCH + "</li>\n" +
                "<li>Action: Closed</li>\n" +
                "<li>Status: Completed</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Close Metrics (CSV)
        String closeMetrics = "action,branch,status,timestamp\n" +
                "close," + BRANCH + ",completed," + timestamp;
        
        Allure.attachment("Close Report (HTML)", closeReport);
        Allure.attachment("Close Metrics (CSV)", closeMetrics);
        Allure.attachment("Close Timestamp", timestamp);
        
        steps.openPullRequestsPage(OWNER, REPO);
        steps.createPullRequestFromBranch(BRANCH);
        steps.closePullRequestForBranch(BRANCH);
        steps.shouldNotSeePullRequestForBranch(BRANCH);
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }

}