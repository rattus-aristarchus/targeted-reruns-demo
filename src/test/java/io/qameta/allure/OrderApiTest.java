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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Creation Configuration (JSON)
        String orderConfig = "{\n" +
                "  \"orderStatus\": \"" + status + "\",\n" +
                "  \"priority\": \"" + getOrderPriority(status) + "\",\n" +
                "  \"processingTime\": \"" + getProcessingTime(status) + "\",\n" +
                "  \"nextStatus\": \"" + getNextStatus(status) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Order Report (HTML)
        String orderReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Creation Report</title></head>\n" +
                "<body><h1>Order Creation Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Order Details</h2>\n" +
                "<ul>\n" +
                "<li>Status: " + status + "</li>\n" +
                "<li>Priority: " + getOrderPriority(status) + "</li>\n" +
                "<li>Processing Time: " + getProcessingTime(status) + "</li>\n" +
                "<li>Next Status: " + getNextStatus(status) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Order Data (CSV)
        String orderData = "order_status,priority,processing_time,next_status,timestamp\n" +
                status + "," + getOrderPriority(status) + "," + getProcessingTime(status) + "," + getNextStatus(status) + "," + timestamp;
        
        // Order Log (Plain Text)
        String orderLog = "ORDER CREATION TESTING LOG\n" +
                "==========================\n\n" +
                "Order Status: " + status + "\n" +
                "Priority: " + getOrderPriority(status) + "\n" +
                "Processing Time: " + getProcessingTime(status) + "\n" +
                "Next Status: " + getNextStatus(status) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order creation validation completed";
        
        Allure.attachment("Order Configuration (JSON)", orderConfig);
        Allure.attachment("Order Report (HTML)", orderReport);
        Allure.attachment("Order Data (CSV)", orderData);
        Allure.attachment("Order Log (Text)", orderLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Order Status: " + status);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order Status: " + status);
    }
    
    private String getOrderPriority(String status) {
        switch (status) {
            case "pending": return "Low";
            case "processing": return "Medium";
            case "shipped": return "High";
            case "delivered": return "High";
            case "cancelled": return "Critical";
            default: return "Unknown";
        }
    }
    
    private String getProcessingTime(String status) {
        switch (status) {
            case "pending": return "0-5 minutes";
            case "processing": return "5-30 minutes";
            case "shipped": return "1-3 days";
            case "delivered": return "3-7 days";
            case "cancelled": return "0-1 hour";
            default: return "Unknown";
        }
    }
    
    private String getNextStatus(String status) {
        switch (status) {
            case "pending": return "processing";
            case "processing": return "shipped";
            case "shipped": return "delivered";
            case "delivered": return "completed";
            case "cancelled": return "refunded";
            default: return "unknown";
        }
    }

    @ParameterizedTest(name = "Get order by ID: {0}")
    @ValueSource(ints = {51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65})
    @TM4J("OA-T2")
    @Story("Retrieve order by ID")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-2")})
    public void shouldGetOrderById(int orderId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order ID Configuration (JSON)
        String orderConfig = "{\n" +
                "  \"orderId\": " + orderId + ",\n" +
                "  \"status\": \"" + getOrderStatusById(orderId) + "\",\n" +
                "  \"customer\": \"" + getCustomerById(orderId) + "\",\n" +
                "  \"total\": " + getOrderTotalById(orderId) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Order Report (HTML)
        String orderReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order ID Report</title></head>\n" +
                "<body><h1>Order ID Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Order Details</h2>\n" +
                "<ul>\n" +
                "<li>Order ID: " + orderId + "</li>\n" +
                "<li>Status: " + getOrderStatusById(orderId) + "</li>\n" +
                "<li>Customer: " + getCustomerById(orderId) + "</li>\n" +
                "<li>Total: $" + getOrderTotalById(orderId) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Order Data (CSV)
        String orderData = "order_id,status,customer,total,timestamp\n" +
                orderId + "," + getOrderStatusById(orderId) + "," + getCustomerById(orderId) + "," + getOrderTotalById(orderId) + "," + timestamp;
        
        // Order Log (Plain Text)
        String orderLog = "ORDER ID TESTING LOG\n" +
                "===================\n\n" +
                "Order ID: " + orderId + "\n" +
                "Status: " + getOrderStatusById(orderId) + "\n" +
                "Customer: " + getCustomerById(orderId) + "\n" +
                "Total: $" + getOrderTotalById(orderId) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order ID validation completed";
        
        Allure.attachment("Order Configuration (JSON)", orderConfig);
        Allure.attachment("Order Report (HTML)", orderReport);
        Allure.attachment("Order Data (CSV)", orderData);
        Allure.attachment("Order Log (Text)", orderLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Order ID: " + orderId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order ID: " + orderId);
    }
    
    private String getOrderStatusById(int orderId) {
        String[] statuses = {"pending", "processing", "shipped", "delivered", "cancelled"};
        return statuses[orderId % statuses.length];
    }
    
    private String getCustomerById(int orderId) {
        String[] customers = {"john.doe@example.com", "jane.smith@example.com", "bob.johnson@example.com", "alice.brown@example.com", "charlie.wilson@example.com"};
        return customers[orderId % customers.length];
    }
    
    private double getOrderTotalById(int orderId) {
        double[] totals = {99.99, 149.99, 299.99, 499.99, 799.99};
        return totals[orderId % totals.length];
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Status Update Configuration (JSON)
        String statusConfig = "{\n" +
                "  \"orderName\": \"" + orderName + "\",\n" +
                "  \"newStatus\": \"" + newStatus + "\",\n" +
                "  \"previousStatus\": \"" + getPreviousStatus(newStatus) + "\",\n" +
                "  \"priority\": \"" + getStatusPriority(newStatus) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Status Report (HTML)
        String statusReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Status Update Report</title></head>\n" +
                "<body><h1>Order Status Update Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Status Details</h2>\n" +
                "<ul>\n" +
                "<li>Order: " + orderName + "</li>\n" +
                "<li>New Status: " + newStatus + "</li>\n" +
                "<li>Previous Status: " + getPreviousStatus(newStatus) + "</li>\n" +
                "<li>Priority: " + getStatusPriority(newStatus) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Status Data (CSV)
        String statusData = "order_name,new_status,previous_status,priority,timestamp\n" +
                orderName + "," + newStatus + "," + getPreviousStatus(newStatus) + "," + getStatusPriority(newStatus) + "," + timestamp;
        
        // Status Log (Plain Text)
        String statusLog = "ORDER STATUS UPDATE LOG\n" +
                "=======================\n\n" +
                "Order: " + orderName + "\n" +
                "New Status: " + newStatus + "\n" +
                "Previous Status: " + getPreviousStatus(newStatus) + "\n" +
                "Priority: " + getStatusPriority(newStatus) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order status update validation completed";
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + orderName + " to " + newStatus);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + orderName + " to " + newStatus);
    }
    
    private String getPreviousStatus(String newStatus) {
        switch (newStatus) {
            case "processing": return "pending";
            case "shipped": return "processing";
            case "delivered": return "shipped";
            case "cancelled": return "pending";
            default: return "unknown";
        }
    }
    
    private String getStatusPriority(String status) {
        switch (status) {
            case "processing": return "Medium";
            case "shipped": return "High";
            case "delivered": return "High";
            case "cancelled": return "Critical";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Delete order with ID: {0}")
    @ValueSource(ints = {66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80})
    @TM4J("OA-T4")
    @Story("Delete order by ID")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-4")})
    public void shouldDeleteOrderById(int orderId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Delete Configuration (JSON)
        String deleteConfig = "{\n" +
                "  \"orderId\": " + orderId + ",\n" +
                "  \"action\": \"delete\",\n" +
                "  \"reason\": \"" + getDeleteReason(orderId) + "\",\n" +
                "  \"backup\": " + getBackupRequired(orderId) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delete Report (HTML)
        String deleteReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Delete Report</title></head>\n" +
                "<body><h1>Order Delete Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Delete Details</h2>\n" +
                "<ul>\n" +
                "<li>Order ID: " + orderId + "</li>\n" +
                "<li>Action: Delete</li>\n" +
                "<li>Reason: " + getDeleteReason(orderId) + "</li>\n" +
                "<li>Backup Required: " + getBackupRequired(orderId) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Delete Data (CSV)
        String deleteData = "order_id,action,reason,backup_required,timestamp\n" +
                orderId + ",delete," + getDeleteReason(orderId) + "," + getBackupRequired(orderId) + "," + timestamp;
        
        // Delete Log (Plain Text)
        String deleteLog = "ORDER DELETE TESTING LOG\n" +
                "========================\n\n" +
                "Order ID: " + orderId + "\n" +
                "Action: Delete\n" +
                "Reason: " + getDeleteReason(orderId) + "\n" +
                "Backup Required: " + getBackupRequired(orderId) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order delete validation completed";
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Order ID: " + orderId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Order ID: " + orderId);
    }
    
    private String getDeleteReason(int orderId) {
        if (orderId % 3 == 0) return "Customer Request";
        if (orderId % 3 == 1) return "Data Retention Policy";
        return "System Cleanup";
    }
    
    private boolean getBackupRequired(int orderId) {
        return orderId % 2 == 0;
    }

    @ParameterizedTest(name = "Search orders by customer: {0}")
    @ValueSource(strings = {"john.doe@example.com", "jane.smith@example.com", "bob.johnson@example.com", "alice.brown@example.com", "charlie.wilson@example.com"})
    @TM4J("OA-T5")
    @Story("Search orders by customer")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-5")})
    public void shouldSearchOrdersByCustomer(String customerEmail) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Customer Search Configuration (JSON)
        String searchConfig = "{\n" +
                "  \"customerEmail\": \"" + customerEmail + "\",\n" +
                "  \"customerType\": \"" + getCustomerType(customerEmail) + "\",\n" +
                "  \"expectedOrders\": " + getExpectedOrders(customerEmail) + ",\n" +
                "  \"lastOrderDate\": \"" + getLastOrderDate(customerEmail) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Search Report (HTML)
        String searchReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Customer Search Report</title></head>\n" +
                "<body><h1>Customer Search Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Search Details</h2>\n" +
                "<ul>\n" +
                "<li>Customer: " + customerEmail + "</li>\n" +
                "<li>Type: " + getCustomerType(customerEmail) + "</li>\n" +
                "<li>Expected Orders: " + getExpectedOrders(customerEmail) + "</li>\n" +
                "<li>Last Order: " + getLastOrderDate(customerEmail) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Search Data (CSV)
        String searchData = "customer_email,customer_type,expected_orders,last_order_date,timestamp\n" +
                customerEmail + "," + getCustomerType(customerEmail) + "," + getExpectedOrders(customerEmail) + "," + getLastOrderDate(customerEmail) + "," + timestamp;
        
        // Search Log (Plain Text)
        String searchLog = "CUSTOMER SEARCH TESTING LOG\n" +
                "===========================\n\n" +
                "Customer: " + customerEmail + "\n" +
                "Type: " + getCustomerType(customerEmail) + "\n" +
                "Expected Orders: " + getExpectedOrders(customerEmail) + "\n" +
                "Last Order: " + getLastOrderDate(customerEmail) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Customer search validation completed";
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Search Customer: " + customerEmail);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Customer: " + customerEmail);
    }
    
    private String getCustomerType(String customerEmail) {
        if (customerEmail.contains("john") || customerEmail.contains("jane")) return "Premium";
        if (customerEmail.contains("bob") || customerEmail.contains("alice")) return "Standard";
        return "Basic";
    }
    
    private int getExpectedOrders(String customerEmail) {
        if (customerEmail.contains("john") || customerEmail.contains("jane")) return 15;
        if (customerEmail.contains("bob") || customerEmail.contains("alice")) return 8;
        return 3;
    }
    
    private String getLastOrderDate(String customerEmail) {
        if (customerEmail.contains("john") || customerEmail.contains("jane")) return "2024-01-15";
        if (customerEmail.contains("bob") || customerEmail.contains("alice")) return "2024-01-10";
        return "2024-01-05";
    }

    @ParameterizedTest(name = "Filter orders by date range: {0}")
    @ValueSource(strings = {"today", "yesterday", "this_week", "this_month", "last_month", "this_year"})
    @TM4J("OA-T6")
    @Story("Filter orders by date")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-6")})
    public void shouldFilterOrdersByDate(String dateRange) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Date Filter Configuration (JSON)
        String filterConfig = "{\n" +
                "  \"dateRange\": \"" + dateRange + "\",\n" +
                "  \"startDate\": \"" + getStartDate(dateRange) + "\",\n" +
                "  \"endDate\": \"" + getEndDate(dateRange) + "\",\n" +
                "  \"expectedOrders\": " + getExpectedOrdersByDate(dateRange) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Filter Report (HTML)
        String filterReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Date Filter Report</title></head>\n" +
                "<body><h1>Date Filter Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Filter Details</h2>\n" +
                "<ul>\n" +
                "<li>Date Range: " + dateRange + "</li>\n" +
                "<li>Start Date: " + getStartDate(dateRange) + "</li>\n" +
                "<li>End Date: " + getEndDate(dateRange) + "</li>\n" +
                "<li>Expected Orders: " + getExpectedOrdersByDate(dateRange) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Filter Data (CSV)
        String filterData = "date_range,start_date,end_date,expected_orders,timestamp\n" +
                dateRange + "," + getStartDate(dateRange) + "," + getEndDate(dateRange) + "," + getExpectedOrdersByDate(dateRange) + "," + timestamp;
        
        // Filter Log (Plain Text)
        String filterLog = "DATE FILTER TESTING LOG\n" +
                "======================\n\n" +
                "Date Range: " + dateRange + "\n" +
                "Start Date: " + getStartDate(dateRange) + "\n" +
                "End Date: " + getEndDate(dateRange) + "\n" +
                "Expected Orders: " + getExpectedOrdersByDate(dateRange) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Date filter validation completed";
        
        Allure.attachment("Filter Configuration (JSON)", filterConfig);
        Allure.attachment("Filter Report (HTML)", filterReport);
        Allure.attachment("Filter Data (CSV)", filterData);
        Allure.attachment("Filter Log (Text)", filterLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Date: " + dateRange);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Date: " + dateRange);
    }
    
    private String getStartDate(String dateRange) {
        switch (dateRange) {
            case "today": return "2024-01-20";
            case "yesterday": return "2024-01-19";
            case "this_week": return "2024-01-15";
            case "this_month": return "2024-01-01";
            case "last_month": return "2023-12-01";
            case "this_year": return "2024-01-01";
            default: return "Unknown";
        }
    }
    
    private String getEndDate(String dateRange) {
        switch (dateRange) {
            case "today": return "2024-01-20";
            case "yesterday": return "2024-01-19";
            case "this_week": return "2024-01-21";
            case "this_month": return "2024-01-31";
            case "last_month": return "2023-12-31";
            case "this_year": return "2024-12-31";
            default: return "Unknown";
        }
    }
    
    private int getExpectedOrdersByDate(String dateRange) {
        switch (dateRange) {
            case "today": return 5;
            case "yesterday": return 8;
            case "this_week": return 25;
            case "this_month": return 120;
            case "last_month": return 95;
            case "this_year": return 1500;
            default: return 0;
        }
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Total Configuration (JSON)
        String totalConfig = "{\n" +
                "  \"orderName\": \"" + orderName + "\",\n" +
                "  \"expectedTotal\": " + expectedTotal + ",\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"taxRate\": " + getTaxRate(expectedTotal) + ",\n" +
                "  \"shipping\": " + getShippingCost(expectedTotal) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Total Report (HTML)
        String totalReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Total Report</title></head>\n" +
                "<body><h1>Order Total Validation Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Total Details</h2>\n" +
                "<ul>\n" +
                "<li>Order: " + orderName + "</li>\n" +
                "<li>Expected Total: $" + expectedTotal + "</li>\n" +
                "<li>Currency: USD</li>\n" +
                "<li>Tax Rate: " + getTaxRate(expectedTotal) + "%</li>\n" +
                "<li>Shipping: $" + getShippingCost(expectedTotal) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Total Data (CSV)
        String totalData = "order_name,expected_total,currency,tax_rate,shipping,timestamp\n" +
                orderName + "," + expectedTotal + ",USD," + getTaxRate(expectedTotal) + "," + getShippingCost(expectedTotal) + "," + timestamp;
        
        // Total Log (Plain Text)
        String totalLog = "ORDER TOTAL VALIDATION LOG\n" +
                "===========================\n\n" +
                "Order: " + orderName + "\n" +
                "Expected Total: $" + expectedTotal + "\n" +
                "Currency: USD\n" +
                "Tax Rate: " + getTaxRate(expectedTotal) + "%\n" +
                "Shipping: $" + getShippingCost(expectedTotal) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order total validation completed";
        
        Allure.attachment("Total Configuration (JSON)", totalConfig);
        Allure.attachment("Total Report (HTML)", totalReport);
        Allure.attachment("Total Data (CSV)", totalData);
        Allure.attachment("Total Log (Text)", totalLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Validate Total: " + orderName + " = " + expectedTotal);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Validate Total: " + orderName + " = " + expectedTotal);
    }
    
    private double getTaxRate(double total) {
        if (total < 100) return 8.5;
        if (total < 300) return 9.0;
        return 9.5;
    }
    
    private double getShippingCost(double total) {
        if (total < 100) return 9.99;
        if (total < 300) return 5.99;
        return 0.0;
    }

    @ParameterizedTest(name = "Check order items for: {0}")
    @ValueSource(strings = {"Single Item", "Multiple Items", "Bulk Order", "Subscription Order", "Gift Order"})
    @TM4J("OA-T8")
    @Story("Check order items")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-8")})
    public void shouldCheckOrderItems(String orderType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Items Configuration (JSON)
        String itemsConfig = "{\n" +
                "  \"orderType\": \"" + orderType + "\",\n" +
                "  \"itemCount\": " + getItemCount(orderType) + ",\n" +
                "  \"category\": \"" + getItemCategory(orderType) + "\",\n" +
                "  \"priority\": \"" + getItemPriority(orderType) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Items Report (HTML)
        String itemsReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Items Report</title></head>\n" +
                "<body><h1>Order Items Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Items Details</h2>\n" +
                "<ul>\n" +
                "<li>Order Type: " + orderType + "</li>\n" +
                "<li>Item Count: " + getItemCount(orderType) + "</li>\n" +
                "<li>Category: " + getItemCategory(orderType) + "</li>\n" +
                "<li>Priority: " + getItemPriority(orderType) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Items Data (CSV)
        String itemsData = "order_type,item_count,category,priority,timestamp\n" +
                orderType + "," + getItemCount(orderType) + "," + getItemCategory(orderType) + "," + getItemPriority(orderType) + "," + timestamp;
        
        // Items Log (Plain Text)
        String itemsLog = "ORDER ITEMS TESTING LOG\n" +
                "=======================\n\n" +
                "Order Type: " + orderType + "\n" +
                "Item Count: " + getItemCount(orderType) + "\n" +
                "Category: " + getItemCategory(orderType) + "\n" +
                "Priority: " + getItemPriority(orderType) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order items validation completed";
        
        Allure.attachment("Items Configuration (JSON)", itemsConfig);
        Allure.attachment("Items Report (HTML)", itemsReport);
        Allure.attachment("Items Data (CSV)", itemsData);
        Allure.attachment("Items Log (Text)", itemsLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Order Items: " + orderType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Order Items: " + orderType);
    }
    
    private int getItemCount(String orderType) {
        switch (orderType) {
            case "Single Item": return 1;
            case "Multiple Items": return 5;
            case "Bulk Order": return 50;
            case "Subscription Order": return 1;
            case "Gift Order": return 3;
            default: return 0;
        }
    }
    
    private String getItemCategory(String orderType) {
        switch (orderType) {
            case "Single Item": return "Standard";
            case "Multiple Items": return "Mixed";
            case "Bulk Order": return "Wholesale";
            case "Subscription Order": return "Recurring";
            case "Gift Order": return "Gift";
            default: return "Unknown";
        }
    }
    
    private String getItemPriority(String orderType) {
        switch (orderType) {
            case "Single Item": return "Medium";
            case "Multiple Items": return "High";
            case "Bulk Order": return "Low";
            case "Subscription Order": return "High";
            case "Gift Order": return "Medium";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Verify shipping address for: {0}")
    @ValueSource(strings = {"Home", "Office", "Pickup Point", "Post Office", "Neighbor"})
    @TM4J("OA-T9")
    @Story("Verify shipping addresses")
    @Microservice("OrderService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-9")})
    public void shouldVerifyShippingAddress(String addressType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Shipping Address Configuration (JSON)
        String addressConfig = "{\n" +
                "  \"addressType\": \"" + addressType + "\",\n" +
                "  \"deliveryMethod\": \"" + getDeliveryMethod(addressType) + "\",\n" +
                "  \"deliveryTime\": \"" + getDeliveryTime(addressType) + "\",\n" +
                "  \"verification\": \"" + getVerificationLevel(addressType) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Address Report (HTML)
        String addressReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Shipping Address Report</title></head>\n" +
                "<body><h1>Shipping Address Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Address Details</h2>\n" +
                "<ul>\n" +
                "<li>Address Type: " + addressType + "</li>\n" +
                "<li>Delivery Method: " + getDeliveryMethod(addressType) + "</li>\n" +
                "<li>Delivery Time: " + getDeliveryTime(addressType) + "</li>\n" +
                "<li>Verification: " + getVerificationLevel(addressType) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Address Data (CSV)
        String addressData = "address_type,delivery_method,delivery_time,verification,timestamp\n" +
                addressType + "," + getDeliveryMethod(addressType) + "," + getDeliveryTime(addressType) + "," + getVerificationLevel(addressType) + "," + timestamp;
        
        // Address Log (Plain Text)
        String addressLog = "SHIPPING ADDRESS TESTING LOG\n" +
                "=============================\n\n" +
                "Address Type: " + addressType + "\n" +
                "Delivery Method: " + getDeliveryMethod(addressType) + "\n" +
                "Delivery Time: " + getDeliveryTime(addressType) + "\n" +
                "Verification: " + getVerificationLevel(addressType) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Shipping address validation completed";
        
        Allure.attachment("Address Configuration (JSON)", addressConfig);
        Allure.attachment("Address Report (HTML)", addressReport);
        Allure.attachment("Address Data (CSV)", addressData);
        Allure.attachment("Address Log (Text)", addressLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Shipping Address: " + addressType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Shipping Address: " + addressType);
    }
    
    private String getDeliveryMethod(String addressType) {
        switch (addressType) {
            case "Home": return "Standard Delivery";
            case "Office": return "Business Delivery";
            case "Pickup Point": return "Pickup Service";
            case "Post Office": return "Postal Service";
            case "Neighbor": return "Alternative Delivery";
            default: return "Unknown";
        }
    }
    
    private String getDeliveryTime(String addressType) {
        switch (addressType) {
            case "Home": return "2-3 business days";
            case "Office": return "1-2 business days";
            case "Pickup Point": return "1-2 business days";
            case "Post Office": return "3-5 business days";
            case "Neighbor": return "2-3 business days";
            default: return "Unknown";
        }
    }
    
    private String getVerificationLevel(String addressType) {
        switch (addressType) {
            case "Home": return "High";
            case "Office": return "Medium";
            case "Pickup Point": return "High";
            case "Post Office": return "Low";
            case "Neighbor": return "Medium";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Test payment processing for: {0}")
    @ValueSource(strings = {"credit_card", "paypal", "apple_pay", "google_pay", "bank_transfer", "crypto"})
    @TM4J("OA-T10")
    @Story("Test payment processing")
    @Microservice("PaymentService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OA-10")})
    public void shouldTestPaymentProcessing(String paymentMethod) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Payment Processing Configuration (JSON)
        String paymentConfig = "{\n" +
                "  \"paymentMethod\": \"" + paymentMethod + "\",\n" +
                "  \"provider\": \"" + getPaymentProvider(paymentMethod) + "\",\n" +
                "  \"processingTime\": \"" + getPaymentProcessingTime(paymentMethod) + "\",\n" +
                "  \"security\": \"" + getSecurityLevel(paymentMethod) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Payment Report (HTML)
        String paymentReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Payment Processing Report</title></head>\n" +
                "<body><h1>Payment Processing Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Payment Details</h2>\n" +
                "<ul>\n" +
                "<li>Payment Method: " + paymentMethod + "</li>\n" +
                "<li>Provider: " + getPaymentProvider(paymentMethod) + "</li>\n" +
                "<li>Processing Time: " + getPaymentProcessingTime(paymentMethod) + "</li>\n" +
                "<li>Security: " + getSecurityLevel(paymentMethod) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Payment Data (CSV)
        String paymentData = "payment_method,provider,processing_time,security,timestamp\n" +
                paymentMethod + "," + getPaymentProvider(paymentMethod) + "," + getPaymentProcessingTime(paymentMethod) + "," + getSecurityLevel(paymentMethod) + "," + timestamp;
        
        // Payment Log (Plain Text)
        String paymentLog = "PAYMENT PROCESSING TESTING LOG\n" +
                "===============================\n\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Provider: " + getPaymentProvider(paymentMethod) + "\n" +
                "Processing Time: " + getPaymentProcessingTime(paymentMethod) + "\n" +
                "Security: " + getSecurityLevel(paymentMethod) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Payment processing validation completed";
        
        Allure.attachment("Payment Configuration (JSON)", paymentConfig);
        Allure.attachment("Payment Report (HTML)", paymentReport);
        Allure.attachment("Payment Data (CSV)", paymentData);
        Allure.attachment("Payment Log (Text)", paymentLog);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Payment Processing: " + paymentMethod);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Payment Processing: " + paymentMethod);
    }
    
    private String getPaymentProvider(String paymentMethod) {
        switch (paymentMethod) {
            case "credit_card": return "Stripe";
            case "paypal": return "PayPal";
            case "apple_pay": return "Apple";
            case "google_pay": return "Google";
            case "bank_transfer": return "Banking API";
            case "crypto": return "Crypto Gateway";
            default: return "Unknown";
        }
    }
    
    private String getPaymentProcessingTime(String paymentMethod) {
        switch (paymentMethod) {
            case "credit_card": return "2-5 seconds";
            case "paypal": return "3-10 seconds";
            case "apple_pay": return "1-3 seconds";
            case "google_pay": return "1-3 seconds";
            case "bank_transfer": return "1-3 business days";
            case "crypto": return "10-60 minutes";
            default: return "Unknown";
        }
    }
    
    private String getSecurityLevel(String paymentMethod) {
        switch (paymentMethod) {
            case "credit_card": return "High";
            case "paypal": return "High";
            case "apple_pay": return "Very High";
            case "google_pay": return "Very High";
            case "bank_transfer": return "Very High";
            case "crypto": return "Medium";
            default: return "Unknown";
        }
    }
}
