package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
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
@Layer("web")
@Owner("analytics-specialist")
@Feature("Analytics Dashboard")
@TM4J("TM4J-126")
@Microservice("analytics-service")
@Story("Data analytics functionality")
@Tag("analytics") @Tag("dashboard") @Tag("web")
public class AnalyticsTest {

    private WebSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new WebSteps();
    }

    @Test
    @DisplayName("Load analytics dashboard")
    @Story("Dashboard loading")
    @JiraIssues({@JiraIssue("JIRA-459")})
    public void shouldLoadAnalyticsDashboard() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // JSON Configuration
        String jsonConfig = "{\n" +
                "  \"dashboard\": {\n" +
                "    \"name\": \"Main Analytics Dashboard\",\n" +
                "    \"version\": \"2.1.0\",\n" +
                "    \"refreshInterval\": 30,\n" +
                "    \"timezone\": \"UTC\",\n" +
                "    \"widgets\": [\n" +
                "      {\"type\": \"revenue\", \"position\": \"top-left\", \"title\": \"Revenue Trend\"},\n" +
                "      {\"type\": \"users\", \"position\": \"top-right\", \"title\": \"Active Users\"},\n" +
                "      {\"type\": \"conversion\", \"position\": \"bottom-left\", \"title\": \"Conversion Rate\"},\n" +
                "      {\"type\": \"orders\", \"position\": \"bottom-right\", \"title\": \"Order Volume\"}\n" +
                "    ],\n" +
                "    \"theme\": \"dark\",\n" +
                "    \"autoRefresh\": true\n" +
                "  }\n" +
                "}";
        
        // HTML Report
        String htmlReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Analytics Dashboard Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f5f5f5;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".metric{display:inline-block;margin:10px;padding:15px;background:#e3f2fd;border-radius:4px;}\n" +
                ".value{font-size:24px;font-weight:bold;color:#1976d2;}\n" +
                ".label{font-size:14px;color:#666;}</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📊 Analytics Dashboard Report</h1>\n" +
                "<div class=\"metric\"><div class=\"value\">$12,345</div><div class=\"label\">Revenue (24h)</div></div>\n" +
                "<div class=\"metric\"><div class=\"value\">1,500</div><div class=\"label\">Active Users</div></div>\n" +
                "<div class=\"metric\"><div class=\"value\">3.2%</div><div class=\"label\">Conversion Rate</div></div>\n" +
                "<div class=\"metric\"><div class=\"value\">89</div><div class=\"label\">Orders</div></div>\n" +
                "<p><em>Generated at: " + timestamp + "</em></p>\n" +
                "</div></body></html>";
        
        // CSV Data Export
        String csvData = "timestamp,metric,value,unit,trend\n" +
                timestamp + ",revenue,12345,usd,+5.2%\n" +
                timestamp + ",active_users,1500,count,+12.1%\n" +
                timestamp + ",conversion_rate,3.2,percent,-0.3%\n" +
                timestamp + ",orders,89,count,+8.7%\n" +
                timestamp + ",bounce_rate,45.2,percent,-2.1%\n" +
                timestamp + ",avg_session_duration,180,seconds,+15.3%";
        
        // XML Configuration
        String xmlConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<dashboard>\n" +
                "  <name>Main Analytics Dashboard</name>\n" +
                "  <version>2.1.0</version>\n" +
                "  <settings>\n" +
                "    <refreshInterval>30</refreshInterval>\n" +
                "    <timezone>UTC</timezone>\n" +
                "    <theme>dark</theme>\n" +
                "    <autoRefresh>true</autoRefresh>\n" +
                "  </settings>\n" +
                "  <widgets>\n" +
                "    <widget type=\"revenue\" position=\"top-left\">\n" +
                "      <title>Revenue Trend</title>\n" +
                "      <dataSource>revenue-api</dataSource>\n" +
                "    </widget>\n" +
                "    <widget type=\"users\" position=\"top-right\">\n" +
                "      <title>Active Users</title>\n" +
                "      <dataSource>users-api</dataSource>\n" +
                "    </widget>\n" +
                "  </widgets>\n" +
                "</dashboard>";
        
        // Plain Text Summary
        String textSummary = "ANALYTICS DASHBOARD SUMMARY\n" +
                "==============================\n\n" +
                "Generated: " + timestamp + "\n" +
                "Dashboard: Main Analytics Dashboard v2.1.0\n\n" +
                "KEY METRICS:\n" +
                "• Revenue (24h): $12,345 (+5.2%)\n" +
                "• Active Users: 1,500 (+12.1%)\n" +
                "• Conversion Rate: 3.2% (-0.3%)\n" +
                "• Orders: 89 (+8.7%)\n" +
                "• Bounce Rate: 45.2% (-2.1%)\n" +
                "• Avg Session Duration: 3:00 (+15.3%)\n\n" +
                "STATUS: ✅ All systems operational\n" +
                "THEME: Dark mode enabled\n" +
                "AUTO-REFRESH: Every 30 seconds\n" +
                "LAST UPDATE: " + timestamp;
        
        // JavaScript Code
        String jsCode = "// Analytics Dashboard JavaScript\n" +
                "class AnalyticsDashboard {\n" +
                "    constructor(config) {\n" +
                "        this.config = config;\n" +
                "        this.metrics = {};\n" +
                "        this.charts = [];\n" +
                "        this.refreshInterval = null;\n" +
                "    }\n\n" +
                "    async loadMetrics() {\n" +
                "        try {\n" +
                "            const response = await fetch('/api/analytics/metrics');\n" +
                "            const data = await response.json();\n" +
                "            this.metrics = data;\n" +
                "            this.updateWidgets();\n" +
                "            this.log('Metrics loaded successfully');\n" +
                "        } catch (error) {\n" +
                "            this.log('Error loading metrics: ' + error.message);\n" +
                "        }\n" +
                "    }\n\n" +
                "    updateWidgets() {\n" +
                "        Object.keys(this.metrics).forEach(key => {\n" +
                "            const widget = document.getElementById(key);\n" +
                "            if (widget) {\n" +
                "                widget.textContent = this.metrics[key];\n" +
                "                widget.classList.add('updated');\n" +
                "            }\n" +
                "        });\n" +
                "    }\n\n" +
                "    startAutoRefresh() {\n" +
                "        this.refreshInterval = setInterval(() => {\n" +
                "            this.loadMetrics();\n" +
                "        }, this.config.refreshInterval * 1000);\n" +
                "    }\n\n" +
                "    log(message) {\n" +
                "        console.log(`[AnalyticsDashboard] ${new Date().toISOString()}: ${message}`);\n" +
                "    }\n" +
                "}\n\n" +
                "// Initialize dashboard\n" +
                "const dashboard = new AnalyticsDashboard({\n" +
                "    refreshInterval: 30,\n" +
                "    theme: 'dark',\n" +
                "    autoRefresh: true\n" +
                "});\n" +
                "dashboard.loadMetrics();\n" +
                "dashboard.startAutoRefresh();";
        
        Allure.attachment("Dashboard Configuration (JSON)", jsonConfig);
        Allure.attachment("Analytics Report (HTML)", htmlReport);
        Allure.attachment("Metrics Data (CSV)", csvData);
        Allure.attachment("Dashboard Config (XML)", xmlConfig);
        Allure.attachment("Summary Report (Text)", textSummary);
        Allure.attachment("Dashboard JavaScript", jsCode);
        
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Analytics Dashboard Loading");
        steps.shouldSeeIssueWithTitle("Analytics Dashboard Loading");
    }

    @Test
    @DisplayName("Test analytics views")
    @Story("Analytics views")
    public void shouldTestAnalyticsViews() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // View configuration
        String viewConfig = "{\n" +
                "  \"viewType\": \"Real-time\",\n" +
                "  \"dataSource\": \"Live Data Stream\",\n" +
                "  \"refreshRate\": \"1 second\",\n" +
                "  \"timeRange\": \"Last 24 hours\",\n" +
                "  \"metrics\": [\"active_users\", \"revenue_per_minute\", \"conversion_rate\"]\n" +
                "}";

        // View performance
        String performance = "View: Real-time\n" +
                "Load Time: 150ms\n" +
                "Data Points: 1,440\n" +
                "Memory Usage: 45MB\n" +
                "Cache Hit Rate: 15%";

        // View result
        String result = "Analytics view 'Real-time' loaded successfully at " + timestamp + "\n" +
                "Status: Active\n" +
                "Data Freshness: Live\n" +
                "User Interactions: High (frequent updates)";

        Allure.attachment("View Configuration", viewConfig);
        Allure.attachment("Performance Metrics", performance);
        Allure.attachment("View Result", result);
        
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Analytics View: Real-time");
        steps.shouldSeeIssueWithTitle("Analytics View: Real-time");
    }
}