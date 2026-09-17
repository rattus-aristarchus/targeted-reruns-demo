package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.qameta.allure.Allure;
import java.io.InputStream;

@ExtendWith(AllureJunit5.class)
@Layer("rest")
@Owner("integration-specialist")
@Feature("Integration API")
@TM4J("TM4J-130")
@Microservice("integration-service")
@Story("Integration API functionality")
@Tag("integration") @Tag("api") @Tag("rest")
public class IntegrationApiTest {

    private RestSteps steps = new RestSteps();

    @Test
    @DisplayName("Test third-party API integration")
    @Story("API integration")
    @JiraIssues({@JiraIssue("JIRA-463")})
    public void shouldTestThirdPartyApiIntegration() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // API Configuration (JSON)
        String apiConfig = "{\n" +
                "  \"integrations\": [\n" +
                "    {\n" +
                "      \"name\": \"Payment Gateway\",\n" +
                "      \"type\": \"payment\",\n" +
                "      \"endpoint\": \"https://api.payment.com/v1\",\n" +
                "      \"auth\": \"bearer\",\n" +
                "      \"timeout\": 30000\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Shipping Provider\",\n" +
                "      \"type\": \"shipping\",\n" +
                "      \"endpoint\": \"https://api.shipping.com/v2\",\n" +
                "      \"auth\": \"api_key\",\n" +
                "      \"timeout\": 15000\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        
        // Integration Status Dashboard (HTML)
        String statusDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Integration Status Dashboard</title></head>\n" +
                "<body><h1>Integration Status Dashboard</h1>\n" +
                "<p>Last updated: " + timestamp + "</p>\n" +
                "<ul>\n" +
                "<li>Payment Gateway: ✅ Online</li>\n" +
                "<li>Shipping Provider: ✅ Online</li>\n" +
                "<li>Email Service: ✅ Online</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // API Response Times (CSV)
        String responseTimes = "service,endpoint,avg_response_time,success_rate,last_check\n" +
                "Payment Gateway,api.payment.com,250ms,99.8%," + timestamp + "\n" +
                "Shipping Provider,api.shipping.com,180ms,99.9%," + timestamp + "\n" +
                "Email Service,api.email.com,95ms,99.7%," + timestamp;
        
        // Integration Schema (XML)
        String integrationSchema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<integration-schema>\n" +
                "  <service name=\"Payment Gateway\" type=\"payment\">\n" +
                "    <endpoint>https://api.payment.com/v1</endpoint>\n" +
                "    <timeout>30000</timeout>\n" +
                "  </service>\n" +
                "</integration-schema>";
        
        // Integration Test Report (Plain Text)
        String testReport = "INTEGRATION API TEST REPORT\n" +
                "============================\n\n" +
                "Test Date: " + timestamp + "\n" +
                "Status: ✅ PASSED\n\n" +
                "SERVICES:\n" +
                "1. Payment Gateway - ✅ Online\n" +
                "2. Shipping Provider - ✅ Online\n" +
                "3. Email Service - ✅ Online\n\n" +
                "Overall Success Rate: 99.8%";
        
        // Integration JavaScript
        String integrationJS = "class IntegrationManager {\n" +
                "  constructor(config) {\n" +
                "    this.integrations = config.integrations;\n" +
                "  }\n\n" +
                "  async checkStatus(integrationName) {\n" +
                "    const integration = this.integrations.find(i => i.name === integrationName);\n" +
                "    return { online: true, responseTime: '250ms' };\n" +
                "  }\n" +
                "}";
        
        Allure.attachment("API Configuration (JSON)", apiConfig);
        Allure.attachment("Status Dashboard (HTML)", statusDashboard);
        Allure.attachment("Response Times (CSV)", responseTimes);
        Allure.attachment("Integration Schema (XML)", integrationSchema);
        Allure.attachment("Test Report (Text)", testReport);
        Allure.attachment("Integration JavaScript", integrationJS);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Integration API Test");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Integration API Test");
    }

    @Test
    @DisplayName("Test API data synchronization")
    @Story("Data synchronization")
    public void shouldTestApiDataSynchronization() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String syncConfig = "{\n" +
                "  \"syncJobs\": [\n" +
                "    {\"id\": \"user-sync\", \"status\": \"success\"},\n" +
                "    {\"id\": \"order-sync\", \"status\": \"success\"}\n" +
                "  ]\n" +
                "}";
        
        String syncReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Data Sync Status</title></head>\n" +
                "<body><h1>Data Sync Status</h1>\n" +
                "<p>Last updated: " + timestamp + "</p>\n" +
                "<ul><li>User Sync: ✅ Success</li><li>Order Sync: ✅ Success</li></ul>\n" +
                "</body></html>";
        
        String syncMetrics = "job_id,records_synced,success_rate,last_sync\n" +
                "user-sync,1250,99.8%," + timestamp + "\n" +
                "order-sync,89,100%," + timestamp;
        
        Allure.attachment("Sync Configuration (JSON)", syncConfig);
        Allure.attachment("Sync Report (HTML)", syncReport);
        Allure.attachment("Sync Metrics (CSV)", syncMetrics);
        Allure.attachment("Sync Timestamp", timestamp);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "API Data Sync Test");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "API Data Sync Test");
    }
}