package io.qameta.allure;

import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import io.qameta.allure.Allure;
import java.io.InputStream;

@Layer("rest")
@Owner("notification-api-team")
@Feature("Notification API")
public class NotificationApiTest {

    private final RestSteps steps = new RestSteps();

    @ParameterizedTest(name = "Send notification via: {0}")
    @ValueSource(strings = {"email", "sms", "push", "whatsapp", "telegram", "slack", "discord", "teams"})
    @TM4J("NA-T1")
    @Story("Send notifications via different channels")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-1")})
    public void shouldSendNotificationViaChannel(String channel) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Notification Channel Configuration (JSON)
        String channelConfig = "{\n" +
                "  \"channel\": \"" + channel + "\",\n" +
                "  \"type\": \"" + getChannelType(channel) + "\",\n" +
                "  \"endpoint\": \"" + getChannelEndpoint(channel) + "\",\n" +
                "  \"priority\": \"" + getChannelPriority(channel) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Channel Report (HTML)
        String channelReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Channel Report</title></head>\n" +
                "<body><h1>Notification Channel Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Channel Details</h2>\n" +
                "<ul>\n" +
                "<li>Channel: " + channel + "</li>\n" +
                "<li>Type: " + getChannelType(channel) + "</li>\n" +
                "<li>Endpoint: " + getChannelEndpoint(channel) + "</li>\n" +
                "<li>Priority: " + getChannelPriority(channel) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Channel Data (CSV)
        String channelData = "channel,type,endpoint,priority,status,timestamp\n" +
                channel + "," + getChannelType(channel) + "," + getChannelEndpoint(channel) + "," + getChannelPriority(channel) + ",testing," + timestamp;
        
        // Channel Log (Plain Text)
        String channelLog = "NOTIFICATION CHANNEL TESTING LOG\n" +
                "==================================\n\n" +
                "Channel: " + channel + "\n" +
                "Type: " + getChannelType(channel) + "\n" +
                "Endpoint: " + getChannelEndpoint(channel) + "\n" +
                "Priority: " + getChannelPriority(channel) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Notification channel validation completed";
        
        Allure.attachment("Channel Configuration (JSON)", channelConfig);
        Allure.attachment("Channel Report (HTML)", channelReport);
        Allure.attachment("Channel Data (CSV)", channelData);
        Allure.attachment("Channel Log (Text)", channelLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Send Notification via: " + channel);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Send Notification via: " + channel);
    }
    
    private String getChannelType(String channel) {
        switch (channel) {
            case "email": return "Email";
            case "sms": return "SMS";
            case "push": return "Push";
            case "whatsapp": return "Messaging";
            case "telegram": return "Messaging";
            case "slack": return "Team";
            case "discord": return "Team";
            case "teams": return "Team";
            default: return "Unknown";
        }
    }
    
    private String getChannelEndpoint(String channel) {
        switch (channel) {
            case "email": return "smtp://mail.example.com:587";
            case "sms": return "https://api.twilio.com/v1/Messages";
            case "push": return "https://fcm.googleapis.com/fcm/send";
            case "whatsapp": return "https://graph.facebook.com/v17.0/messages";
            case "telegram": return "https://api.telegram.org/bot{token}/sendMessage";
            case "slack": return "https://hooks.slack.com/services/...";
            case "discord": return "https://discord.com/api/webhooks/...";
            case "teams": return "https://outlook.office.com/webhook/...";
            default: return "Unknown";
        }
    }
    
    private String getChannelPriority(String channel) {
        switch (channel) {
            case "email": return "Medium";
            case "sms": return "High";
            case "push": return "High";
            case "whatsapp": return "Medium";
            case "telegram": return "Medium";
            case "slack": return "Low";
            case "discord": return "Low";
            case "teams": return "Medium";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Create notification template: {0}")
    @ValueSource(strings = {"Welcome", "Order Update", "Price Alert", "Stock Alert", "Promotion", "Newsletter", "Security Alert", "Maintenance"})
    @TM4J("NA-T2")
    @Story("Create notification templates")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-2")})
    public void shouldCreateNotificationTemplate(String templateName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Template Configuration (JSON)
        String templateConfig = "{\n" +
                "  \"templateName\": \"" + templateName + "\",\n" +
                "  \"category\": \"" + getTemplateCategory(templateName) + "\",\n" +
                "  \"priority\": \"" + getTemplatePriority(templateName) + "\",\n" +
                "  \"channels\": [\n" +
                "    \"" + getTemplateChannels(templateName) + "\"\n" +
                "  ],\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Template Report (HTML)
        String templateReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Template Report</title></head>\n" +
                "<body><h1>Notification Template Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Template Details</h2>\n" +
                "<ul>\n" +
                "<li>Template: " + templateName + "</li>\n" +
                "<li>Category: " + getTemplateCategory(templateName) + "</li>\n" +
                "<li>Priority: " + getTemplatePriority(templateName) + "</li>\n" +
                "<li>Channels: " + getTemplateChannels(templateName) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Template Data (CSV)
        String templateData = "template_name,category,priority,channels,status,timestamp\n" +
                templateName + "," + getTemplateCategory(templateName) + "," + getTemplatePriority(templateName) + "," + getTemplateChannels(templateName) + ",testing," + timestamp;
        
        // Template Log (Plain Text)
        String templateLog = "NOTIFICATION TEMPLATE TESTING LOG\n" +
                "===================================\n\n" +
                "Template: " + templateName + "\n" +
                "Category: " + getTemplateCategory(templateName) + "\n" +
                "Priority: " + getTemplatePriority(templateName) + "\n" +
                "Channels: " + getTemplateChannels(templateName) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Notification template validation completed";
        
        Allure.attachment("Template Configuration (JSON)", templateConfig);
        Allure.attachment("Template Report (HTML)", templateReport);
        Allure.attachment("Template Data (CSV)", templateData);
        Allure.attachment("Template Log (Text)", templateLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Notification Template: " + templateName);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification Template: " + templateName);
    }
    
    private String getTemplateCategory(String templateName) {
        switch (templateName) {
            case "Welcome": return "User Onboarding";
            case "Order Update": return "E-commerce";
            case "Price Alert": return "E-commerce";
            case "Stock Alert": return "Inventory";
            case "Promotion": return "Marketing";
            case "Newsletter": return "Marketing";
            case "Security Alert": return "Security";
            case "Maintenance": return "System";
            default: return "Unknown";
        }
    }
    
    private String getTemplatePriority(String templateName) {
        switch (templateName) {
            case "Welcome": return "Low";
            case "Order Update": return "Medium";
            case "Price Alert": return "High";
            case "Stock Alert": return "High";
            case "Promotion": return "Low";
            case "Newsletter": return "Low";
            case "Security Alert": return "Critical";
            case "Maintenance": return "Medium";
            default: return "Unknown";
        }
    }
    
    private String getTemplateChannels(String templateName) {
        switch (templateName) {
            case "Welcome": return "email,push";
            case "Order Update": return "email,sms";
            case "Price Alert": return "email,push,sms";
            case "Stock Alert": return "email,sms";
            case "Promotion": return "email,push";
            case "Newsletter": return "email";
            case "Security Alert": return "email,sms,push";
            case "Maintenance": return "email,push";
            default: return "email";
        }
    }

    @ParameterizedTest(name = "Get notification by ID: {0}")
    @ValueSource(ints = {81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95})
    @TM4J("NA-T3")
    @Story("Retrieve notification by ID")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-3")})
    public void shouldGetNotificationById(int notificationId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Notification ID Configuration (JSON)
        String notificationConfig = "{\n" +
                "  \"notificationId\": " + notificationId + ",\n" +
                "  \"status\": \"" + getNotificationStatus(notificationId) + "\",\n" +
                "  \"priority\": \"" + getNotificationPriority(notificationId) + "\",\n" +
                "  \"channel\": \"" + getNotificationChannel(notificationId) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Notification Report (HTML)
        String notificationReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification ID Report</title></head>\n" +
                "<body><h1>Notification ID Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Notification Details</h2>\n" +
                "<ul>\n" +
                "<li>ID: " + notificationId + "</li>\n" +
                "<li>Status: " + getNotificationStatus(notificationId) + "</li>\n" +
                "<li>Priority: " + getNotificationPriority(notificationId) + "</li>\n" +
                "<li>Channel: " + getNotificationChannel(notificationId) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Notification Data (CSV)
        String notificationData = "notification_id,status,priority,channel,timestamp\n" +
                notificationId + "," + getNotificationStatus(notificationId) + "," + getNotificationPriority(notificationId) + "," + getNotificationChannel(notificationId) + "," + timestamp;
        
        // Notification Log (Plain Text)
        String notificationLog = "NOTIFICATION ID TESTING LOG\n" +
                "===========================\n\n" +
                "Notification ID: " + notificationId + "\n" +
                "Status: " + getNotificationStatus(notificationId) + "\n" +
                "Priority: " + getNotificationPriority(notificationId) + "\n" +
                "Channel: " + getNotificationChannel(notificationId) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Notification ID validation completed";
        
        Allure.attachment("Notification Configuration (JSON)", notificationConfig);
        Allure.attachment("Notification Report (HTML)", notificationReport);
        Allure.attachment("Notification Data (CSV)", notificationData);
        Allure.attachment("Notification Log (Text)", notificationLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Notification ID: " + notificationId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification ID: " + notificationId);
    }
    
    private String getNotificationStatus(int notificationId) {
        if (notificationId % 4 == 0) return "sent";
        if (notificationId % 4 == 1) return "delivered";
        if (notificationId % 4 == 2) return "read";
        return "pending";
    }
    
    private String getNotificationPriority(int notificationId) {
        if (notificationId % 5 == 0) return "critical";
        if (notificationId % 5 == 1) return "high";
        if (notificationId % 5 == 2) return "medium";
        if (notificationId % 5 == 3) return "low";
        return "normal";
    }
    
    private String getNotificationChannel(int notificationId) {
        String[] channels = {"email", "sms", "push", "whatsapp", "telegram", "slack", "discord", "teams"};
        return channels[notificationId % channels.length];
    }

    @ParameterizedTest(name = "Update notification {0} status to {1}")
    @CsvSource({
        "Notification A, sent",
        "Notification B, delivered",
        "Notification C, read",
        "Notification D, failed"
    })
    @TM4J("NA-T4")
    @Story("Update notification status")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-4")})
    public void shouldUpdateNotificationStatus(String notificationName, String newStatus) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Status Update Configuration (JSON)
        String statusConfig = "{\n" +
                "  \"notificationName\": \"" + notificationName + "\",\n" +
                "  \"newStatus\": \"" + newStatus + "\",\n" +
                "  \"previousStatus\": \"" + getPreviousStatus(newStatus) + "\",\n" +
                "  \"priority\": \"" + getStatusPriority(newStatus) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Status Report (HTML)
        String statusReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Status Report</title></head>\n" +
                "<body><h1>Notification Status Update Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Status Details</h2>\n" +
                "<ul>\n" +
                "<li>Notification: " + notificationName + "</li>\n" +
                "<li>New Status: " + newStatus + "</li>\n" +
                "<li>Previous Status: " + getPreviousStatus(newStatus) + "</li>\n" +
                "<li>Priority: " + getStatusPriority(newStatus) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Status Data (CSV)
        String statusData = "notification_name,new_status,previous_status,priority,timestamp\n" +
                notificationName + "," + newStatus + "," + getPreviousStatus(newStatus) + "," + getStatusPriority(newStatus) + "," + timestamp;
        
        // Status Log (Plain Text)
        String statusLog = "NOTIFICATION STATUS UPDATE LOG\n" +
                "===============================\n\n" +
                "Notification: " + notificationName + "\n" +
                "New Status: " + newStatus + "\n" +
                "Previous Status: " + getPreviousStatus(newStatus) + "\n" +
                "Priority: " + getStatusPriority(newStatus) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Notification status update validation completed";
        
        Allure.attachment("Status Configuration (JSON)", statusConfig);
        Allure.attachment("Status Report (HTML)", statusReport);
        Allure.attachment("Status Data (CSV)", statusData);
        Allure.attachment("Status Log (Text)", statusLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + notificationName + " to " + newStatus);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + notificationName + " to " + newStatus);
    }
    
    private String getPreviousStatus(String newStatus) {
        switch (newStatus) {
            case "sent": return "pending";
            case "delivered": return "sent";
            case "read": return "delivered";
            case "failed": return "pending";
            default: return "unknown";
        }
    }
    
    private String getStatusPriority(String status) {
        switch (status) {
            case "sent": return "Medium";
            case "delivered": return "High";
            case "read": return "Low";
            case "failed": return "Critical";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Delete notification with ID: {0}")
    @ValueSource(ints = {96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110})
    @TM4J("NA-T5")
    @Story("Delete notification by ID")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-5")})
    public void shouldDeleteNotificationById(int notificationId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Delete Configuration (JSON)
        String deleteConfig = "{\n" +
                "  \"notificationId\": " + notificationId + ",\n" +
                "  \"action\": \"delete\",\n" +
                "  \"reason\": \"" + getDeleteReason(notificationId) + "\",\n" +
                "  \"backup\": " + getBackupRequired(notificationId) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delete Report (HTML)
        String deleteReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Delete Report</title></head>\n" +
                "<body><h1>Notification Delete Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Delete Details</h2>\n" +
                "<ul>\n" +
                "<li>Notification ID: " + notificationId + "</li>\n" +
                "<li>Action: Delete</li>\n" +
                "<li>Reason: " + getDeleteReason(notificationId) + "</li>\n" +
                "<li>Backup Required: " + getBackupRequired(notificationId) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Delete Data (CSV)
        String deleteData = "notification_id,action,reason,backup_required,timestamp\n" +
                notificationId + ",delete," + getDeleteReason(notificationId) + "," + getBackupRequired(notificationId) + "," + timestamp;
        
        // Delete Log (Plain Text)
        String deleteLog = "NOTIFICATION DELETE TESTING LOG\n" +
                "==============================\n\n" +
                "Notification ID: " + notificationId + "\n" +
                "Action: Delete\n" +
                "Reason: " + getDeleteReason(notificationId) + "\n" +
                "Backup Required: " + getBackupRequired(notificationId) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Notification delete validation completed";
        
        Allure.attachment("Delete Configuration (JSON)", deleteConfig);
        Allure.attachment("Delete Report (HTML)", deleteReport);
        Allure.attachment("Delete Data (CSV)", deleteData);
        Allure.attachment("Delete Log (Text)", deleteLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Notification ID: " + notificationId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Notification ID: " + notificationId);
    }
    
    private String getDeleteReason(int notificationId) {
        if (notificationId % 3 == 0) return "User Request";
        if (notificationId % 3 == 1) return "Data Retention Policy";
        return "System Cleanup";
    }
    
    private boolean getBackupRequired(int notificationId) {
        return notificationId % 2 == 0;
    }

    @ParameterizedTest(name = "Search notifications by type: {0}")
    @ValueSource(strings = {"order", "product", "marketing", "security", "system", "maintenance", "update", "alert"})
    @TM4J("NA-T6")
    @Story("Search notifications by type")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-6")})
    public void shouldSearchNotificationsByType(String notificationType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Search Configuration (JSON)
        String searchConfig = "{\n" +
                "  \"searchType\": \"" + notificationType + "\",\n" +
                "  \"category\": \"" + getTypeCategory(notificationType) + "\",\n" +
                "  \"priority\": \"" + getTypePriority(notificationType) + "\",\n" +
                "  \"expectedResults\": " + getExpectedResults(notificationType) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Search Report (HTML)
        String searchReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Search Report</title></head>\n" +
                "<body><h1>Notification Search Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Search Details</h2>\n" +
                "<ul>\n" +
                "<li>Type: " + notificationType + "</li>\n" +
                "<li>Category: " + getTypeCategory(notificationType) + "</li>\n" +
                "<li>Priority: " + getTypePriority(notificationType) + "</li>\n" +
                "<li>Expected Results: " + getExpectedResults(notificationType) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Search Data (CSV)
        String searchData = "search_type,category,priority,expected_results,timestamp\n" +
                notificationType + "," + getTypeCategory(notificationType) + "," + getTypePriority(notificationType) + "," + getExpectedResults(notificationType) + "," + timestamp;
        
        // Search Log (Plain Text)
        String searchLog = "NOTIFICATION SEARCH TESTING LOG\n" +
                "===============================\n\n" +
                "Search Type: " + notificationType + "\n" +
                "Category: " + getTypeCategory(notificationType) + "\n" +
                "Priority: " + getTypePriority(notificationType) + "\n" +
                "Expected Results: " + getExpectedResults(notificationType) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Notification search validation completed";
        
        Allure.attachment("Search Configuration (JSON)", searchConfig);
        Allure.attachment("Search Report (HTML)", searchReport);
        Allure.attachment("Search Data (CSV)", searchData);
        Allure.attachment("Search Log (Text)", searchLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Search Notification Type: " + notificationType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Notification Type: " + notificationType);
    }
    
    private String getTypeCategory(String notificationType) {
        switch (notificationType) {
            case "order": return "E-commerce";
            case "product": return "E-commerce";
            case "marketing": return "Marketing";
            case "security": return "Security";
            case "system": return "System";
            case "maintenance": return "System";
            case "update": return "System";
            case "alert": return "Security";
            default: return "Unknown";
        }
    }
    
    private String getTypePriority(String notificationType) {
        switch (notificationType) {
            case "order": return "Medium";
            case "product": return "Low";
            case "marketing": return "Low";
            case "security": return "Critical";
            case "system": return "High";
            case "maintenance": return "Medium";
            case "update": return "Medium";
            case "alert": return "High";
            default: return "Unknown";
        }
    }
    
    private int getExpectedResults(String notificationType) {
        switch (notificationType) {
            case "order": return 25;
            case "product": return 15;
            case "marketing": return 50;
            case "security": return 5;
            case "system": return 10;
            case "maintenance": return 8;
            case "update": return 12;
            case "alert": return 3;
            default: return 0;
        }
    }

    @ParameterizedTest(name = "Filter notifications by priority: {0}")
    @ValueSource(strings = {"low", "medium", "high", "urgent", "critical"})
    @TM4J("NA-T7")
    @Story("Filter notifications by priority")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-7")})
    public void shouldFilterNotificationsByPriority(String priority) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Priority Filter Configuration (JSON)
        String priorityConfig = "{\n" +
                "  \"priority\": \"" + priority + "\",\n" +
                "  \"level\": " + getPriorityLevel(priority) + ",\n" +
                "  \"color\": \"" + getPriorityColor(priority) + "\",\n" +
                "  \"expectedCount\": " + getExpectedCount(priority) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Priority Report (HTML)
        String priorityReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Priority Filter Report</title></head>\n" +
                "<body><h1>Priority Filter Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Priority Details</h2>\n" +
                "<ul>\n" +
                "<li>Priority: " + priority + "</li>\n" +
                "<li>Level: " + getPriorityLevel(priority) + "</li>\n" +
                "<li>Color: " + getPriorityColor(priority) + "</li>\n" +
                "<li>Expected Count: " + getExpectedCount(priority) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Priority Data (CSV)
        String priorityData = "priority,level,color,expected_count,timestamp\n" +
                priority + "," + getPriorityLevel(priority) + "," + getPriorityColor(priority) + "," + getExpectedCount(priority) + "," + timestamp;
        
        // Priority Log (Plain Text)
        String priorityLog = "PRIORITY FILTER TESTING LOG\n" +
                "===========================\n\n" +
                "Priority: " + priority + "\n" +
                "Level: " + getPriorityLevel(priority) + "\n" +
                "Color: " + getPriorityColor(priority) + "\n" +
                "Expected Count: " + getExpectedCount(priority) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Priority filter validation completed";
        
        Allure.attachment("Priority Configuration (JSON)", priorityConfig);
        Allure.attachment("Priority Report (HTML)", priorityReport);
        Allure.attachment("Priority Data (CSV)", priorityData);
        Allure.attachment("Priority Log (Text)", priorityLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Priority: " + priority);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Priority: " + priority);
    }
    
    private int getPriorityLevel(String priority) {
        switch (priority) {
            case "low": return 1;
            case "medium": return 2;
            case "high": return 3;
            case "urgent": return 4;
            case "critical": return 5;
            default: return 0;
        }
    }
    
    private String getPriorityColor(String priority) {
        switch (priority) {
            case "low": return "#4CAF50";
            case "medium": return "#FF9800";
            case "high": return "#FF5722";
            case "urgent": return "#E91E63";
            case "critical": return "#F44336";
            default: return "#000000";
        }
    }
    
    private int getExpectedCount(String priority) {
        switch (priority) {
            case "low": return 50;
            case "medium": return 30;
            case "high": return 15;
            case "urgent": return 8;
            case "critical": return 3;
            default: return 0;
        }
    }

    @ParameterizedTest(name = "Test notification delivery for: {0}")
    @ValueSource(strings = {"immediate", "scheduled", "batch", "recurring", "conditional"})
    @TM4J("NA-T8")
    @Story("Test notification delivery methods")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-8")})
    public void shouldTestNotificationDelivery(String deliveryMethod) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Delivery Configuration (JSON)
        String deliveryConfig = "{\n" +
                "  \"deliveryMethod\": \"" + deliveryMethod + "\",\n" +
                "  \"type\": \"" + getDeliveryType(deliveryMethod) + "\",\n" +
                "  \"delay\": \"" + getDeliveryDelay(deliveryMethod) + "\",\n" +
                "  \"reliability\": \"" + getDeliveryReliability(deliveryMethod) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delivery Report (HTML)
        String deliveryReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Delivery Method Report</title></head>\n" +
                "<body><h1>Delivery Method Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Delivery Details</h2>\n" +
                "<ul>\n" +
                "<li>Method: " + deliveryMethod + "</li>\n" +
                "<li>Type: " + getDeliveryType(deliveryMethod) + "</li>\n" +
                "<li>Delay: " + getDeliveryDelay(deliveryMethod) + "</li>\n" +
                "<li>Reliability: " + getDeliveryReliability(deliveryMethod) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Delivery Data (CSV)
        String deliveryData = "delivery_method,type,delay,reliability,timestamp\n" +
                deliveryMethod + "," + getDeliveryType(deliveryMethod) + "," + getDeliveryDelay(deliveryMethod) + "," + getDeliveryReliability(deliveryMethod) + "," + timestamp;
        
        // Delivery Log (Plain Text)
        String deliveryLog = "DELIVERY METHOD TESTING LOG\n" +
                "============================\n\n" +
                "Method: " + deliveryMethod + "\n" +
                "Type: " + getDeliveryType(deliveryMethod) + "\n" +
                "Delay: " + getDeliveryDelay(deliveryMethod) + "\n" +
                "Reliability: " + getDeliveryReliability(deliveryMethod) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Delivery method validation completed";
        
        Allure.attachment("Delivery Configuration (JSON)", deliveryConfig);
        Allure.attachment("Delivery Report (HTML)", deliveryReport);
        Allure.attachment("Delivery Data (CSV)", deliveryData);
        Allure.attachment("Delivery Log (Text)", deliveryLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Delivery Method: " + deliveryMethod);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Delivery Method: " + deliveryMethod);
    }
    
    private String getDeliveryType(String deliveryMethod) {
        switch (deliveryMethod) {
            case "immediate": return "Real-time";
            case "scheduled": return "Time-based";
            case "batch": return "Bulk";
            case "recurring": return "Periodic";
            case "conditional": return "Event-driven";
            default: return "Unknown";
        }
    }
    
    private String getDeliveryDelay(String deliveryMethod) {
        switch (deliveryMethod) {
            case "immediate": return "0-5 seconds";
            case "scheduled": return "As scheduled";
            case "batch": return "5-30 minutes";
            case "recurring": return "As per schedule";
            case "conditional": return "Event dependent";
            default: return "Unknown";
        }
    }
    
    private String getDeliveryReliability(String deliveryMethod) {
        switch (deliveryMethod) {
            case "immediate": return "High";
            case "scheduled": return "Very High";
            case "batch": return "Medium";
            case "recurring": return "High";
            case "conditional": return "Medium";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Validate notification content for: {0}")
    @ValueSource(strings = {"Text Only", "HTML", "Rich Text", "Template", "Dynamic Content", "Multilingual"})
    @TM4J("NA-T9")
    @Story("Validate notification content")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-9")})
    public void shouldValidateNotificationContent(String contentType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Content Validation Configuration (JSON)
        String contentConfig = "{\n" +
                "  \"contentType\": \"" + contentType + "\",\n" +
                "  \"format\": \"" + getContentFormat(contentType) + "\",\n" +
                "  \"validation\": \"" + getContentValidation(contentType) + "\",\n" +
                "  \"complexity\": \"" + getContentComplexity(contentType) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Content Report (HTML)
        String contentReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Content Validation Report</title></head>\n" +
                "<body><h1>Content Validation Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Content Details</h2>\n" +
                "<ul>\n" +
                "<li>Type: " + contentType + "</li>\n" +
                "<li>Format: " + getContentFormat(contentType) + "</li>\n" +
                "<li>Validation: " + getContentValidation(contentType) + "</li>\n" +
                "<li>Complexity: " + getContentComplexity(contentType) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Content Data (CSV)
        String contentData = "content_type,format,validation,complexity,timestamp\n" +
                contentType + "," + getContentFormat(contentType) + "," + getContentValidation(contentType) + "," + getContentComplexity(contentType) + "," + timestamp;
        
        // Content Log (Plain Text)
        String contentLog = "CONTENT VALIDATION TESTING LOG\n" +
                "===============================\n\n" +
                "Content Type: " + contentType + "\n" +
                "Format: " + getContentFormat(contentType) + "\n" +
                "Validation: " + getContentValidation(contentType) + "\n" +
                "Complexity: " + getContentComplexity(contentType) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Content validation completed";
        
        Allure.attachment("Content Configuration (JSON)", contentConfig);
        Allure.attachment("Content Report (HTML)", contentReport);
        Allure.attachment("Content Data (CSV)", contentData);
        Allure.attachment("Content Log (Text)", contentLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Content Type: " + contentType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Content Type: " + contentType);
    }
    
    private String getContentFormat(String contentType) {
        switch (contentType) {
            case "Text Only": return "Plain Text";
            case "HTML": return "HTML";
            case "Rich Text": return "Rich Text";
            case "Template": return "Template";
            case "Dynamic Content": return "Dynamic";
            case "Multilingual": return "Multi-language";
            default: return "Unknown";
        }
    }
    
    private String getContentValidation(String contentType) {
        switch (contentType) {
            case "Text Only": return "Basic";
            case "HTML": return "HTML Parser";
            case "Rich Text": return "Rich Text Parser";
            case "Template": return "Template Engine";
            case "Dynamic Content": return "Dynamic Parser";
            case "Multilingual": return "Language Detection";
            default: return "Unknown";
        }
    }
    
    private String getContentComplexity(String contentType) {
        switch (contentType) {
            case "Text Only": return "Low";
            case "HTML": return "Medium";
            case "Rich Text": return "Medium";
            case "Template": return "High";
            case "Dynamic Content": return "High";
            case "Multilingual": return "Very High";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Test notification scheduling for: {0}")
    @ValueSource(strings = {"5 minutes", "1 hour", "1 day", "1 week", "1 month", "custom"})
    @TM4J("NA-T10")
    @Story("Test notification scheduling")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-10")})
    public void shouldTestNotificationScheduling(String schedule) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Scheduling Configuration (JSON)
        String scheduleConfig = "{\n" +
                "  \"schedule\": \"" + schedule + "\",\n" +
                "  \"type\": \"" + getScheduleType(schedule) + "\",\n" +
                "  \"duration\": \"" + getScheduleDuration(schedule) + "\",\n" +
                "  \"frequency\": \"" + getScheduleFrequency(schedule) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Schedule Report (HTML)
        String scheduleReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Notification Schedule Report</title></head>\n" +
                "<body><h1>Notification Schedule Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Schedule Details</h2>\n" +
                "<ul>\n" +
                "<li>Schedule: " + schedule + "</li>\n" +
                "<li>Type: " + getScheduleType(schedule) + "</li>\n" +
                "<li>Duration: " + getScheduleDuration(schedule) + "</li>\n" +
                "<li>Frequency: " + getScheduleFrequency(schedule) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Schedule Data (CSV)
        String scheduleData = "schedule,type,duration,frequency,timestamp\n" +
                schedule + "," + getScheduleType(schedule) + "," + getScheduleDuration(schedule) + "," + getScheduleFrequency(schedule) + "," + timestamp;
        
        // Schedule Log (Plain Text)
        String scheduleLog = "NOTIFICATION SCHEDULING TESTING LOG\n" +
                "===================================\n\n" +
                "Schedule: " + schedule + "\n" +
                "Type: " + getScheduleType(schedule) + "\n" +
                "Duration: " + getScheduleDuration(schedule) + "\n" +
                "Frequency: " + getScheduleFrequency(schedule) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Notification scheduling validation completed";
        
        Allure.attachment("Schedule Configuration (JSON)", scheduleConfig);
        Allure.attachment("Schedule Report (HTML)", scheduleReport);
        Allure.attachment("Schedule Data (CSV)", scheduleData);
        Allure.attachment("Schedule Log (Text)", scheduleLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Notification Schedule: " + schedule);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification Schedule: " + schedule);
    }
    
    private String getScheduleType(String schedule) {
        switch (schedule) {
            case "5 minutes": return "Short-term";
            case "1 hour": return "Short-term";
            case "1 day": return "Medium-term";
            case "1 week": return "Long-term";
            case "1 month": return "Long-term";
            case "custom": return "Custom";
            default: return "Unknown";
        }
    }
    
    private String getScheduleDuration(String schedule) {
        switch (schedule) {
            case "5 minutes": return "5 minutes";
            case "1 hour": return "1 hour";
            case "1 day": return "24 hours";
            case "1 week": return "7 days";
            case "1 month": return "30 days";
            case "custom": return "User defined";
            default: return "Unknown";
        }
    }
    
    private String getScheduleFrequency(String schedule) {
        switch (schedule) {
            case "5 minutes": return "High";
            case "1 hour": return "Medium";
            case "1 day": return "Low";
            case "1 week": return "Very Low";
            case "1 month": return "Very Low";
            case "custom": return "Variable";
            default: return "Unknown";
        }
    }
}
