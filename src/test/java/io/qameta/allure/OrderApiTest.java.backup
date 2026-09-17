package io.qameta.allure;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

@Layer("rest")
@Owner("order-api-team")
@Feature("Order API")
public class OrderApiTest {

    private final RestSteps steps = new RestSteps();

    @ParameterizedTest(name = "Create order with status: {0}")
    @ValueSource(strings = {"pending", "processing", "shipped", "delivered", "cancelled"})
    @TM4J("OA-T1")
    @Story("Create new order via API")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-1")})
    public void shouldCreateOrderViaApi(String status) {
        steps.createIssueWithTitle("testuser", "testrepo", "Order Status: " + status);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order Status: " + status);
    }

    @ParameterizedTest(name = "Get order by ID: {0}")
    @ValueSource(ints = {51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65})
    @TM4J("OA-T2")
    @Story("Retrieve order by ID")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-2")})
    public void shouldGetOrderById(int orderId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Order ID: " + orderId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order ID: " + orderId);
    }

    @ParameterizedTest(name = "Update order {0} status to {1}")
    @CsvSource({
        "Order A, processing",
        "Order B, shipped",
        "Order C, delivered",
        "Order D, cancelled"
    })
    @TM4J("OA-T3")
    @Story("Update order status")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-3")})
    public void shouldUpdateOrderStatus(String orderName, String newStatus) {
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + orderName + " to " + newStatus);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + orderName + " to " + newStatus);
    }

    @ParameterizedTest(name = "Delete order with ID: {0}")
    @ValueSource(ints = {66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80})
    @TM4J("OA-T4")
    @Story("Delete order by ID")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-4")})
    public void shouldDeleteOrderById(int orderId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Order ID: " + orderId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Order ID: " + orderId);
    }

    @ParameterizedTest(name = "Search orders by customer: {0}")
    @ValueSource(strings = {"john.doe@example.com", "jane.smith@example.com", "bob.johnson@example.com", "alice.brown@example.com", "charlie.wilson@example.com"})
    @TM4J("OA-T5")
    @Story("Search orders by customer")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-5")})
    public void shouldSearchOrdersByCustomer(String customerEmail) {
        steps.createIssueWithTitle("testuser", "testrepo", "Search Customer: " + customerEmail);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Customer: " + customerEmail);
    }

    @ParameterizedTest(name = "Filter orders by date range: {0}")
    @ValueSource(strings = {"today", "yesterday", "this_week", "this_month", "last_month", "this_year"})
    @TM4J("OA-T6")
    @Story("Filter orders by date")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-6")})
    public void shouldFilterOrdersByDate(String dateRange) {
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Date: " + dateRange);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Date: " + dateRange);
    }

    @ParameterizedTest(name = "Validate order total for: {0}")
    @CsvSource({
        "Order A, 99.99",
        "Order B, 149.99",
        "Order C, 299.99",
        "Order D, 499.99"
    })
    @TM4J("OA-T7")
    @Story("Validate order totals")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-7")})
    public void shouldValidateOrderTotal(String orderName, double expectedTotal) {
        steps.createIssueWithTitle("testuser", "testrepo", "Validate Total: " + orderName + " = " + expectedTotal);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Validate Total: " + orderName + " = " + expectedTotal);
    }

    @ParameterizedTest(name = "Check order items for: {0}")
    @ValueSource(strings = {"Single Item", "Multiple Items", "Bulk Order", "Subscription Order", "Gift Order"})
    @TM4J("OA-T8")
    @Story("Check order items")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-8")})
    public void shouldCheckOrderItems(String orderType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Order Items: " + orderType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order Items: " + orderType);
    }

    @ParameterizedTest(name = "Verify shipping address for: {0}")
    @ValueSource(strings = {"Home", "Office", "Pickup Point", "Post Office", "Neighbor"})
    @TM4J("OA-T9")
    @Story("Verify shipping addresses")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-9")})
    public void shouldVerifyShippingAddress(String addressType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Shipping Address: " + addressType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Shipping Address: " + addressType);
    }

    @ParameterizedTest(name = "Test payment processing for: {0}")
    @ValueSource(strings = {"credit_card", "paypal", "apple_pay", "google_pay", "bank_transfer", "crypto"})
    @TM4J("OA-T10")
    @Story("Test payment processing")
    @Microservice("PaymentService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-10")})
    public void shouldTestPaymentProcessing(String paymentMethod) {
        steps.createIssueWithTitle("testuser", "testrepo", "Payment Processing: " + paymentMethod);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Payment Processing: " + paymentMethod);
    }
}
