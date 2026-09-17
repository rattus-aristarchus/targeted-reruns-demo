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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.InputStream;

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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Logging System Configuration (JSON)
        String loggingConfig = "{\n" +
                "  \"systemId\": \"logging-system\",\n" +
                "  \"version\": \"3.2.1\",\n" +
                "  \"appenders\": [\n" +
                "    {\"name\": \"console\", \"type\": \"ConsoleAppender\", \"level\": \"INFO\", \"status\": \"active\"},\n" +
                "    {\"name\": \"file\", \"type\": \"FileAppender\", \"level\": \"DEBUG\", \"file\": \"/var/log/app.log\", \"status\": \"active\"},\n" +
                "    {\"name\": \"rolling\", \"type\": \"RollingFileAppender\", \"level\": \"WARN\", \"file\": \"/var/log/rolling.log\", \"status\": \"active\"}\n" +
                "  ],\n" +
                "  \"loggers\": [\n" +
                "    {\"name\": \"com.example\", \"level\": \"DEBUG\", \"additivity\": false},\n" +
                "    {\"name\": \"org.springframework\", \"level\": \"INFO\", \"additivity\": true},\n" +
                "    {\"name\": \"root\", \"level\": \"WARN\", \"additivity\": false}\n" +
                "  ],\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"initialized\"\n" +
                "}";
        
        // Logging Dashboard (HTML)
        String loggingDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Logging System Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".appender{display:flex;justify-content:space-between;align-items:center;padding:15px;margin:10px 0;border-radius:4px;}\n" +
                ".console{background:#e8f5e8;border-left:4px solid #4caf50;}\n" +
                ".file{background:#e3f2fd;border-left:4px solid #2196f3;}\n" +
                ".rolling{background:#fff3e0;border-left:4px solid #ff9800;}\n" +
                ".status{font-weight:bold;color:#2e7d32;}\n" +
                ".metrics{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".metric{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".logger{background:#f3e5f5;padding:8px;margin:5px;border-radius:4px;display:inline-block;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📝 Logging System Dashboard</h1>\n" +
                "<div class=\"appender console\">\n" +
                "<div><strong>Console Appender</strong><br/>Level: INFO</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<div class=\"appender file\">\n" +
                "<div><strong>File Appender</strong><br/>/var/log/app.log (DEBUG)</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<div class=\"appender rolling\">\n" +
                "<div><strong>Rolling File Appender</strong><br/>/var/log/rolling.log (WARN)</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<h2>Active Loggers</h2>\n" +
                "<div class=\"logger\">com.example (DEBUG)</div>\n" +
                "<div class=\"logger\">org.springframework (INFO)</div>\n" +
                "<div class=\"logger\">root (WARN)</div>\n" +
                "<div class=\"metrics\">\n" +
                "<div class=\"metric\"><h3>Logs Generated</h3><p>25,847</p></div>\n" +
                "<div class=\"metric\"><h3>Error Rate</h3><p>0.3%</p></div>\n" +
                "<div class=\"metric\"><h3>Storage Used</h3><p>2.3 GB</p></div>\n" +
                "</div>\n" +
                "<p><em>Last updated: " + timestamp + "</em></p>\n" +
                "</div></body></html>";
        
        // Logging Metrics (CSV)
        String loggingMetrics = "appender,level,logs_count,error_rate,file_size,timestamp\n" +
                "console,INFO,15420,0.1%,0MB," + timestamp + "\n" +
                "file,DEBUG,8927,0.2%,1.8GB," + timestamp + "\n" +
                "rolling,WARN,1500,0.5%,0.5GB," + timestamp;
        
        // Logging Schema (XML)
        String loggingSchema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<configuration>\n" +
                "  <appenders>\n" +
                "    <Console name=\"console\" target=\"SYSTEM_OUT\">\n" +
                "      <PatternLayout pattern=\"%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n\"/>\n" +
                "      <ThresholdFilter level=\"INFO\" onMatch=\"ACCEPT\" onMismatch=\"DENY\"/>\n" +
                "    </Console>\n" +
                "    <File name=\"file\" fileName=\"/var/log/app.log\">\n" +
                "      <PatternLayout pattern=\"%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n\"/>\n" +
                "      <ThresholdFilter level=\"DEBUG\" onMatch=\"ACCEPT\" onMismatch=\"DENY\"/>\n" +
                "    </File>\n" +
                "    <RollingFile name=\"rolling\" fileName=\"/var/log/rolling.log\"\n" +
                "                 filePattern=\"/var/log/rolling-%d{yyyy-MM-dd}-%i.log.gz\">\n" +
                "      <PatternLayout pattern=\"%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n\"/>\n" +
                "      <ThresholdFilter level=\"WARN\" onMatch=\"ACCEPT\" onMismatch=\"DENY\"/>\n" +
                "      <Policies>\n" +
                "        <TimeBasedTriggeringPolicy/>\n" +
                "        <SizeBasedTriggeringPolicy size=\"100MB\"/>\n" +
                "      </Policies>\n" +
                "      <DefaultRolloverStrategy max=\"10\"/>\n" +
                "    </RollingFile>\n" +
                "  </appenders>\n" +
                "  <loggers>\n" +
                "    <Logger name=\"com.example\" level=\"DEBUG\" additivity=\"false\">\n" +
                "      <AppenderRef ref=\"console\"/>\n" +
                "      <AppenderRef ref=\"file\"/>\n" +
                "    </Logger>\n" +
                "    <Logger name=\"org.springframework\" level=\"INFO\" additivity=\"true\">\n" +
                "      <AppenderRef ref=\"console\"/>\n" +
                "    </Logger>\n" +
                "    <Root level=\"WARN\">\n" +
                "      <AppenderRef ref=\"rolling\"/>\n" +
                "    </Root>\n" +
                "  </loggers>\n" +
                "</configuration>";
        
        // Logging Log (Plain Text)
        String loggingLog = "LOGGING SYSTEM INITIALIZATION LOG\n" +
                "===================================\n\n" +
                "System ID: logging-system\n" +
                "Version: 3.2.1\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: ✅ Initialized\n\n" +
                "APPENDER STATUS:\n" +
                "---------------\n" +
                "1. Console Appender (INFO) - ✅ Active\n" +
                "2. File Appender (/var/log/app.log, DEBUG) - ✅ Active\n" +
                "3. Rolling File Appender (/var/log/rolling.log, WARN) - ✅ Active\n\n" +
                "LOGGER CONFIGURATION:\n" +
                "--------------------\n" +
                "• com.example (DEBUG) - Console + File\n" +
                "• org.springframework (INFO) - Console only\n" +
                "• root (WARN) - Rolling file only\n\n" +
                "PERFORMANCE METRICS:\n" +
                "-------------------\n" +
                "Total Logs Generated: 25,847\n" +
                "Error Rate: 0.3%\n" +
                "Storage Used: 2.3 GB\n" +
                "Peak Throughput: 500 logs/min\n\n" +
                "RECENT ACTIVITY:\n" +
                "---------------\n" +
                "• Logging system initialized successfully\n" +
                "• All appenders connected\n" +
                "• Log files created and ready\n" +
                "• Log rotation configured";
        
        // Logging JavaScript
        String loggingJS = "// Logging System JavaScript\n" +
                "class LoggingSystem {\n" +
                "    constructor(config) {\n" +
                "        this.systemId = config.systemId;\n" +
                "        this.version = config.version;\n" +
                "        this.appenders = config.appenders;\n" +
                "        this.loggers = config.loggers;\n" +
                "        this.metrics = {\n" +
                "            totalLogs: 0,\n" +
                "            errorCount: 0,\n" +
                "            storageUsed: 0\n" +
                "        };\n" +
                "    }\n\n" +
                "    log(level, message, logger = 'root') {\n" +
                "        const logEntry = {\n" +
                "            timestamp: new Date().toISOString(),\n" +
                "            level: level.toUpperCase(),\n" +
                "            logger: logger,\n" +
                "            message: message,\n" +
                "            thread: 'main'\n" +
                "        };\n\n" +
                "        // Simulate log processing\n" +
                "        this.processLogEntry(logEntry);\n" +
                "        \n" +
                "        this.metrics.totalLogs++;\n" +
                "        if (level === 'ERROR' || level === 'FATAL') {\n" +
                "            this.metrics.errorCount++;\n" +
                "        }\n" +
                "        \n" +
                "        return logEntry;\n    }\n\n" +
                "    processLogEntry(logEntry) {\n" +
                "        // Simulate appender processing\n" +
                "        this.appenders.forEach(appender => {\n" +
                "            if (this.shouldLogToAppender(logEntry, appender)) {\n" +
                "                this.writeToAppender(logEntry, appender);\n" +
                "            }\n        });\n    }\n\n" +
                "    shouldLogToAppender(logEntry, appender) {\n" +
                "        const levelPriority = { 'DEBUG': 0, 'INFO': 1, 'WARN': 2, 'ERROR': 3, 'FATAL': 4 };\n" +
                "        const entryPriority = levelPriority[logEntry.level] || 0;\n" +
                "        const appenderPriority = levelPriority[appender.level] || 0;\n" +
                "        \n" +
                "        return entryPriority >= appenderPriority;\n    }\n\n" +
                "    writeToAppender(logEntry, appender) {\n" +
                "        const formattedLog = this.formatLogEntry(logEntry);\n" +
                "        \n" +
                "        switch (appender.type) {\n" +
                "            case 'ConsoleAppender':\n" +
                "                console.log(formattedLog);\n" +
                "                break;\n" +
                "            case 'FileAppender':\n" +
                "                // Simulate file writing\n" +
                "                this.metrics.storageUsed += formattedLog.length;\n" +
                "                break;\n" +
                "            case 'RollingFileAppender':\n" +
                "                // Simulate rolling file writing\n" +
                "                this.metrics.storageUsed += formattedLog.length;\n" +
                "                break;\n        }\n    }\n\n" +
                "    formatLogEntry(logEntry) {\n" +
                "        return `${logEntry.timestamp} [${logEntry.thread}] ${logEntry.level.padEnd(5)} ${logEntry.logger} - ${logEntry.message}`;\n    }\n\n" +
                "    getMetrics() {\n" +
                "        const errorRate = this.metrics.totalLogs > 0 ? \n" +
                "            (this.metrics.errorCount / this.metrics.totalLogs * 100).toFixed(1) + '%' : '0%';\n" +
                "        \n" +
                "        return {\n" +
                "            ...this.metrics,\n" +
                "            errorRate,\n" +
                "            storageUsedMB: Math.round(this.metrics.storageUsed / 1024 / 1024 * 100) / 100 + ' MB'\n" +
                "        };\n    }\n}\n\n" +
                "// Usage example\n" +
                "const loggingSystem = new LoggingSystem(loggingConfig);\n" +
                "loggingSystem.log('INFO', 'Application started', 'com.example');\n" +
                "loggingSystem.log('ERROR', 'Database connection failed', 'com.example.db');\n" +
                "console.log('Metrics:', loggingSystem.getMetrics());";
        
        Allure.attachment("Logging Configuration (JSON)", loggingConfig);
        Allure.attachment("Logging Dashboard (HTML)", loggingDashboard);
        Allure.attachment("Logging Metrics (CSV)", loggingMetrics);
        Allure.attachment("Logging Schema (XML)", loggingSchema);
        Allure.attachment("Logging Log (Text)", loggingLog);
        Allure.attachment("Logging JavaScript", loggingJS);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Logging System Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Logging System Initialization Test");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Logging System Initialization Test");
    }

    @ParameterizedTest
    @ValueSource(strings = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"})
    @DisplayName("Test different log levels")
    @Story("Log levels")
    public void shouldTestDifferentLogLevels(String level) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Level Configuration (JSON)
        String levelConfig = "{\n" +
                "  \"level\": \"" + level + "\",\n" +
                "  \"priority\": " + getLevelPriority(level) + ",\n" +
                "  \"color\": \"" + getLevelColor(level) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Log Level Report (HTML)
        String levelReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Level Report</title></head>\n" +
                "<body><h1>Log Level Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Level Details</h2>\n" +
                "<ul>\n" +
                "<li>Level: " + level + "</li>\n" +
                "<li>Priority: " + getLevelPriority(level) + "</li>\n" +
                "<li>Color: " + getLevelColor(level) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Log Level Data (CSV)
        String levelData = "level,priority,color,status,timestamp\n" +
                level + "," + getLevelPriority(level) + "," + getLevelColor(level) + ",active," + timestamp;
        
        // Log Level Log (Plain Text)
        String levelLog = "LOG LEVEL PROCESSING LOG\n" +
                "=========================\n\n" +
                "Level: " + level + "\n" +
                "Priority: " + getLevelPriority(level) + "\n" +
                "Color: " + getLevelColor(level) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log level validation completed";
        
        Allure.attachment("Log Level Configuration (JSON)", levelConfig);
        Allure.attachment("Log Level Report (HTML)", levelReport);
        Allure.attachment("Log Level Data (CSV)", levelData);
        Allure.attachment("Log Level Log (Text)", levelLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Level Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Level: " + level);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Level: " + level);
    }
    
    private int getLevelPriority(String level) {
        switch (level) {
            case "DEBUG": return 0;
            case "INFO": return 1;
            case "WARN": return 2;
            case "ERROR": return 3;
            case "FATAL": return 4;
            default: return 0;
        }
    }
    
    private String getLevelColor(String level) {
        switch (level) {
            case "DEBUG": return "#9E9E9E";
            case "INFO": return "#2196F3";
            case "WARN": return "#FF9800";
            case "ERROR": return "#F44336";
            case "FATAL": return "#9C27B0";
            default: return "#000000";
        }
    }

    @ParameterizedTest
    @CsvSource({"Application, app.log", "Access, access.log", "Error, error.log", "Security, security.log"})
    @DisplayName("Test different log categories")
    @Story("Log categories")
    public void shouldTestDifferentLogCategories(String category, String filename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Category Configuration (JSON)
        String categoryConfig = "{\n" +
                "  \"category\": \"" + category + "\",\n" +
                "  \"filename\": \"" + filename + "\",\n" +
                "  \"path\": \"/var/log/" + filename + "\",\n" +
                "  \"rotation\": \"daily\",\n" +
                "  \"retention\": \"30 days\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Category Report (HTML)
        String categoryReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Category Report</title></head>\n" +
                "<body><h1>Log Category Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Category Details</h2>\n" +
                "<ul>\n" +
                "<li>Category: " + category + "</li>\n" +
                "<li>Filename: " + filename + "</li>\n" +
                "<li>Path: /var/log/" + filename + "</li>\n" +
                "<li>Rotation: Daily</li>\n" +
                "<li>Retention: 30 days</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Category Data (CSV)
        String categoryData = "category,filename,path,rotation,retention,status,timestamp\n" +
                category + "," + filename + ",/var/log/" + filename + ",daily,30 days,active," + timestamp;
        
        // Category Log (Plain Text)
        String categoryLog = "LOG CATEGORY PROCESSING LOG\n" +
                "=============================\n\n" +
                "Category: " + category + "\n" +
                "Filename: " + filename + "\n" +
                "Path: /var/log/" + filename + "\n" +
                "Rotation: Daily\n" +
                "Retention: 30 days\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log category validation completed";
        
        Allure.attachment("Category Configuration (JSON)", categoryConfig);
        Allure.attachment("Category Report (HTML)", categoryReport);
        Allure.attachment("Category Data (CSV)", categoryData);
        Allure.attachment("Category Log (Text)", categoryLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Category Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Category: " + category + " -> " + filename);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Category: " + category + " -> " + filename);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Console", "File", "Database", "Syslog", "Remote Server"})
    @DisplayName("Test different log destinations")
    @Story("Log destinations")
    public void shouldTestDifferentLogDestinations(String destination) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Destination Configuration (JSON)
        String destinationConfig = "{\n" +
                "  \"destination\": \"" + destination + "\",\n" +
                "  \"type\": \"" + getDestinationType(destination) + "\",\n" +
                "  \"enabled\": true,\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Destination Report (HTML)
        String destinationReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Destination Report</title></head>\n" +
                "<body><h1>Log Destination Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Destination Details</h2>\n" +
                "<ul>\n" +
                "<li>Destination: " + destination + "</li>\n" +
                "<li>Type: " + getDestinationType(destination) + "</li>\n" +
                "<li>Enabled: true</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Destination Data (CSV)
        String destinationData = "destination,type,enabled,status,timestamp\n" +
                destination + "," + getDestinationType(destination) + ",true,active," + timestamp;
        
        // Destination Log (Plain Text)
        String destinationLog = "LOG DESTINATION PROCESSING LOG\n" +
                "===============================\n\n" +
                "Destination: " + destination + "\n" +
                "Type: " + getDestinationType(destination) + "\n" +
                "Enabled: true\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log destination validation completed";
        
        Allure.attachment("Destination Configuration (JSON)", destinationConfig);
        Allure.attachment("Destination Report (HTML)", destinationReport);
        Allure.attachment("Destination Data (CSV)", destinationData);
        Allure.attachment("Destination Log (Text)", destinationLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Destination Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Destination: " + destination);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Destination: " + destination);
    }
    
    private String getDestinationType(String destination) {
        switch (destination) {
            case "Console": return "output";
            case "File": return "storage";
            case "Database": return "persistence";
            case "Syslog": return "system";
            case "Remote Server": return "network";
            default: return "unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"JSON", "XML", "Plain Text", "Structured", "Binary"})
    @DisplayName("Test different log formats")
    @Story("Log formats")
    public void shouldTestDifferentLogFormats(String format) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Format Configuration (JSON)
        String formatConfig = "{\n" +
                "  \"format\": \"" + format + "\",\n" +
                "  \"mimeType\": \"" + getFormatMimeType(format) + "\",\n" +
                "  \"encoding\": \"UTF-8\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Format Report (HTML)
        String formatReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Format Report</title></head>\n" +
                "<body><h1>Log Format Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Format Details</h2>\n" +
                "<ul>\n" +
                "<li>Format: " + format + "</li>\n" +
                "<li>MIME Type: " + getFormatMimeType(format) + "</li>\n" +
                "<li>Encoding: UTF-8</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Format Data (CSV)
        String formatData = "format,mime_type,encoding,status,timestamp\n" +
                format + "," + getFormatMimeType(format) + ",UTF-8,active," + timestamp;
        
        // Format Log (Plain Text)
        String formatLog = "LOG FORMAT PROCESSING LOG\n" +
                "==========================\n\n" +
                "Format: " + format + "\n" +
                "MIME Type: " + getFormatMimeType(format) + "\n" +
                "Encoding: UTF-8\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log format validation completed";
        
        Allure.attachment("Format Configuration (JSON)", formatConfig);
        Allure.attachment("Format Report (HTML)", formatReport);
        Allure.attachment("Format Data (CSV)", formatData);
        Allure.attachment("Format Log (Text)", formatLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Format Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Format: " + format);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Format: " + format);
    }
    
    private String getFormatMimeType(String format) {
        switch (format) {
            case "JSON": return "application/json";
            case "XML": return "application/xml";
            case "Plain Text": return "text/plain";
            case "Structured": return "application/structured";
            case "Binary": return "application/octet-stream";
            default: return "text/plain";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Timestamp", "Log Level", "Source", "Message", "Stack Trace"})
    @DisplayName("Test different log fields")
    @Story("Log fields")
    public void shouldTestDifferentLogFields(String field) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Field Configuration (JSON)
        String fieldConfig = "{\n" +
                "  \"field\": \"" + field + "\",\n" +
                "  \"type\": \"" + getFieldType(field) + "\",\n" +
                "  \"required\": " + isFieldRequired(field) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Field Report (HTML)
        String fieldReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Field Report</title></head>\n" +
                "<body><h1>Log Field Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Field Details</h2>\n" +
                "<ul>\n" +
                "<li>Field: " + field + "</li>\n" +
                "<li>Type: " + getFieldType(field) + "</li>\n" +
                "<li>Required: " + isFieldRequired(field) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Field Data (CSV)
        String fieldData = "field,type,required,status,timestamp\n" +
                field + "," + getFieldType(field) + "," + isFieldRequired(field) + ",active," + timestamp;
        
        // Field Log (Plain Text)
        String fieldLog = "LOG FIELD PROCESSING LOG\n" +
                "=========================\n\n" +
                "Field: " + field + "\n" +
                "Type: " + getFieldType(field) + "\n" +
                "Required: " + isFieldRequired(field) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log field validation completed";
        
        Allure.attachment("Field Configuration (JSON)", fieldConfig);
        Allure.attachment("Field Report (HTML)", fieldReport);
        Allure.attachment("Field Data (CSV)", fieldData);
        Allure.attachment("Field Log (Text)", fieldLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Field Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Field: " + field);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Field: " + field);
    }
    
    private String getFieldType(String field) {
        switch (field) {
            case "Timestamp": return "datetime";
            case "Log Level": return "enum";
            case "Source": return "string";
            case "Message": return "text";
            case "Stack Trace": return "text";
            default: return "string";
        }
    }
    
    private boolean isFieldRequired(String field) {
        switch (field) {
            case "Timestamp": return true;
            case "Log Level": return true;
            case "Source": return false;
            case "Message": return true;
            case "Stack Trace": return false;
            default: return false;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rotation", "Compression", "Archiving", "Cleanup", "Retention"})
    @DisplayName("Test different log management features")
    @Story("Log management")
    public void shouldTestDifferentLogManagementFeatures(String feature) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Management Configuration (JSON)
        String managementConfig = "{\n" +
                "  \"feature\": \"" + feature + "\",\n" +
                "  \"enabled\": true,\n" +
                "  \"schedule\": \"" + getFeatureSchedule(feature) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Management Report (HTML)
        String managementReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Management Report</title></head>\n" +
                "<body><h1>Log Management Processing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Feature Details</h2>\n" +
                "<ul>\n" +
                "<li>Feature: " + feature + "</li>\n" +
                "<li>Enabled: true</li>\n" +
                "<li>Schedule: " + getFeatureSchedule(feature) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Management Data (CSV)
        String managementData = "feature,enabled,schedule,status,timestamp\n" +
                feature + ",true," + getFeatureSchedule(feature) + ",active," + timestamp;
        
        // Management Log (Plain Text)
        String managementLog = "LOG MANAGEMENT PROCESSING LOG\n" +
                "===============================\n\n" +
                "Feature: " + feature + "\n" +
                "Enabled: true\n" +
                "Schedule: " + getFeatureSchedule(feature) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log management feature validation completed";
        
        Allure.attachment("Management Configuration (JSON)", managementConfig);
        Allure.attachment("Management Report (HTML)", managementReport);
        Allure.attachment("Management Data (CSV)", managementData);
        Allure.attachment("Management Log (Text)", managementLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Management Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Management: " + feature);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Management: " + feature);
    }
    
    private String getFeatureSchedule(String feature) {
        switch (feature) {
            case "Rotation": return "daily";
            case "Compression": return "hourly";
            case "Archiving": return "weekly";
            case "Cleanup": return "daily";
            case "Retention": return "monthly";
            default: return "manual";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Real-time", "Batch", "Scheduled", "On-demand", "Event-driven"})
    @DisplayName("Test different log processing modes")
    @Story("Log processing")
    public void shouldTestDifferentLogProcessingModes(String mode) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Processing Configuration (JSON)
        String processingConfig = "{\n" +
                "  \"mode\": \"" + mode + "\",\n" +
                "  \"latency\": \"" + getModeLatency(mode) + "\",\n" +
                "  \"throughput\": \"" + getModeThroughput(mode) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Processing Report (HTML)
        String processingReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Processing Report</title></head>\n" +
                "<body><h1>Log Processing Mode Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Mode Details</h2>\n" +
                "<ul>\n" +
                "<li>Mode: " + mode + "</li>\n" +
                "<li>Latency: " + getModeLatency(mode) + "</li>\n" +
                "<li>Throughput: " + getModeThroughput(mode) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Processing Data (CSV)
        String processingData = "mode,latency,throughput,status,timestamp\n" +
                mode + "," + getModeLatency(mode) + "," + getModeThroughput(mode) + ",active," + timestamp;
        
        // Processing Log (Plain Text)
        String processingLog = "LOG PROCESSING MODE LOG\n" +
                "========================\n\n" +
                "Mode: " + mode + "\n" +
                "Latency: " + getModeLatency(mode) + "\n" +
                "Throughput: " + getModeThroughput(mode) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log processing mode validation completed";
        
        Allure.attachment("Processing Configuration (JSON)", processingConfig);
        Allure.attachment("Processing Report (HTML)", processingReport);
        Allure.attachment("Processing Data (CSV)", processingData);
        Allure.attachment("Processing Log (Text)", processingLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Processing Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Processing Mode: " + mode);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Processing Mode: " + mode);
    }
    
    private String getModeLatency(String mode) {
        switch (mode) {
            case "Real-time": return "< 1ms";
            case "Batch": return "5-15 minutes";
            case "Scheduled": return "1-60 minutes";
            case "On-demand": return "immediate";
            case "Event-driven": return "< 100ms";
            default: return "unknown";
        }
    }
    
    private String getModeThroughput(String mode) {
        switch (mode) {
            case "Real-time": return "1000+ logs/sec";
            case "Batch": return "10,000+ logs/batch";
            case "Scheduled": return "500 logs/min";
            case "On-demand": return "100 logs/sec";
            case "Event-driven": return "500+ logs/sec";
            default: return "unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Filtering", "Searching", "Aggregation", "Analysis", "Reporting"})
    @DisplayName("Test different log analysis features")
    @Story("Log analysis")
    public void shouldTestDifferentLogAnalysisFeatures(String feature) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Log Analysis Configuration (JSON)
        String analysisConfig = "{\n" +
                "  \"feature\": \"" + feature + "\",\n" +
                "  \"complexity\": \"" + getFeatureComplexity(feature) + "\",\n" +
                "  \"performance\": \"" + getFeaturePerformance(feature) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Analysis Report (HTML)
        String analysisReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Log Analysis Report</title></head>\n" +
                "<body><h1>Log Analysis Feature Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Feature Details</h2>\n" +
                "<ul>\n" +
                "<li>Feature: " + feature + "</li>\n" +
                "<li>Complexity: " + getFeatureComplexity(feature) + "</li>\n" +
                "<li>Performance: " + getFeaturePerformance(feature) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Analysis Data (CSV)
        String analysisData = "feature,complexity,performance,status,timestamp\n" +
                feature + "," + getFeatureComplexity(feature) + "," + getFeaturePerformance(feature) + ",active," + timestamp;
        
        // Analysis Log (Plain Text)
        String analysisLog = "LOG ANALYSIS FEATURE LOG\n" +
                "==========================\n\n" +
                "Feature: " + feature + "\n" +
                "Complexity: " + getFeatureComplexity(feature) + "\n" +
                "Performance: " + getFeaturePerformance(feature) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Log analysis feature validation completed";
        
        Allure.attachment("Analysis Configuration (JSON)", analysisConfig);
        Allure.attachment("Analysis Report (HTML)", analysisReport);
        Allure.attachment("Analysis Data (CSV)", analysisData);
        Allure.attachment("Analysis Log (Text)", analysisLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Log Analysis Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Log Analysis: " + feature);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Log Analysis: " + feature);
    }
    
    private String getFeatureComplexity(String feature) {
        switch (feature) {
            case "Filtering": return "Low";
            case "Searching": return "Medium";
            case "Aggregation": return "High";
            case "Analysis": return "High";
            case "Reporting": return "Medium";
            default: return "Unknown";
        }
    }
    
    private String getFeaturePerformance(String feature) {
        switch (feature) {
            case "Filtering": return "Fast";
            case "Searching": return "Medium";
            case "Aggregation": return "Slow";
            case "Analysis": return "Slow";
            case "Reporting": return "Medium";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Performance", "Security", "Compliance", "Audit", "Debugging"})
    @DisplayName("Test different logging use cases")
    @Story("Logging use cases")
    public void shouldTestDifferentLoggingUseCases(String useCase) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Logging Use Case Configuration (JSON)
        String useCaseConfig = "{\n" +
                "  \"useCase\": \"" + useCase + "\",\n" +
                "  \"priority\": \"" + getUseCasePriority(useCase) + "\",\n" +
                "  \"retention\": \"" + getUseCaseRetention(useCase) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Use Case Report (HTML)
        String useCaseReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Logging Use Case Report</title></head>\n" +
                "<body><h1>Logging Use Case Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Use Case Details</h2>\n" +
                "<ul>\n" +
                "<li>Use Case: " + useCase + "</li>\n" +
                "<li>Priority: " + getUseCasePriority(useCase) + "</li>\n" +
                "<li>Retention: " + getUseCaseRetention(useCase) + "</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Use Case Data (CSV)
        String useCaseData = "use_case,priority,retention,status,timestamp\n" +
                useCase + "," + getUseCasePriority(useCase) + "," + getUseCaseRetention(useCase) + ",active," + timestamp;
        
        // Use Case Log (Plain Text)
        String useCaseLog = "LOGGING USE CASE LOG\n" +
                "====================\n\n" +
                "Use Case: " + useCase + "\n" +
                "Priority: " + getUseCasePriority(useCase) + "\n" +
                "Retention: " + getUseCaseRetention(useCase) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Logging use case validation completed";
        
        Allure.attachment("Use Case Configuration (JSON)", useCaseConfig);
        Allure.attachment("Use Case Report (HTML)", useCaseReport);
        Allure.attachment("Use Case Data (CSV)", useCaseData);
        Allure.attachment("Use Case Log (Text)", useCaseLog);
        
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Logging Use Case Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Logging Use Case: " + useCase);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Logging Use Case: " + useCase);
    }
    
    private String getUseCasePriority(String useCase) {
        switch (useCase) {
            case "Performance": return "High";
            case "Security": return "Critical";
            case "Compliance": return "High";
            case "Audit": return "Medium";
            case "Debugging": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getUseCaseRetention(String useCase) {
        switch (useCase) {
            case "Performance": return "30 days";
            case "Security": return "7 years";
            case "Compliance": return "5 years";
            case "Audit": return "3 years";
            case "Debugging": return "7 days";
            default: return "Unknown";
        }
    }
}
