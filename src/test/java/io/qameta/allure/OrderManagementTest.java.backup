package io.qameta.allure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

@Layer("web")
@Owner("order-team")
@Feature("Order Management")
public class OrderManagementTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("OM-T1")
    @Microservice("Orders")
    @Story("Create new order")
    @Tags({@Tag("web"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-1")})
    @DisplayName("Create new order with products")
    public void shouldCreateNewOrder() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Create New Order");
        steps.shouldSeeIssueWithTitle("Create New Order");
    }

    @ParameterizedTest(name = "Create order with {0} items")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    @TM4J("OM-T2")
    @Microservice("Orders")
    @Story("Create orders with multiple items")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-2")})
    public void shouldCreateOrderWithMultipleItems(int itemCount) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Order with " + itemCount + " items");
        steps.shouldSeeIssueWithTitle("Order with " + itemCount + " items");
    }

    @ParameterizedTest(name = "Test shipping method: {0}")
    @ValueSource(strings = {"Standard", "Express", "Overnight", "Same Day", "Economy"})
    @TM4J("OM-T3")
    @Microservice("Shipping")
    @Story("Test different shipping methods")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-3")})
    public void shouldTestShippingMethod(String shippingMethod) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Shipping Method: " + shippingMethod);
        steps.shouldSeeIssueWithTitle("Shipping Method: " + shippingMethod);
    }

    @ParameterizedTest(name = "Test payment method: {0}")
    @ValueSource(strings = {"Credit Card", "PayPal", "Apple Pay", "Google Pay", "Bank Transfer"})
    @TM4J("OM-T4")
    @Microservice("Payment")
    @Story("Test different payment methods")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-4")})
    public void shouldTestPaymentMethod(String paymentMethod) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Payment Method: " + paymentMethod);
        steps.shouldSeeIssueWithTitle("Payment Method: " + paymentMethod);
    }

    @Test
    @TM4J("OM-T5")
    @Microservice("Orders")
    @Story("Cancel order")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-5")})
    @DisplayName("Cancel existing order")
    public void shouldCancelOrder() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Cancel Order Test");
        steps.closeIssueWithTitle("Cancel Order Test");
    }

    @ParameterizedTest(name = "Track order status: {0}")
    @ValueSource(strings = {"Pending", "Processing", "Shipped", "Delivered", "Cancelled"})
    @TM4J("OM-T6")
    @Microservice("Orders")
    @Story("Track order status")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-6")})
    public void shouldTrackOrderStatus(String status) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Order Status: " + status);
        steps.shouldSeeIssueWithTitle("Order Status: " + status);
    }

    @ParameterizedTest(name = "Test delivery date: {0}")
    @CsvSource({
        "Today, Same Day",
        "Tomorrow, Next Day",
        "Next Week, Standard",
        "Next Month, Economy"
    })
    @TM4J("OM-T7")
    @Microservice("Shipping")
    @Story("Test delivery dates")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-7")})
    public void shouldTestDeliveryDate(String deliveryDate, String expectedMethod) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Delivery Date: " + deliveryDate + " (" + expectedMethod + ")");
        steps.shouldSeeIssueWithTitle("Delivery Date: " + deliveryDate + " (" + expectedMethod + ")");
    }

    @Test
    @TM4J("OM-T8")
    @Microservice("Orders")
    @Story("Reorder previous order")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-8")})
    @DisplayName("Reorder from order history")
    public void shouldReorderFromHistory() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Reorder from History");
        steps.shouldSeeIssueWithTitle("Reorder from History");
    }

    @ParameterizedTest(name = "Test order notifications: {0}")
    @ValueSource(strings = {"Email", "SMS", "Push", "WhatsApp", "Telegram"})
    @TM4J("OM-T9")
    @Microservice("Notifications")
    @Story("Test order notifications")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-9")})
    public void shouldTestOrderNotifications(String notificationType) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Order Notification: " + notificationType);
        steps.shouldSeeIssueWithTitle("Order Notification: " + notificationType);
    }

    @Test
    @TM4J("OM-T10")
    @Microservice("Orders")
    @Story("Export order history")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-10")})
    @DisplayName("Export order history to PDF")
    public void shouldExportOrderHistory() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Export Order History");
        steps.shouldSeeIssueWithTitle("Export Order History");
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
