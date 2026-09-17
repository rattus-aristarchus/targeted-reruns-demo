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
import java.io.InputStream;

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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Sales Report Configuration (JSON)
        String reportConfig = "{\n" +
                "  \"reportType\": \"Sales Report\",\n" +
                "  \"reportName\": \"Sales Report Generation\",\n" +
                "  \"period\": \"Monthly\",\n" +
                "  \"format\": \"PDF\",\n" +
                "  \"metrics\": [\"Revenue\", \"Units Sold\", \"Profit Margin\"],\n" +
                "  \"visualization\": \"Chart\",\n" +
                "  \"priority\": \"High\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Sales Report Dashboard (HTML)
        String reportDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Sales Report Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".format{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".priority{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".metric{font-size:16px;font-weight:bold;color:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📊 Sales Report Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Sales Report Generation <span class=\"format\">PDF</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Report Type:</strong></span>\n" +
                "<span>Sales Report</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Period:</strong></span>\n" +
                "<span>Monthly</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Format:</strong></span>\n" +
                "<span class=\"format\">PDF</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Priority:</strong></span>\n" +
                "<span class=\"priority\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Visualization:</strong></span>\n" +
                "<span>Chart</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Revenue</h3>\n" +
                "<p class=\"metric\">$125,000</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Units Sold</h3>\n" +
                "<p class=\"metric\">1,250</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Profit Margin</h3>\n" +
                "<p class=\"metric\">23.5%</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Sales Report Data (CSV)
        String reportData = "report_type,report_name,period,format,visualization,priority,timestamp\n" +
                "Sales Report,Sales Report Generation,Monthly,PDF,Chart,High," + timestamp;
        
        // Sales Report Log (Plain Text)
        String reportLog = "SALES REPORT GENERATION LOG\n" +
                "===========================\n\n" +
                "Report Type: Sales Report\n" +
                "Report Name: Sales Report Generation\n" +
                "Period: Monthly\n" +
                "Format: PDF\n" +
                "Visualization: Chart\n" +
                "Priority: High\n" +
                "Metrics: Revenue, Units Sold, Profit Margin\n" +
                "Revenue: $125,000\n" +
                "Units Sold: 1,250\n" +
                "Profit Margin: 23.5%\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Sales report generation completed";
        
        Allure.attachment("Sales Report Configuration (JSON)", reportConfig);
        Allure.attachment("Sales Report Dashboard (HTML)", reportDashboard);
        Allure.attachment("Sales Report Data (CSV)", reportData);
        Allure.attachment("Sales Report Log (Text)", reportLog);
        
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
        steps.createIssueWithTitle("Sales Report Generation");
        steps.shouldSeeIssueWithTitle("Sales Report Generation");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Daily", "Weekly", "Monthly", "Quarterly", "Yearly"})
    @DisplayName("Generate reports for different time periods")
    @Story("Time-based reporting")
    public void shouldGenerateTimeBasedReports(String period) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Time-based Report Configuration (JSON)
        String timeConfig = "{\n" +
                "  \"reportType\": \"Time-based Report\",\n" +
                "  \"period\": \"" + period + "\",\n" +
                "  \"reportName\": \"Time Report: " + period + "\",\n" +
                "  \"frequency\": \"" + getReportFrequency(period) + "\",\n" +
                "  \"dataPoints\": " + getDataPoints(period) + ",\n" +
                "  \"format\": \"PDF\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Time-based Report Dashboard (HTML)
        String timeDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Time-based Report Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                ".period{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".frequency{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".data-points{font-size:16px;font-weight:bold;color:#2196f3;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>⏰ Time-based Report Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Time Report: " + period + " <span class=\"period\">" + period + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Report Type:</strong></span>\n" +
                "<span>Time-based Report</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Period:</strong></span>\n" +
                "<span class=\"period\">" + period + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Frequency:</strong></span>\n" +
                "<span class=\"frequency\">" + getReportFrequency(period) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Data Points:</strong></span>\n" +
                "<span class=\"data-points\">" + getDataPoints(period) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Period</h3>\n" +
                "<p>" + period + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Frequency</h3>\n" +
                "<p>" + getReportFrequency(period) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Data Points</h3>\n" +
                "<p>" + getDataPoints(period) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Time-based Report Data (CSV)
        String timeData = "report_type,period,frequency,data_points,format,timestamp\n" +
                "Time-based Report," + period + "," + getReportFrequency(period) + "," + getDataPoints(period) + ",PDF," + timestamp;
        
        // Time-based Report Log (Plain Text)
        String timeLog = "TIME-BASED REPORT GENERATION LOG\n" +
                "=================================\n\n" +
                "Report Type: Time-based Report\n" +
                "Period: " + period + "\n" +
                "Frequency: " + getReportFrequency(period) + "\n" +
                "Data Points: " + getDataPoints(period) + "\n" +
                "Format: PDF\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Time-based report generation completed";
        
        Allure.attachment("Time Report Configuration (JSON)", timeConfig);
        Allure.attachment("Time Report Dashboard (HTML)", timeDashboard);
        Allure.attachment("Time Report Data (CSV)", timeData);
        Allure.attachment("Time Report Log (Text)", timeLog);
        
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
        steps.createIssueWithTitle("Time Report: " + period);
        steps.shouldSeeIssueWithTitle("Time Report: " + period);
    }
    
    private String getReportFrequency(String period) {
        switch (period) {
            case "Daily": return "24 hours";
            case "Weekly": return "7 days";
            case "Monthly": return "30 days";
            case "Quarterly": return "90 days";
            case "Yearly": return "365 days";
            default: return "Unknown";
        }
    }
    
    private int getDataPoints(String period) {
        switch (period) {
            case "Daily": return 24;
            case "Weekly": return 7;
            case "Monthly": return 30;
            case "Quarterly": return 12;
            case "Yearly": return 12;
            default: return 0;
        }
    }

    @ParameterizedTest
    @CsvSource({"Sales, Revenue", "Inventory, Stock", "Customer, Satisfaction", "Product, Performance"})
    @DisplayName("Generate different report types")
    @Story("Report type generation")
    public void shouldGenerateDifferentReportTypes(String reportType, String metric) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Report Type Configuration (JSON)
        String typeConfig = "{\n" +
                "  \"reportType\": \"" + reportType + " Report\",\n" +
                "  \"metric\": \"" + metric + "\",\n" +
                "  \"reportName\": \"Report Type: " + reportType + " - " + metric + "\",\n" +
                "  \"category\": \"" + getReportCategory(reportType) + "\",\n" +
                "  \"complexity\": \"" + getReportComplexity(reportType) + "\",\n" +
                "  \"format\": \"PDF\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Report Type Dashboard (HTML)
        String typeDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Report Type Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#e65100;}\n" +
                ".type{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".metric{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".complexity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".category{font-size:16px;font-weight:bold;color:#ff9800;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📋 Report Type Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">" + reportType + " Report <span class=\"type\">" + reportType + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Report Type:</strong></span>\n" +
                "<span class=\"type\">" + reportType + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Metric:</strong></span>\n" +
                "<span class=\"metric\">" + metric + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Category:</strong></span>\n" +
                "<span class=\"category\">" + getReportCategory(reportType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Complexity:</strong></span>\n" +
                "<span class=\"complexity\">" + getReportComplexity(reportType) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Type</h3>\n" +
                "<p>" + reportType + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Metric</h3>\n" +
                "<p>" + metric + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Complexity</h3>\n" +
                "<p>" + getReportComplexity(reportType) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Report Type Data (CSV)
        String typeData = "report_type,metric,category,complexity,format,timestamp\n" +
                reportType + "," + metric + "," + getReportCategory(reportType) + "," + getReportComplexity(reportType) + ",PDF," + timestamp;
        
        // Report Type Log (Plain Text)
        String typeLog = "REPORT TYPE GENERATION LOG\n" +
                "==========================\n\n" +
                "Report Type: " + reportType + "\n" +
                "Metric: " + metric + "\n" +
                "Category: " + getReportCategory(reportType) + "\n" +
                "Complexity: " + getReportComplexity(reportType) + "\n" +
                "Format: PDF\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Report type generation completed";
        
        Allure.attachment("Report Type Configuration (JSON)", typeConfig);
        Allure.attachment("Report Type Dashboard (HTML)", typeDashboard);
        Allure.attachment("Report Type Data (CSV)", typeData);
        Allure.attachment("Report Type Log (Text)", typeLog);
        
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
        steps.createIssueWithTitle("Report Type: " + reportType + " - " + metric);
        steps.shouldSeeIssueWithTitle("Report Type: " + reportType + " - " + metric);
    }
    
    private String getReportCategory(String reportType) {
        switch (reportType) {
            case "Sales": return "Business";
            case "Inventory": return "Operations";
            case "Customer": return "Analytics";
            case "Product": return "Performance";
            default: return "General";
        }
    }
    
    private String getReportComplexity(String reportType) {
        switch (reportType) {
            case "Sales": return "High";
            case "Inventory": return "Medium";
            case "Customer": return "High";
            case "Product": return "Medium";
            default: return "Low";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"PDF", "Excel", "CSV", "HTML", "JSON"})
    @DisplayName("Export reports in different formats")
    @Story("Report export formats")
    public void shouldExportReportsInDifferentFormats(String format) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Export Format Configuration (JSON)
        String exportConfig = "{\n" +
                "  \"exportFormat\": \"" + format + "\",\n" +
                "  \"reportName\": \"Export Format: " + format + "\",\n" +
                "  \"mimeType\": \"" + getMimeType(format) + "\",\n" +
                "  \"fileSize\": \"" + getFileSize(format) + "\",\n" +
                "  \"compatibility\": \"" + getCompatibility(format) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Export Format Dashboard (HTML)
        String exportDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Export Format Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#7b1fa2;}\n" +
                ".format{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#9c27b0;}\n" +
                ".mime{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".size{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".compatibility{font-size:16px;font-weight:bold;color:#9c27b0;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📤 Export Format Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Export Format: " + format + " <span class=\"format\">" + format + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Format:</strong></span>\n" +
                "<span class=\"format\">" + format + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>MIME Type:</strong></span>\n" +
                "<span class=\"mime\">" + getMimeType(format) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Size:</strong></span>\n" +
                "<span class=\"size\">" + getFileSize(format) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Compatibility:</strong></span>\n" +
                "<span class=\"compatibility\">" + getCompatibility(format) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Format</h3>\n" +
                "<p>" + format + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Size</h3>\n" +
                "<p>" + getFileSize(format) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Compatibility</h3>\n" +
                "<p>" + getCompatibility(format) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Export Format Data (CSV)
        String exportData = "export_format,mime_type,file_size,compatibility,timestamp\n" +
                format + "," + getMimeType(format) + "," + getFileSize(format) + "," + getCompatibility(format) + "," + timestamp;
        
        // Export Format Log (Plain Text)
        String exportLog = "EXPORT FORMAT GENERATION LOG\n" +
                "============================\n\n" +
                "Export Format: " + format + "\n" +
                "MIME Type: " + getMimeType(format) + "\n" +
                "File Size: " + getFileSize(format) + "\n" +
                "Compatibility: " + getCompatibility(format) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Export format generation completed";
        
        Allure.attachment("Export Format Configuration (JSON)", exportConfig);
        Allure.attachment("Export Format Dashboard (HTML)", exportDashboard);
        Allure.attachment("Export Format Data (CSV)", exportData);
        Allure.attachment("Export Format Log (Text)", exportLog);
        
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
        steps.createIssueWithTitle("Export Format: " + format);
        steps.shouldSeeIssueWithTitle("Export Format: " + format);
    }
    
    private String getMimeType(String format) {
        switch (format) {
            case "PDF": return "application/pdf";
            case "Excel": return "application/vnd.ms-excel";
            case "CSV": return "text/csv";
            case "HTML": return "text/html";
            case "JSON": return "application/json";
            default: return "application/octet-stream";
        }
    }
    
    private String getFileSize(String format) {
        switch (format) {
            case "PDF": return "2.5 MB";
            case "Excel": return "1.8 MB";
            case "CSV": return "850 KB";
            case "HTML": return "1.2 MB";
            case "JSON": return "950 KB";
            default: return "1.0 MB";
        }
    }
    
    private String getCompatibility(String format) {
        switch (format) {
            case "PDF": return "Universal";
            case "Excel": return "Microsoft Office";
            case "CSV": return "Universal";
            case "HTML": return "Web Browsers";
            case "JSON": return "APIs";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chart", "Table", "Graph", "Dashboard", "Summary"})
    @DisplayName("Generate different visualization types")
    @Story("Report visualization")
    public void shouldGenerateDifferentVisualizationTypes(String visualization) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Visualization Configuration (JSON)
        String vizConfig = "{\n" +
                "  \"visualizationType\": \"" + visualization + "\",\n" +
                "  \"reportName\": \"Visualization: " + visualization + "\",\n" +
                "  \"interactivity\": \"" + getInteractivity(visualization) + "\",\n" +
                "  \"dataDensity\": \"" + getDataDensity(visualization) + "\",\n" +
                "  \"renderingTime\": \"" + getRenderingTime(visualization) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Visualization Dashboard (HTML)
        String vizDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Visualization Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e0f2f1;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #009688;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#00695c;}\n" +
                ".viz{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#009688;}\n" +
                ".interactive{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".density{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#009688;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📈 Visualization Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Visualization: " + visualization + " <span class=\"viz\">" + visualization + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Visualization Type:</strong></span>\n" +
                "<span class=\"viz\">" + visualization + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Interactivity:</strong></span>\n" +
                "<span class=\"interactive\">" + getInteractivity(visualization) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Data Density:</strong></span>\n" +
                "<span class=\"density\">" + getDataDensity(visualization) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Rendering Time:</strong></span>\n" +
                "<span class=\"time\">" + getRenderingTime(visualization) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Type</h3>\n" +
                "<p>" + visualization + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Interactivity</h3>\n" +
                "<p>" + getInteractivity(visualization) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Rendering Time</h3>\n" +
                "<p>" + getRenderingTime(visualization) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Visualization Data (CSV)
        String vizData = "visualization_type,interactivity,data_density,rendering_time,timestamp\n" +
                visualization + "," + getInteractivity(visualization) + "," + getDataDensity(visualization) + "," + getRenderingTime(visualization) + "," + timestamp;
        
        // Visualization Log (Plain Text)
        String vizLog = "VISUALIZATION GENERATION LOG\n" +
                "============================\n\n" +
                "Visualization Type: " + visualization + "\n" +
                "Interactivity: " + getInteractivity(visualization) + "\n" +
                "Data Density: " + getDataDensity(visualization) + "\n" +
                "Rendering Time: " + getRenderingTime(visualization) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Visualization generation completed";
        
        Allure.attachment("Visualization Configuration (JSON)", vizConfig);
        Allure.attachment("Visualization Dashboard (HTML)", vizDashboard);
        Allure.attachment("Visualization Data (CSV)", vizData);
        Allure.attachment("Visualization Log (Text)", vizLog);
        
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
        steps.createIssueWithTitle("Visualization: " + visualization);
        steps.shouldSeeIssueWithTitle("Visualization: " + visualization);
    }
    
    private String getInteractivity(String visualization) {
        switch (visualization) {
            case "Chart": return "High";
            case "Table": return "Medium";
            case "Graph": return "High";
            case "Dashboard": return "Very High";
            case "Summary": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getDataDensity(String visualization) {
        switch (visualization) {
            case "Chart": return "Medium";
            case "Table": return "High";
            case "Graph": return "High";
            case "Dashboard": return "Very High";
            case "Summary": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getRenderingTime(String visualization) {
        switch (visualization) {
            case "Chart": return "2.3s";
            case "Table": return "1.8s";
            case "Graph": return "3.1s";
            case "Dashboard": return "4.5s";
            case "Summary": return "0.9s";
            default: return "2.0s";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Admin", "Manager", "Analyst", "Viewer", "Guest"})
    @DisplayName("Generate reports for different user roles")
    @Story("Role-based reporting")
    public void shouldGenerateReportsForDifferentUserRoles(String role) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // User Role Configuration (JSON)
        String roleConfig = "{\n" +
                "  \"userRole\": \"" + role + "\",\n" +
                "  \"reportName\": \"User Role: " + role + "\",\n" +
                "  \"permissions\": \"" + getPermissions(role) + "\",\n" +
                "  \"accessLevel\": \"" + getAccessLevel(role) + "\",\n" +
                "  \"reportScope\": \"" + getReportScope(role) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // User Role Dashboard (HTML)
        String roleDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>User Role Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".role{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".permissions{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".access{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".scope{font-size:16px;font-weight:bold;color:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>👤 User Role Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">User Role: " + role + " <span class=\"role\">" + role + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>User Role:</strong></span>\n" +
                "<span class=\"role\">" + role + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Permissions:</strong></span>\n" +
                "<span class=\"permissions\">" + getPermissions(role) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Access Level:</strong></span>\n" +
                "<span class=\"access\">" + getAccessLevel(role) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Report Scope:</strong></span>\n" +
                "<span class=\"scope\">" + getReportScope(role) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Role</h3>\n" +
                "<p>" + role + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Access Level</h3>\n" +
                "<p>" + getAccessLevel(role) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Scope</h3>\n" +
                "<p>" + getReportScope(role) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // User Role Data (CSV)
        String roleData = "user_role,permissions,access_level,report_scope,timestamp\n" +
                role + "," + getPermissions(role) + "," + getAccessLevel(role) + "," + getReportScope(role) + "," + timestamp;
        
        // User Role Log (Plain Text)
        String roleLog = "USER ROLE REPORT GENERATION LOG\n" +
                "==================================\n\n" +
                "User Role: " + role + "\n" +
                "Permissions: " + getPermissions(role) + "\n" +
                "Access Level: " + getAccessLevel(role) + "\n" +
                "Report Scope: " + getReportScope(role) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: User role report generation completed";
        
        Allure.attachment("User Role Configuration (JSON)", roleConfig);
        Allure.attachment("User Role Dashboard (HTML)", roleDashboard);
        Allure.attachment("User Role Data (CSV)", roleData);
        Allure.attachment("User Role Log (Text)", roleLog);
        
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
        steps.createIssueWithTitle("User Role: " + role);
        steps.shouldSeeIssueWithTitle("User Role: " + role);
    }
    
    private String getPermissions(String role) {
        switch (role) {
            case "Admin": return "Full Access";
            case "Manager": return "Management Access";
            case "Analyst": return "Analytical Access";
            case "Viewer": return "Read Only";
            case "Guest": return "Limited Access";
            default: return "Unknown";
        }
    }
    
    private String getAccessLevel(String role) {
        switch (role) {
            case "Admin": return "Level 5";
            case "Manager": return "Level 4";
            case "Analyst": return "Level 3";
            case "Viewer": return "Level 2";
            case "Guest": return "Level 1";
            default: return "Level 0";
        }
    }
    
    private String getReportScope(String role) {
        switch (role) {
            case "Admin": return "All Reports";
            case "Manager": return "Department Reports";
            case "Analyst": return "Analytical Reports";
            case "Viewer": return "Public Reports";
            case "Guest": return "Basic Reports";
            default: return "No Access";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"North", "South", "East", "West", "Central"})
    @DisplayName("Generate regional reports")
    @Story("Regional reporting")
    public void shouldGenerateRegionalReports(String region) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Regional Report Configuration (JSON)
        String regionConfig = "{\n" +
                "  \"region\": \"" + region + "\",\n" +
                "  \"reportName\": \"Region: " + region + "\",\n" +
                "  \"population\": " + getPopulation(region) + ",\n" +
                "  \"salesVolume\": \"" + getSalesVolume(region) + "\",\n" +
                "  \"marketShare\": \"" + getMarketShare(region) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Regional Report Dashboard (HTML)
        String regionDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Regional Report Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                ".region{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".population{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".sales{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".market{font-size:16px;font-weight:bold;color:#2196f3;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🌍 Regional Report Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Region: " + region + " <span class=\"region\">" + region + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Region:</strong></span>\n" +
                "<span class=\"region\">" + region + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Population:</strong></span>\n" +
                "<span class=\"population\">" + getPopulation(region) + "M</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Sales Volume:</strong></span>\n" +
                "<span class=\"sales\">" + getSalesVolume(region) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Market Share:</strong></span>\n" +
                "<span class=\"market\">" + getMarketShare(region) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Population</h3>\n" +
                "<p>" + getPopulation(region) + "M</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Sales Volume</h3>\n" +
                "<p>" + getSalesVolume(region) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Market Share</h3>\n" +
                "<p>" + getMarketShare(region) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Regional Report Data (CSV)
        String regionData = "region,population,sales_volume,market_share,timestamp\n" +
                region + "," + getPopulation(region) + "," + getSalesVolume(region) + "," + getMarketShare(region) + "," + timestamp;
        
        // Regional Report Log (Plain Text)
        String regionLog = "REGIONAL REPORT GENERATION LOG\n" +
                "==============================\n\n" +
                "Region: " + region + "\n" +
                "Population: " + getPopulation(region) + "M\n" +
                "Sales Volume: " + getSalesVolume(region) + "\n" +
                "Market Share: " + getMarketShare(region) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Regional report generation completed";
        
        Allure.attachment("Regional Report Configuration (JSON)", regionConfig);
        Allure.attachment("Regional Report Dashboard (HTML)", regionDashboard);
        Allure.attachment("Regional Report Data (CSV)", regionData);
        Allure.attachment("Regional Report Log (Text)", regionLog);
        
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
        steps.createIssueWithTitle("Region: " + region);
        steps.shouldSeeIssueWithTitle("Region: " + region);
    }
    
    private int getPopulation(String region) {
        switch (region) {
            case "North": return 25;
            case "South": return 18;
            case "East": return 22;
            case "West": return 20;
            case "Central": return 15;
            default: return 0;
        }
    }
    
    private String getSalesVolume(String region) {
        switch (region) {
            case "North": return "$2.5M";
            case "South": return "$1.8M";
            case "East": return "$2.2M";
            case "West": return "$2.0M";
            case "Central": return "$1.5M";
            default: return "$0M";
        }
    }
    
    private String getMarketShare(String region) {
        switch (region) {
            case "North": return "28%";
            case "South": return "20%";
            case "East": return "25%";
            case "West": return "22%";
            case "Central": return "15%";
            default: return "0%";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Real-time", "Scheduled", "On-demand", "Batch", "Streaming"})
    @DisplayName("Generate reports with different delivery methods")
    @Story("Report delivery methods")
    public void shouldGenerateReportsWithDifferentDeliveryMethods(String method) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Delivery Method Configuration (JSON)
        String deliveryConfig = "{\n" +
                "  \"deliveryMethod\": \"" + method + "\",\n" +
                "  \"reportName\": \"Delivery Method: " + method + "\",\n" +
                "  \"latency\": \"" + getLatency(method) + "\",\n" +
                "  \"frequency\": \"" + getFrequency(method) + "\",\n" +
                "  \"reliability\": \"" + getReliability(method) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delivery Method Dashboard (HTML)
        String deliveryDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Delivery Method Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#e65100;}\n" +
                ".method{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".latency{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".frequency{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".reliability{font-size:16px;font-weight:bold;color:#ff9800;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🚀 Delivery Method Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Delivery Method: " + method + " <span class=\"method\">" + method + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Delivery Method:</strong></span>\n" +
                "<span class=\"method\">" + method + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Latency:</strong></span>\n" +
                "<span class=\"latency\">" + getLatency(method) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Frequency:</strong></span>\n" +
                "<span class=\"frequency\">" + getFrequency(method) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Reliability:</strong></span>\n" +
                "<span class=\"reliability\">" + getReliability(method) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Method</h3>\n" +
                "<p>" + method + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Latency</h3>\n" +
                "<p>" + getLatency(method) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Reliability</h3>\n" +
                "<p>" + getReliability(method) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Delivery Method Data (CSV)
        String deliveryData = "delivery_method,latency,frequency,reliability,timestamp\n" +
                method + "," + getLatency(method) + "," + getFrequency(method) + "," + getReliability(method) + "," + timestamp;
        
        // Delivery Method Log (Plain Text)
        String deliveryLog = "DELIVERY METHOD REPORT GENERATION LOG\n" +
                "====================================\n\n" +
                "Delivery Method: " + method + "\n" +
                "Latency: " + getLatency(method) + "\n" +
                "Frequency: " + getFrequency(method) + "\n" +
                "Reliability: " + getReliability(method) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Delivery method report generation completed";
        
        Allure.attachment("Delivery Method Configuration (JSON)", deliveryConfig);
        Allure.attachment("Delivery Method Dashboard (HTML)", deliveryDashboard);
        Allure.attachment("Delivery Method Data (CSV)", deliveryData);
        Allure.attachment("Delivery Method Log (Text)", deliveryLog);
        
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
        steps.createIssueWithTitle("Delivery Method: " + method);
        steps.shouldSeeIssueWithTitle("Delivery Method: " + method);
    }
    
    private String getLatency(String method) {
        switch (method) {
            case "Real-time": return "< 1s";
            case "Scheduled": return "5-10s";
            case "On-demand": return "2-5s";
            case "Batch": return "30-60s";
            case "Streaming": return "< 500ms";
            default: return "Unknown";
        }
    }
    
    private String getFrequency(String method) {
        switch (method) {
            case "Real-time": return "Continuous";
            case "Scheduled": return "Daily";
            case "On-demand": return "As Needed";
            case "Batch": return "Hourly";
            case "Streaming": return "Continuous";
            default: return "Unknown";
        }
    }
    
    private String getReliability(String method) {
        switch (method) {
            case "Real-time": return "95%";
            case "Scheduled": return "99%";
            case "On-demand": return "98%";
            case "Batch": return "99.5%";
            case "Streaming": return "97%";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Low", "Medium", "High", "Critical", "Info"})
    @DisplayName("Generate reports with different priority levels")
    @Story("Report priority levels")
    public void shouldGenerateReportsWithDifferentPriorityLevels(String priority) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Priority Level Configuration (JSON)
        String priorityConfig = "{\n" +
                "  \"priorityLevel\": \"" + priority + "\",\n" +
                "  \"reportName\": \"Priority: " + priority + "\",\n" +
                "  \"processingTime\": \"" + getProcessingTime(priority) + "\",\n" +
                "  \"resourceAllocation\": \"" + getResourceAllocation(priority) + "\",\n" +
                "  \"notificationLevel\": \"" + getNotificationLevel(priority) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Priority Level Dashboard (HTML)
        String priorityDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Priority Level Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#d32f2f;}\n" +
                ".priority{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".processing{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".resource{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".notification{font-size:16px;font-weight:bold;color:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>⚡ Priority Level Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Priority: " + priority + " <span class=\"priority\">" + priority + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Priority Level:</strong></span>\n" +
                "<span class=\"priority\">" + priority + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Processing Time:</strong></span>\n" +
                "<span class=\"processing\">" + getProcessingTime(priority) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Resource Allocation:</strong></span>\n" +
                "<span class=\"resource\">" + getResourceAllocation(priority) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Notification Level:</strong></span>\n" +
                "<span class=\"notification\">" + getNotificationLevel(priority) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Priority</h3>\n" +
                "<p>" + priority + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Processing Time</h3>\n" +
                "<p>" + getProcessingTime(priority) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Resources</h3>\n" +
                "<p>" + getResourceAllocation(priority) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Priority Level Data (CSV)
        String priorityData = "priority_level,processing_time,resource_allocation,notification_level,timestamp\n" +
                priority + "," + getProcessingTime(priority) + "," + getResourceAllocation(priority) + "," + getNotificationLevel(priority) + "," + timestamp;
        
        // Priority Level Log (Plain Text)
        String priorityLog = "PRIORITY LEVEL REPORT GENERATION LOG\n" +
                "====================================\n\n" +
                "Priority Level: " + priority + "\n" +
                "Processing Time: " + getProcessingTime(priority) + "\n" +
                "Resource Allocation: " + getResourceAllocation(priority) + "\n" +
                "Notification Level: " + getNotificationLevel(priority) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Priority level report generation completed";
        
        Allure.attachment("Priority Level Configuration (JSON)", priorityConfig);
        Allure.attachment("Priority Level Dashboard (HTML)", priorityDashboard);
        Allure.attachment("Priority Level Data (CSV)", priorityData);
        Allure.attachment("Priority Level Log (Text)", priorityLog);
        
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
        steps.createIssueWithTitle("Priority: " + priority);
        steps.shouldSeeIssueWithTitle("Priority: " + priority);
    }
    
    private String getProcessingTime(String priority) {
        switch (priority) {
            case "Low": return "5-10 min";
            case "Medium": return "2-5 min";
            case "High": return "1-2 min";
            case "Critical": return "< 30s";
            case "Info": return "10-15 min";
            default: return "Unknown";
        }
    }
    
    private String getResourceAllocation(String priority) {
        switch (priority) {
            case "Low": return "Low";
            case "Medium": return "Medium";
            case "High": return "High";
            case "Critical": return "Maximum";
            case "Info": return "Minimal";
            default: return "Unknown";
        }
    }
    
    private String getNotificationLevel(String priority) {
        switch (priority) {
            case "Low": return "Email";
            case "Medium": return "Email + SMS";
            case "High": return "Email + SMS + Push";
            case "Critical": return "All Channels";
            case "Info": return "Dashboard Only";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"English", "Spanish", "French", "German", "Chinese"})
    @DisplayName("Generate reports in different languages")
    @Story("Multi-language reporting")
    public void shouldGenerateReportsInDifferentLanguages(String language) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Language Configuration (JSON)
        String languageConfig = "{\n" +
                "  \"language\": \"" + language + "\",\n" +
                "  \"reportName\": \"Language: " + language + "\",\n" +
                "  \"locale\": \"" + getLocale(language) + "\",\n" +
                "  \"characterSet\": \"" + getCharacterSet(language) + "\",\n" +
                "  \"textDirection\": \"" + getTextDirection(language) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Language Dashboard (HTML)
        String languageDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Language Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".report{background:#e0f2f1;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #009688;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".report-name{font-size:20px;font-weight:bold;color:#00695c;}\n" +
                ".language{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#009688;}\n" +
                ".locale{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".charset{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".direction{font-size:16px;font-weight:bold;color:#009688;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🌐 Language Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"report\">\n" +
                "<h2 class=\"report-name\">Language: " + language + " <span class=\"language\">" + language + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Language:</strong></span>\n" +
                "<span class=\"language\">" + language + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Locale:</strong></span>\n" +
                "<span class=\"locale\">" + getLocale(language) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Character Set:</strong></span>\n" +
                "<span class=\"charset\">" + getCharacterSet(language) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Text Direction:</strong></span>\n" +
                "<span class=\"direction\">" + getTextDirection(language) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Language</h3>\n" +
                "<p>" + language + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Locale</h3>\n" +
                "<p>" + getLocale(language) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Direction</h3>\n" +
                "<p>" + getTextDirection(language) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Language Data (CSV)
        String languageData = "language,locale,character_set,text_direction,timestamp\n" +
                language + "," + getLocale(language) + "," + getCharacterSet(language) + "," + getTextDirection(language) + "," + timestamp;
        
        // Language Log (Plain Text)
        String languageLog = "LANGUAGE REPORT GENERATION LOG\n" +
                "=============================\n\n" +
                "Language: " + language + "\n" +
                "Locale: " + getLocale(language) + "\n" +
                "Character Set: " + getCharacterSet(language) + "\n" +
                "Text Direction: " + getTextDirection(language) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Language report generation completed";
        
        Allure.attachment("Language Configuration (JSON)", languageConfig);
        Allure.attachment("Language Dashboard (HTML)", languageDashboard);
        Allure.attachment("Language Data (CSV)", languageData);
        Allure.attachment("Language Log (Text)", languageLog);
        
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
        steps.createIssueWithTitle("Language: " + language);
        steps.shouldSeeIssueWithTitle("Language: " + language);
    }
    
    private String getLocale(String language) {
        switch (language) {
            case "English": return "en-US";
            case "Spanish": return "es-ES";
            case "French": return "fr-FR";
            case "German": return "de-DE";
            case "Chinese": return "zh-CN";
            default: return "en-US";
        }
    }
    
    private String getCharacterSet(String language) {
        switch (language) {
            case "English": return "UTF-8";
            case "Spanish": return "UTF-8";
            case "French": return "UTF-8";
            case "German": return "UTF-8";
            case "Chinese": return "UTF-8";
            default: return "UTF-8";
        }
    }
    
    private String getTextDirection(String language) {
        switch (language) {
            case "English": return "LTR";
            case "Spanish": return "LTR";
            case "French": return "LTR";
            case "German": return "LTR";
            case "Chinese": return "LTR";
            default: return "LTR";
        }
    }
}
