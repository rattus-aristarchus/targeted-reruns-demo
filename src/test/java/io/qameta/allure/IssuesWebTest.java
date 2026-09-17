package io.qameta.allure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Allure;
import java.io.InputStream;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
@Layer("web")
@Owner("eroshenkoam")
@Feature("Issues")
public class IssuesWebTest {

    private static final String OWNER = "allure-framework, not-allure-framework";
    private static final String REPO = "allure2";

    private static final String ISSUE_TITLE = "Some issue, title here";

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("AE-T4")
    @Microservice("Repository")
    @Story("Create new issue")
    @Tags({@Tag("web"), @Tag("regress4")})
    @JiraIssues({@JiraIssue("AE-1")})
    @DisplayName("Adding note to advertisement")
    public void shouldAddLabelToIssue() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Issue Configuration (JSON)
        String issueConfig = "{\n" +
                "  \"issueId\": \"AE-T4\",\n" +
                "  \"title\": \"" + ISSUE_TITLE + "\",\n" +
                "  \"owner\": \"" + OWNER + "\",\n" +
                "  \"repo\": \"" + REPO + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"open\"\n" +
                "}";
        
        // Issue Report (HTML)
        String htmlReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Issue Report</title></head>\n" +
                "<body><h1>Issue Management Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Issue Details</h2>\n" +
                "<ul>\n" +
                "<li>Title: " + ISSUE_TITLE + "</li>\n" +
                "<li>Repository: " + OWNER + "/" + REPO + "</li>\n" +
                "<li>Status: Open</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Issue Data (CSV)
        String issueData = "issue_id,title,owner,repo,status,timestamp\n" +
                "AE-T4," + ISSUE_TITLE + "," + OWNER + "," + REPO + ",open," + timestamp;
        
        // Issue Log (Plain Text)
        String issueLog = "ISSUE MANAGEMENT LOG\n" +
                "====================\n\n" +
                "Issue ID: AE-T4\n" +
                "Title: " + ISSUE_TITLE + "\n" +
                "Repository: " + OWNER + "/" + REPO + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Open\n" +
                "Action: Label added successfully";
        
        Allure.attachment("Issue Configuration (JSON)", issueConfig);
        Allure.attachment("Issue Report (HTML)", htmlReport);
        Allure.attachment("Issue Data (CSV)", issueData);
        Allure.attachment("Issue Log (Text)", issueLog);
        
        // Add big.json file attachment
        try {
            InputStream bigJsonStream = getClass().getClassLoader().getResourceAsStream("big.json");
            if (bigJsonStream != null) {
                Allure.attachment("Big JSON Data", bigJsonStream);
            }
        } catch (Exception e) {
            // If big.json file is not found, continue with test
        }
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage(OWNER, REPO);
        steps.createIssueWithTitle(ISSUE_TITLE);
        steps.shouldSeeIssueWithTitle(ISSUE_TITLE);
    }

    @Test
    @TM4J("AE-T5")
    @Microservice("Repository")
    @Story("Close existing issue")
    @Tags({@Tag("web"), @Tag("regress4")})
    @JiraIssues({@JiraIssue("AE-1")})
    @DisplayName("Closing new issue for authorized user")
    public void shouldCloseIssue() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Close Issue Report (HTML)
        String closeReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Issue Close Report</title></head>\n" +
                "<body><h1>Issue Close Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Close Details</h2>\n" +
                "<ul>\n" +
                "<li>Issue: " + ISSUE_TITLE + "</li>\n" +
                "<li>Action: Closed</li>\n" +
                "<li>Status: Completed</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Close Metrics (CSV)
        String closeMetrics = "action,issue_title,status,timestamp\n" +
                "close," + ISSUE_TITLE + ",completed," + timestamp;
        
        Allure.attachment("Close Report (HTML)", closeReport);
        Allure.attachment("Close Metrics (CSV)", closeMetrics);
        Allure.attachment("Close Timestamp", timestamp);
        
        // Add big.json file attachment
        try {
            InputStream bigJsonStream = getClass().getClassLoader().getResourceAsStream("big.json");
            if (bigJsonStream != null) {
                Allure.attachment("Big JSON Data", bigJsonStream);
            }
        } catch (Exception e) {
            // If big.json file is not found, continue with test
        }
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage(OWNER, REPO);
        steps.createIssueWithTitle(ISSUE_TITLE);
        steps.closeIssueWithTitle(ISSUE_TITLE);
        steps.shouldNotSeeIssueWithTitle(ISSUE_TITLE);
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }

}