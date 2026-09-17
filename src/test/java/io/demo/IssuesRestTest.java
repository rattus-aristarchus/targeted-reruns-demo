package io.demo;

import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;

@Layer("rest")
@Owner("baev")
@Feature("Issues")
@ExtendWith(FailureAttachmentExtension.class)
public class IssuesRestTest {

    private static final String OWNER = "allure-framework, not-allure-framework";
    private static final String REPO = "allure2";

    private final RestSteps steps = new RestSteps();

    @TM4J("AE-T1")
    @Story("Create new issue")
    @Microservice("Billing")
    @Tags({@Tag("api"), @Tag("smoke4")})
    @ParameterizedTest(name = "Create issue via api {0}")
    @ValueSource(strings = {"First Note", "Second Note"})
    public void shouldCreateUserNote(@Param(value = "Title,") String title) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // API Issue Configuration (JSON)
        String apiConfig = "{\n" +
                "  \"endpoint\": \"/api/issues\",\n" +
                "  \"method\": \"POST\",\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"owner\": \"" + OWNER + "\",\n" +
                "  \"repo\": \"" + REPO + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\"\n" +
                "}";
        
        // API Response (HTML)
        String apiResponse = "<!DOCTYPE html>\n" +
                "<html><head><title>API Response</title></head>\n" +
                "<body><h1>API Issue Creation Response</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Response Details</h2>\n" +
                "<ul>\n" +
                "<li>Title: " + title + "</li>\n" +
                "<li>Status: 201 Created</li>\n" +
                "<li>Repository: " + OWNER + "/" + REPO + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // API Metrics (CSV)
        String apiMetrics = "endpoint,method,title,status,timestamp\n" +
                "/api/issues,POST," + title + ",201," + timestamp;
        
        // API Log (Plain Text)
        String apiLog = "API ISSUE CREATION LOG\n" +
                "======================\n\n" +
                "Endpoint: /api/issues\n" +
                "Method: POST\n" +
                "Title: " + title + "\n" +
                "Repository: " + OWNER + "/" + REPO + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: 201 Created";
        
        Allure.attachment("API Configuration (JSON)", apiConfig);
        Allure.attachment("API Response (HTML)", apiResponse);
        Allure.attachment("API Metrics (CSV)", apiMetrics);
        Allure.attachment("API Log (Text)", apiLog);
        
        steps.createIssueWithTitle(OWNER, REPO, title);
        steps.shouldSeeIssueWithTitle(OWNER, REPO, title);
    }

    @TM4J("AE-T2")
    @Story("Close existing issue")
    @Microservice("Repository")
    @Tags({@Tag("web"), @Tag("regress4")})
    @JiraIssues({@JiraIssue("AE-1")})
    @ParameterizedTest(name = "Close issue via api")
    @ValueSource(strings = {"First Note", "Second Note", "Third Note", "Fourth Note"})
    public void shouldDeleteUserNote(@Param(value = "Title") String title) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Close API Configuration (JSON)
        String closeConfig = "{\n" +
                "  \"endpoint\": \"/api/issues/close\",\n" +
                "  \"method\": \"PUT\",\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\"\n" +
                "}";
        
        // Close Response (HTML)
        String closeResponse = "<!DOCTYPE html>\n" +
                "<html><head><title>Close Response</title></head>\n" +
                "<body><h1>Issue Close Response</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Close Details</h2>\n" +
                "<ul>\n" +
                "<li>Title: " + title + "</li>\n" +
                "<li>Status: 200 OK</li>\n" +
                "<li>Action: Closed</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Close Metrics (CSV)
        String closeMetrics = "endpoint,method,title,status,timestamp\n" +
                "/api/issues/close,PUT," + title + ",200," + timestamp;
        
        Allure.attachment("Close Configuration (JSON)", closeConfig);
        Allure.attachment("Close Response (HTML)", closeResponse);
        Allure.attachment("Close Metrics (CSV)", closeMetrics);
        Allure.attachment("Close Timestamp", timestamp);
        
        steps.createIssueWithTitle(OWNER, REPO, title);
        steps.closeIssueWithTitle(OWNER, REPO, title);
    }


}
