package io.qameta.allure;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

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
        steps.createIssueWithTitle("testuser", "testrepo", "Send Notification via: " + channel);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Send Notification via: " + channel);
    }

    @ParameterizedTest(name = "Create notification template: {0}")
    @ValueSource(strings = {"Welcome", "Order Update", "Price Alert", "Stock Alert", "Promotion", "Newsletter", "Security Alert", "Maintenance"})
    @TM4J("NA-T2")
    @Story("Create notification templates")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-2")})
    public void shouldCreateNotificationTemplate(String templateName) {
        steps.createIssueWithTitle("testuser", "testrepo", "Notification Template: " + templateName);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification Template: " + templateName);
    }

    @ParameterizedTest(name = "Get notification by ID: {0}")
    @ValueSource(ints = {81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95})
    @TM4J("NA-T3")
    @Story("Retrieve notification by ID")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-3")})
    public void shouldGetNotificationById(int notificationId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Notification ID: " + notificationId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification ID: " + notificationId);
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
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + notificationName + " to " + newStatus);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + notificationName + " to " + newStatus);
    }

    @ParameterizedTest(name = "Delete notification with ID: {0}")
    @ValueSource(ints = {96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110})
    @TM4J("NA-T5")
    @Story("Delete notification by ID")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-5")})
    public void shouldDeleteNotificationById(int notificationId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Notification ID: " + notificationId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Notification ID: " + notificationId);
    }

    @ParameterizedTest(name = "Search notifications by type: {0}")
    @ValueSource(strings = {"order", "product", "marketing", "security", "system", "maintenance", "update", "alert"})
    @TM4J("NA-T6")
    @Story("Search notifications by type")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-6")})
    public void shouldSearchNotificationsByType(String notificationType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Search Notification Type: " + notificationType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Notification Type: " + notificationType);
    }

    @ParameterizedTest(name = "Filter notifications by priority: {0}")
    @ValueSource(strings = {"low", "medium", "high", "urgent", "critical"})
    @TM4J("NA-T7")
    @Story("Filter notifications by priority")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-7")})
    public void shouldFilterNotificationsByPriority(String priority) {
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Priority: " + priority);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Priority: " + priority);
    }

    @ParameterizedTest(name = "Test notification delivery for: {0}")
    @ValueSource(strings = {"immediate", "scheduled", "batch", "recurring", "conditional"})
    @TM4J("NA-T8")
    @Story("Test notification delivery methods")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-8")})
    public void shouldTestNotificationDelivery(String deliveryMethod) {
        steps.createIssueWithTitle("testuser", "testrepo", "Delivery Method: " + deliveryMethod);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Delivery Method: " + deliveryMethod);
    }

    @ParameterizedTest(name = "Validate notification content for: {0}")
    @ValueSource(strings = {"Text Only", "HTML", "Rich Text", "Template", "Dynamic Content", "Multilingual"})
    @TM4J("NA-T9")
    @Story("Validate notification content")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-9")})
    public void shouldValidateNotificationContent(String contentType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Content Type: " + contentType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Content Type: " + contentType);
    }

    @ParameterizedTest(name = "Test notification scheduling for: {0}")
    @ValueSource(strings = {"5 minutes", "1 hour", "1 day", "1 week", "1 month", "custom"})
    @TM4J("NA-T10")
    @Story("Test notification scheduling")
    @Microservice("NotificationService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("NA-10")})
    public void shouldTestNotificationScheduling(String schedule) {
        steps.createIssueWithTitle("testuser", "testrepo", "Notification Schedule: " + schedule);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Notification Schedule: " + schedule);
    }
}
