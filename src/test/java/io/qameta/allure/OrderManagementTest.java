package io.qameta.allure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.qameta.allure.Allure;
import java.io.InputStream;
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Creation Configuration (JSON)
        String orderConfig = "{\n" +
                "  \"orderType\": \"new_order\",\n" +
                "  \"products\": [\n" +
                "    {\"id\": \"PROD-001\", \"name\": \"Laptop\", \"price\": 999.99, \"quantity\": 1},\n" +
                "    {\"id\": \"PROD-002\", \"name\": \"Mouse\", \"price\": 29.99, \"quantity\": 2},\n" +
                "    {\"id\": \"PROD-003\", \"name\": \"Keyboard\", \"price\": 79.99, \"quantity\": 1}\n" +
                "  ],\n" +
                "  \"totalAmount\": 1139.96,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Order Dashboard (HTML)
        String orderDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Management Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".product{display:flex;justify-content:space-between;align-items:center;padding:15px;margin:10px 0;border-radius:4px;background:#f5f5f5;}\n" +
                ".product-info{flex:1;}\n" +
                ".product-price{font-weight:bold;color:#2e7d32;}\n" +
                ".total{background:#e8f5e8;padding:20px;border-radius:4px;text-align:center;margin:20px 0;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".pending{background:#ff9800;}\n" +
                ".processing{background:#2196f3;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🛒 Order Management Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Order Details</h2>\n" +
                "<div class=\"product\">\n" +
                "<div class=\"product-info\"><strong>Laptop</strong><br/>PROD-001</div>\n" +
                "<div class=\"product-price\">$999.99 x 1</div>\n" +
                "</div>\n" +
                "<div class=\"product\">\n" +
                "<div class=\"product-info\"><strong>Mouse</strong><br/>PROD-002</div>\n" +
                "<div class=\"product-price\">$29.99 x 2</div>\n" +
                "</div>\n" +
                "<div class=\"product\">\n" +
                "<div class=\"product-info\"><strong>Keyboard</strong><br/>PROD-003</div>\n" +
                "<div class=\"product-price\">$79.99 x 1</div>\n" +
                "</div>\n" +
                "<div class=\"total\">\n" +
                "<h3>Total Amount: $1,139.96</h3>\n" +
                "<p>Currency: USD</p>\n" +
                "</div>\n" +
                "<div class=\"status pending\">Order Status: Pending</div>\n" +
                "</div></body></html>";
        
        // Order Data (CSV)
        String orderData = "product_id,product_name,price,quantity,subtotal,timestamp\n" +
                "PROD-001,Laptop,999.99,1,999.99," + timestamp + "\n" +
                "PROD-002,Mouse,29.99,2,59.98," + timestamp + "\n" +
                "PROD-003,Keyboard,79.99,1,79.99," + timestamp;
        
        // Order Log (Plain Text)
        String orderLog = "ORDER CREATION TESTING LOG\n" +
                "===========================\n\n" +
                "Order Type: New Order\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Pending\n\n" +
                "PRODUCTS:\n" +
                "---------\n" +
                "1. Laptop (PROD-001) - $999.99 x 1 = $999.99\n" +
                "2. Mouse (PROD-002) - $29.99 x 2 = $59.98\n" +
                "3. Keyboard (PROD-003) - $79.99 x 1 = $79.99\n\n" +
                "TOTAL AMOUNT: $1,139.96\n" +
                "CURRENCY: USD\n\n" +
                "ACTION: Order creation validation completed";
        
        Allure.attachment("Order Configuration (JSON)", orderConfig);
        Allure.attachment("Order Dashboard (HTML)", orderDashboard);
        Allure.attachment("Order Data (CSV)", orderData);
        Allure.attachment("Order Log (Text)", orderLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Multiple Items Configuration (JSON)
        String itemsConfig = "{\n" +
                "  \"itemCount\": " + itemCount + ",\n" +
                "  \"orderType\": \"multiple_items\",\n" +
                "  \"estimatedTotal\": " + (itemCount * 99.99) + ",\n" +
                "  \"shippingWeight\": " + (itemCount * 1.5) + ",\n" +
                "  \"priority\": \"" + getOrderPriority(itemCount) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Items Report (HTML)
        String itemsReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Multiple Items Order Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".metric{display:inline-block;background:#e3f2fd;padding:15px;margin:10px;border-radius:4px;text-align:center;}\n" +
                ".priority{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".low{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".high{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📦 Multiple Items Order Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Order Metrics</h2>\n" +
                "<div class=\"metric\"><h3>Item Count</h3><p>" + itemCount + "</p></div>\n" +
                "<div class=\"metric\"><h3>Estimated Total</h3><p>$" + String.format("%.2f", itemCount * 99.99) + "</p></div>\n" +
                "<div class=\"metric\"><h3>Shipping Weight</h3><p>" + String.format("%.1f", itemCount * 1.5) + " lbs</p></div>\n" +
                "<div class=\"priority " + getPriorityClass(itemCount) + "\">Priority: " + getOrderPriority(itemCount) + "</div>\n" +
                "</div></body></html>";
        
        // Items Data (CSV)
        String itemsData = "item_count,estimated_total,shipping_weight,priority,timestamp\n" +
                itemCount + "," + String.format("%.2f", itemCount * 99.99) + "," + String.format("%.1f", itemCount * 1.5) + "," + getOrderPriority(itemCount) + "," + timestamp;
        
        // Items Log (Plain Text)
        String itemsLog = "MULTIPLE ITEMS ORDER TESTING LOG\n" +
                "===================================\n\n" +
                "Item Count: " + itemCount + "\n" +
                "Order Type: Multiple Items\n" +
                "Estimated Total: $" + String.format("%.2f", itemCount * 99.99) + "\n" +
                "Shipping Weight: " + String.format("%.1f", itemCount * 1.5) + " lbs\n" +
                "Priority: " + getOrderPriority(itemCount) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Multiple items order validation completed";
        
        Allure.attachment("Items Configuration (JSON)", itemsConfig);
        Allure.attachment("Items Report (HTML)", itemsReport);
        Allure.attachment("Items Data (CSV)", itemsData);
        Allure.attachment("Items Log (Text)", itemsLog);
        
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
        steps.createIssueWithTitle("Order with " + itemCount + " items");
        steps.shouldSeeIssueWithTitle("Order with " + itemCount + " items");
    }
    
    private String getOrderPriority(int itemCount) {
        if (itemCount <= 3) return "Low";
        if (itemCount <= 7) return "Medium";
        return "High";
    }
    
    private String getPriorityClass(int itemCount) {
        if (itemCount <= 3) return "low";
        if (itemCount <= 7) return "medium";
        return "high";
    }

    @ParameterizedTest(name = "Test shipping method: {0}")
    @ValueSource(strings = {"Standard", "Express", "Overnight", "Same Day", "Economy"})
    @TM4J("OM-T3")
    @Microservice("Shipping")
    @Story("Test different shipping methods")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-3")})
    public void shouldTestShippingMethod(String shippingMethod) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Shipping Method Configuration (JSON)
        String shippingConfig = "{\n" +
                "  \"shippingMethod\": \"" + shippingMethod + "\",\n" +
                "  \"deliveryTime\": \"" + getDeliveryTime(shippingMethod) + "\",\n" +
                "  \"cost\": " + getShippingCost(shippingMethod) + ",\n" +
                "  \"tracking\": " + getTrackingAvailable(shippingMethod) + ",\n" +
                "  \"insurance\": " + getInsuranceIncluded(shippingMethod) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Shipping Report (HTML)
        String shippingReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Shipping Method Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".shipping{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".available{background:#4caf50;}\n" +
                ".unavailable{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🚚 Shipping Method Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"shipping\">\n" +
                "<h2>Shipping Method: " + shippingMethod + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Delivery Time:</strong></span>\n" +
                "<span>" + getDeliveryTime(shippingMethod) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Cost:</strong></span>\n" +
                "<span>$" + getShippingCost(shippingMethod) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Tracking:</strong></span>\n" +
                "<span class=\"status " + (getTrackingAvailable(shippingMethod) ? "available" : "unavailable") + "\">" + 
                (getTrackingAvailable(shippingMethod) ? "Available" : "Not Available") + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Insurance:</strong></span>\n" +
                "<span class=\"status " + (getInsuranceIncluded(shippingMethod) ? "available" : "unavailable") + "\">" + 
                (getInsuranceIncluded(shippingMethod) ? "Included" : "Not Included") + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Shipping Data (CSV)
        String shippingData = "shipping_method,delivery_time,cost,tracking,insurance,timestamp\n" +
                shippingMethod + "," + getDeliveryTime(shippingMethod) + "," + getShippingCost(shippingMethod) + "," + 
                getTrackingAvailable(shippingMethod) + "," + getInsuranceIncluded(shippingMethod) + "," + timestamp;
        
        // Shipping Log (Plain Text)
        String shippingLog = "SHIPPING METHOD TESTING LOG\n" +
                "============================\n\n" +
                "Shipping Method: " + shippingMethod + "\n" +
                "Delivery Time: " + getDeliveryTime(shippingMethod) + "\n" +
                "Cost: $" + getShippingCost(shippingMethod) + "\n" +
                "Tracking: " + (getTrackingAvailable(shippingMethod) ? "Available" : "Not Available") + "\n" +
                "Insurance: " + (getInsuranceIncluded(shippingMethod) ? "Included" : "Not Included") + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Shipping method validation completed";
        
        Allure.attachment("Shipping Configuration (JSON)", shippingConfig);
        Allure.attachment("Shipping Report (HTML)", shippingReport);
        Allure.attachment("Shipping Data (CSV)", shippingData);
        Allure.attachment("Shipping Log (Text)", shippingLog);
        
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
        steps.createIssueWithTitle("Shipping Method: " + shippingMethod);
        steps.shouldSeeIssueWithTitle("Shipping Method: " + shippingMethod);
    }
    
    private String getDeliveryTime(String shippingMethod) {
        switch (shippingMethod) {
            case "Standard": return "3-5 business days";
            case "Express": return "1-2 business days";
            case "Overnight": return "Next business day";
            case "Same Day": return "Same day";
            case "Economy": return "5-7 business days";
            default: return "Unknown";
        }
    }
    
    private double getShippingCost(String shippingMethod) {
        switch (shippingMethod) {
            case "Standard": return 9.99;
            case "Express": return 19.99;
            case "Overnight": return 29.99;
            case "Same Day": return 49.99;
            case "Economy": return 4.99;
            default: return 0.0;
        }
    }
    
    private boolean getTrackingAvailable(String shippingMethod) {
        return !shippingMethod.equals("Economy");
    }
    
    private boolean getInsuranceIncluded(String shippingMethod) {
        return shippingMethod.equals("Express") || shippingMethod.equals("Overnight") || shippingMethod.equals("Same Day");
    }

    @ParameterizedTest(name = "Test payment method: {0}")
    @ValueSource(strings = {"Credit Card", "PayPal", "Apple Pay", "Google Pay", "Bank Transfer"})
    @TM4J("OM-T4")
    @Microservice("Payment")
    @Story("Test different payment methods")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-4")})
    public void shouldTestPaymentMethod(String paymentMethod) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Payment Method Configuration (JSON)
        String paymentConfig = "{\n" +
                "  \"paymentMethod\": \"" + paymentMethod + "\",\n" +
                "  \"processingTime\": \"" + getPaymentProcessingTime(paymentMethod) + "\",\n" +
                "  \"securityLevel\": \"" + getPaymentSecurityLevel(paymentMethod) + "\",\n" +
                "  \"fees\": " + getPaymentFees(paymentMethod) + ",\n" +
                "  \"refundTime\": \"" + getRefundTime(paymentMethod) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Payment Report (HTML)
        String paymentReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Payment Method Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".payment{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".security{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>💳 Payment Method Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"payment\">\n" +
                "<h2>Payment Method: " + paymentMethod + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Processing Time:</strong></span>\n" +
                "<span>" + getPaymentProcessingTime(paymentMethod) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Security Level:</strong></span>\n" +
                "<span class=\"security " + getSecurityClass(paymentMethod) + "\">" + getPaymentSecurityLevel(paymentMethod) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Fees:</strong></span>\n" +
                "<span>" + getPaymentFees(paymentMethod) + "%</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Refund Time:</strong></span>\n" +
                "<span>" + getRefundTime(paymentMethod) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Payment Data (CSV)
        String paymentData = "payment_method,processing_time,security_level,fees,refund_time,timestamp\n" +
                paymentMethod + "," + getPaymentProcessingTime(paymentMethod) + "," + getPaymentSecurityLevel(paymentMethod) + "," + 
                getPaymentFees(paymentMethod) + "," + getRefundTime(paymentMethod) + "," + timestamp;
        
        // Payment Log (Plain Text)
        String paymentLog = "PAYMENT METHOD TESTING LOG\n" +
                "===========================\n\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Processing Time: " + getPaymentProcessingTime(paymentMethod) + "\n" +
                "Security Level: " + getPaymentSecurityLevel(paymentMethod) + "\n" +
                "Fees: " + getPaymentFees(paymentMethod) + "%\n" +
                "Refund Time: " + getRefundTime(paymentMethod) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Payment method validation completed";
        
        Allure.attachment("Payment Configuration (JSON)", paymentConfig);
        Allure.attachment("Payment Report (HTML)", paymentReport);
        Allure.attachment("Payment Data (CSV)", paymentData);
        Allure.attachment("Payment Log (Text)", paymentLog);
        
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
        steps.createIssueWithTitle("Payment Method: " + paymentMethod);
        steps.shouldSeeIssueWithTitle("Payment Method: " + paymentMethod);
    }
    
    private String getPaymentProcessingTime(String paymentMethod) {
        switch (paymentMethod) {
            case "Credit Card": return "2-5 seconds";
            case "PayPal": return "3-10 seconds";
            case "Apple Pay": return "1-3 seconds";
            case "Google Pay": return "1-3 seconds";
            case "Bank Transfer": return "1-3 business days";
            default: return "Unknown";
        }
    }
    
    private String getPaymentSecurityLevel(String paymentMethod) {
        switch (paymentMethod) {
            case "Credit Card": return "High";
            case "PayPal": return "High";
            case "Apple Pay": return "Very High";
            case "Google Pay": return "Very High";
            case "Bank Transfer": return "Very High";
            default: return "Unknown";
        }
    }
    
    private double getPaymentFees(String paymentMethod) {
        switch (paymentMethod) {
            case "Credit Card": return 2.9;
            case "PayPal": return 2.9;
            case "Apple Pay": return 2.9;
            case "Google Pay": return 2.9;
            case "Bank Transfer": return 0.0;
            default: return 0.0;
        }
    }
    
    private String getRefundTime(String paymentMethod) {
        switch (paymentMethod) {
            case "Credit Card": return "3-5 business days";
            case "PayPal": return "1-3 business days";
            case "Apple Pay": return "1-3 business days";
            case "Google Pay": return "1-3 business days";
            case "Bank Transfer": return "5-7 business days";
            default: return "Unknown";
        }
    }
    
    private String getSecurityClass(String paymentMethod) {
        switch (paymentMethod) {
            case "Credit Card": return "high";
            case "PayPal": return "high";
            case "Apple Pay": return "high";
            case "Google Pay": return "high";
            case "Bank Transfer": return "high";
            default: return "low";
        }
    }

    @Test
    @TM4J("OM-T5")
    @Microservice("Orders")
    @Story("Cancel order")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-5")})
    @DisplayName("Cancel existing order")
    public void shouldCancelOrder() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Cancellation Configuration (JSON)
        String cancelConfig = "{\n" +
                "  \"action\": \"cancel_order\",\n" +
                "  \"orderId\": \"ORD-12345\",\n" +
                "  \"reason\": \"Customer Request\",\n" +
                "  \"refundAmount\": 1139.96,\n" +
                "  \"refundMethod\": \"Original Payment\",\n" +
                "  \"processingTime\": \"1-3 business days\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Cancellation Report (HTML)
        String cancelReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Cancellation Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".cancellation{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".refund{background:#e8f5e8;padding:15px;border-radius:4px;margin:15px 0;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>❌ Order Cancellation Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"cancellation\">\n" +
                "<h2>Order Cancellation Details</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Order ID:</strong></span>\n" +
                "<span>ORD-12345</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Reason:</strong></span>\n" +
                "<span>Customer Request</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Cancelled</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"refund\">\n" +
                "<h3>Refund Information</h3>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Refund Amount:</strong></span>\n" +
                "<span>$1,139.96</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Refund Method:</strong></span>\n" +
                "<span>Original Payment</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Processing Time:</strong></span>\n" +
                "<span>1-3 business days</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Cancellation Data (CSV)
        String cancelData = "action,order_id,reason,refund_amount,refund_method,processing_time,timestamp\n" +
                "cancel_order,ORD-12345,Customer Request,1139.96,Original Payment,1-3 business days," + timestamp;
        
        // Cancellation Log (Plain Text)
        String cancelLog = "ORDER CANCELLATION TESTING LOG\n" +
                "===============================\n\n" +
                "Action: Cancel Order\n" +
                "Order ID: ORD-12345\n" +
                "Reason: Customer Request\n" +
                "Status: Cancelled\n\n" +
                "REFUND INFORMATION:\n" +
                "------------------\n" +
                "Refund Amount: $1,139.96\n" +
                "Refund Method: Original Payment\n" +
                "Processing Time: 1-3 business days\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order cancellation validation completed";
        
        Allure.attachment("Cancellation Configuration (JSON)", cancelConfig);
        Allure.attachment("Cancellation Report (HTML)", cancelReport);
        Allure.attachment("Cancellation Data (CSV)", cancelData);
        Allure.attachment("Cancellation Log (Text)", cancelLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Order Status Configuration (JSON)
        String statusConfig = "{\n" +
                "  \"orderStatus\": \"" + status + "\",\n" +
                "  \"orderId\": \"ORD-12345\",\n" +
                "  \"estimatedDelivery\": \"" + getEstimatedDelivery(status) + "\",\n" +
                "  \"trackingNumber\": \"" + getTrackingNumber(status) + "\",\n" +
                "  \"lastUpdate\": \"" + getLastUpdate(status) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Status Report (HTML)
        String statusReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Status Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".status{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;}\n" +
                ".status-badge{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".pending{background:#ff9800;}\n" +
                ".processing{background:#2196f3;}\n" +
                ".shipped{background:#9c27b0;}\n" +
                ".delivered{background:#4caf50;}\n" +
                ".cancelled{background:#f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📦 Order Status Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"status\">\n" +
                "<h2>Order Status: <span class=\"status-badge " + getStatusClass(status) + "\">" + status + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Order ID:</strong></span>\n" +
                "<span>ORD-12345</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Estimated Delivery:</strong></span>\n" +
                "<span>" + getEstimatedDelivery(status) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Tracking Number:</strong></span>\n" +
                "<span>" + getTrackingNumber(status) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Last Update:</strong></span>\n" +
                "<span>" + getLastUpdate(status) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Status Data (CSV)
        String statusData = "order_status,order_id,estimated_delivery,tracking_number,last_update,timestamp\n" +
                status + ",ORD-12345," + getEstimatedDelivery(status) + "," + getTrackingNumber(status) + "," + getLastUpdate(status) + "," + timestamp;
        
        // Status Log (Plain Text)
        String statusLog = "ORDER STATUS TRACKING LOG\n" +
                "=========================\n\n" +
                "Order Status: " + status + "\n" +
                "Order ID: ORD-12345\n" +
                "Estimated Delivery: " + getEstimatedDelivery(status) + "\n" +
                "Tracking Number: " + getTrackingNumber(status) + "\n" +
                "Last Update: " + getLastUpdate(status) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order status tracking validation completed";
        
        Allure.attachment("Status Configuration (JSON)", statusConfig);
        Allure.attachment("Status Report (HTML)", statusReport);
        Allure.attachment("Status Data (CSV)", statusData);
        Allure.attachment("Status Log (Text)", statusLog);
        
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
        steps.createIssueWithTitle("Order Status: " + status);
        steps.shouldSeeIssueWithTitle("Order Status: " + status);
    }
    
    private String getEstimatedDelivery(String status) {
        switch (status) {
            case "Pending": return "TBD";
            case "Processing": return "3-5 business days";
            case "Shipped": return "1-2 business days";
            case "Delivered": return "Delivered";
            case "Cancelled": return "N/A";
            default: return "Unknown";
        }
    }
    
    private String getTrackingNumber(String status) {
        switch (status) {
            case "Pending": return "N/A";
            case "Processing": return "N/A";
            case "Shipped": return "1Z999AA1234567890";
            case "Delivered": return "1Z999AA1234567890";
            case "Cancelled": return "N/A";
            default: return "Unknown";
        }
    }
    
    private String getLastUpdate(String status) {
        switch (status) {
            case "Pending": return "2024-01-20 10:30:00";
            case "Processing": return "2024-01-20 14:15:00";
            case "Shipped": return "2024-01-21 09:45:00";
            case "Delivered": return "2024-01-22 16:20:00";
            case "Cancelled": return "2024-01-20 11:00:00";
            default: return "Unknown";
        }
    }
    
    private String getStatusClass(String status) {
        switch (status) {
            case "Pending": return "pending";
            case "Processing": return "processing";
            case "Shipped": return "shipped";
            case "Delivered": return "delivered";
            case "Cancelled": return "cancelled";
            default: return "pending";
        }
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Delivery Date Configuration (JSON)
        String deliveryConfig = "{\n" +
                "  \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                "  \"expectedMethod\": \"" + expectedMethod + "\",\n" +
                "  \"shippingCost\": " + getShippingCostForDate(deliveryDate) + ",\n" +
                "  \"cutoffTime\": \"" + getCutoffTime(deliveryDate) + "\",\n" +
                "  \"availability\": " + getAvailability(deliveryDate) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delivery Report (HTML)
        String deliveryReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Delivery Date Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".delivery{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".available{background:#4caf50;}\n" +
                ".unavailable{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📅 Delivery Date Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"delivery\">\n" +
                "<h2>Delivery Date: " + deliveryDate + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Expected Method:</strong></span>\n" +
                "<span>" + expectedMethod + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Shipping Cost:</strong></span>\n" +
                "<span>$" + getShippingCostForDate(deliveryDate) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Cutoff Time:</strong></span>\n" +
                "<span>" + getCutoffTime(deliveryDate) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Availability:</strong></span>\n" +
                "<span class=\"status " + (getAvailability(deliveryDate) ? "available" : "unavailable") + "\">" + 
                (getAvailability(deliveryDate) ? "Available" : "Not Available") + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Delivery Data (CSV)
        String deliveryData = "delivery_date,expected_method,shipping_cost,cutoff_time,availability,timestamp\n" +
                deliveryDate + "," + expectedMethod + "," + getShippingCostForDate(deliveryDate) + "," + 
                getCutoffTime(deliveryDate) + "," + getAvailability(deliveryDate) + "," + timestamp;
        
        // Delivery Log (Plain Text)
        String deliveryLog = "DELIVERY DATE TESTING LOG\n" +
                "==========================\n\n" +
                "Delivery Date: " + deliveryDate + "\n" +
                "Expected Method: " + expectedMethod + "\n" +
                "Shipping Cost: $" + getShippingCostForDate(deliveryDate) + "\n" +
                "Cutoff Time: " + getCutoffTime(deliveryDate) + "\n" +
                "Availability: " + (getAvailability(deliveryDate) ? "Available" : "Not Available") + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Delivery date validation completed";
        
        Allure.attachment("Delivery Configuration (JSON)", deliveryConfig);
        Allure.attachment("Delivery Report (HTML)", deliveryReport);
        Allure.attachment("Delivery Data (CSV)", deliveryData);
        Allure.attachment("Delivery Log (Text)", deliveryLog);
        
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
        steps.createIssueWithTitle("Delivery Date: " + deliveryDate + " (" + expectedMethod + ")");
        steps.shouldSeeIssueWithTitle("Delivery Date: " + deliveryDate + " (" + expectedMethod + ")");
    }
    
    private double getShippingCostForDate(String deliveryDate) {
        switch (deliveryDate) {
            case "Today": return 49.99;
            case "Tomorrow": return 29.99;
            case "Next Week": return 9.99;
            case "Next Month": return 4.99;
            default: return 0.0;
        }
    }
    
    private String getCutoffTime(String deliveryDate) {
        switch (deliveryDate) {
            case "Today": return "2:00 PM";
            case "Tomorrow": return "11:00 PM";
            case "Next Week": return "11:59 PM";
            case "Next Month": return "11:59 PM";
            default: return "Unknown";
        }
    }
    
    private boolean getAvailability(String deliveryDate) {
        return !deliveryDate.equals("Next Month");
    }

    @Test
    @TM4J("OM-T8")
    @Microservice("Orders")
    @Story("Reorder previous order")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-8")})
    @DisplayName("Reorder from order history")
    public void shouldReorderFromHistory() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Reorder Configuration (JSON)
        String reorderConfig = "{\n" +
                "  \"action\": \"reorder_from_history\",\n" +
                "  \"originalOrderId\": \"ORD-12345\",\n" +
                "  \"newOrderId\": \"ORD-12346\",\n" +
                "  \"products\": [\n" +
                "    {\"id\": \"PROD-001\", \"name\": \"Laptop\", \"price\": 999.99, \"quantity\": 1, \"available\": true},\n" +
                "    {\"id\": \"PROD-002\", \"name\": \"Mouse\", \"price\": 29.99, \"quantity\": 2, \"available\": true},\n" +
                "    {\"id\": \"PROD-003\", \"name\": \"Keyboard\", \"price\": 79.99, \"quantity\": 1, \"available\": false}\n" +
                "  ],\n" +
                "  \"totalAmount\": 1109.97,\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Reorder Report (HTML)
        String reorderReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Reorder from History Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".reorder{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".product{display:flex;justify-content:space-between;align-items:center;padding:10px;margin:5px 0;border-radius:4px;background:#f5f5f5;}\n" +
                ".available{background:#e8f5e8;}\n" +
                ".unavailable{background:#ffebee;}\n" +
                ".status{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".available-status{background:#4caf50;}\n" +
                ".unavailable-status{background:#f44336;}\n" +
                ".total{background:#e3f2fd;padding:15px;border-radius:4px;text-align:center;margin:15px 0;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔄 Reorder from History Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"reorder\">\n" +
                "<h2>Reorder Details</h2>\n" +
                "<p><strong>Original Order:</strong> ORD-12345</p>\n" +
                "<p><strong>New Order:</strong> ORD-12346</p>\n" +
                "<h3>Products</h3>\n" +
                "<div class=\"product available\">\n" +
                "<div><strong>Laptop</strong><br/>PROD-001 - $999.99 x 1</div>\n" +
                "<div><span class=\"status available-status\">Available</span></div>\n" +
                "</div>\n" +
                "<div class=\"product available\">\n" +
                "<div><strong>Mouse</strong><br/>PROD-002 - $29.99 x 2</div>\n" +
                "<div><span class=\"status available-status\">Available</span></div>\n" +
                "</div>\n" +
                "<div class=\"product unavailable\">\n" +
                "<div><strong>Keyboard</strong><br/>PROD-003 - $79.99 x 1</div>\n" +
                "<div><span class=\"status unavailable-status\">Out of Stock</span></div>\n" +
                "</div>\n" +
                "<div class=\"total\">\n" +
                "<h3>Total Amount: $1,109.97</h3>\n" +
                "<p>Note: Keyboard excluded due to unavailability</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Reorder Data (CSV)
        String reorderData = "action,original_order_id,new_order_id,product_id,product_name,price,quantity,available,timestamp\n" +
                "reorder_from_history,ORD-12345,ORD-12346,PROD-001,Laptop,999.99,1,true," + timestamp + "\n" +
                "reorder_from_history,ORD-12345,ORD-12346,PROD-002,Mouse,29.99,2,true," + timestamp + "\n" +
                "reorder_from_history,ORD-12345,ORD-12346,PROD-003,Keyboard,79.99,1,false," + timestamp;
        
        // Reorder Log (Plain Text)
        String reorderLog = "REORDER FROM HISTORY TESTING LOG\n" +
                "===================================\n\n" +
                "Action: Reorder from History\n" +
                "Original Order ID: ORD-12345\n" +
                "New Order ID: ORD-12346\n\n" +
                "PRODUCTS:\n" +
                "---------\n" +
                "1. Laptop (PROD-001) - $999.99 x 1 - ✅ Available\n" +
                "2. Mouse (PROD-002) - $29.99 x 2 - ✅ Available\n" +
                "3. Keyboard (PROD-003) - $79.99 x 1 - ❌ Out of Stock\n\n" +
                "TOTAL AMOUNT: $1,109.97\n" +
                "Note: Keyboard excluded due to unavailability\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Reorder from history validation completed";
        
        Allure.attachment("Reorder Configuration (JSON)", reorderConfig);
        Allure.attachment("Reorder Report (HTML)", reorderReport);
        Allure.attachment("Reorder Data (CSV)", reorderData);
        Allure.attachment("Reorder Log (Text)", reorderLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Notification Configuration (JSON)
        String notificationConfig = "{\n" +
                "  \"notificationType\": \"" + notificationType + "\",\n" +
                "  \"orderId\": \"ORD-12345\",\n" +
                "  \"deliveryTime\": \"" + getNotificationDeliveryTime(notificationType) + "\",\n" +
                "  \"priority\": \"" + getNotificationPriority(notificationType) + "\",\n" +
                "  \"template\": \"" + getNotificationTemplate(notificationType) + "\",\n" +
                "  \"enabled\": " + getNotificationEnabled(notificationType) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Notification Report (HTML)
        String notificationReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order Notification Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".notification{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".priority{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#f44336;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#4caf50;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".enabled{background:#4caf50;}\n" +
                ".disabled{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔔 Order Notification Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"notification\">\n" +
                "<h2>Notification Type: " + notificationType + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Order ID:</strong></span>\n" +
                "<span>ORD-12345</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Delivery Time:</strong></span>\n" +
                "<span>" + getNotificationDeliveryTime(notificationType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Priority:</strong></span>\n" +
                "<span class=\"priority " + getPriorityClass(notificationType) + "\">" + getNotificationPriority(notificationType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Template:</strong></span>\n" +
                "<span>" + getNotificationTemplate(notificationType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status " + (getNotificationEnabled(notificationType) ? "enabled" : "disabled") + "\">" + 
                (getNotificationEnabled(notificationType) ? "Enabled" : "Disabled") + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Notification Data (CSV)
        String notificationData = "notification_type,order_id,delivery_time,priority,template,enabled,timestamp\n" +
                notificationType + ",ORD-12345," + getNotificationDeliveryTime(notificationType) + "," + 
                getNotificationPriority(notificationType) + "," + getNotificationTemplate(notificationType) + "," + 
                getNotificationEnabled(notificationType) + "," + timestamp;
        
        // Notification Log (Plain Text)
        String notificationLog = "ORDER NOTIFICATION TESTING LOG\n" +
                "==============================\n\n" +
                "Notification Type: " + notificationType + "\n" +
                "Order ID: ORD-12345\n" +
                "Delivery Time: " + getNotificationDeliveryTime(notificationType) + "\n" +
                "Priority: " + getNotificationPriority(notificationType) + "\n" +
                "Template: " + getNotificationTemplate(notificationType) + "\n" +
                "Status: " + (getNotificationEnabled(notificationType) ? "Enabled" : "Disabled") + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order notification validation completed";
        
        Allure.attachment("Notification Configuration (JSON)", notificationConfig);
        Allure.attachment("Notification Report (HTML)", notificationReport);
        Allure.attachment("Notification Data (CSV)", notificationData);
        Allure.attachment("Notification Log (Text)", notificationLog);
        
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
        steps.createIssueWithTitle("Order Notification: " + notificationType);
        steps.shouldSeeIssueWithTitle("Order Notification: " + notificationType);
    }
    
    private String getNotificationDeliveryTime(String notificationType) {
        switch (notificationType) {
            case "Email": return "1-2 minutes";
            case "SMS": return "30-60 seconds";
            case "Push": return "5-15 seconds";
            case "WhatsApp": return "1-3 minutes";
            case "Telegram": return "1-2 minutes";
            default: return "Unknown";
        }
    }
    
    private String getNotificationPriority(String notificationType) {
        switch (notificationType) {
            case "Email": return "Medium";
            case "SMS": return "High";
            case "Push": return "High";
            case "WhatsApp": return "Medium";
            case "Telegram": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getNotificationTemplate(String notificationType) {
        switch (notificationType) {
            case "Email": return "order_email_template.html";
            case "SMS": return "order_sms_template.txt";
            case "Push": return "order_push_template.json";
            case "WhatsApp": return "order_whatsapp_template.txt";
            case "Telegram": return "order_telegram_template.md";
            default: return "Unknown";
        }
    }
    
    private boolean getNotificationEnabled(String notificationType) {
        return !notificationType.equals("Telegram");
    }
    
    private String getPriorityClass(String notificationType) {
        switch (notificationType) {
            case "Email": return "medium";
            case "SMS": return "high";
            case "Push": return "high";
            case "WhatsApp": return "medium";
            case "Telegram": return "low";
            default: return "low";
        }
    }

    @Test
    @TM4J("OM-T10")
    @Microservice("Orders")
    @Story("Export order history")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("OM-10")})
    @DisplayName("Export order history to PDF")
    public void shouldExportOrderHistory() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Export Configuration (JSON)
        String exportConfig = "{\n" +
                "  \"action\": \"export_order_history\",\n" +
                "  \"format\": \"PDF\",\n" +
                "  \"dateRange\": \"Last 12 months\",\n" +
                "  \"orderCount\": 47,\n" +
                "  \"totalAmount\": 12547.89,\n" +
                "  \"fileName\": \"order_history_2024.pdf\",\n" +
                "  \"fileSize\": \"2.3 MB\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Export Report (HTML)
        String exportReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Order History Export Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".export{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📄 Order History Export Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"export\">\n" +
                "<h2>Export Details</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Format:</strong></span>\n" +
                "<span>PDF</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Date Range:</strong></span>\n" +
                "<span>Last 12 months</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Name:</strong></span>\n" +
                "<span>order_history_2024.pdf</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Size:</strong></span>\n" +
                "<span>2.3 MB</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Completed</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Order Count</h3>\n" +
                "<p>47</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Total Amount</h3>\n" +
                "<p>$12,547.89</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Average Order</h3>\n" +
                "<p>$267.19</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Export Data (CSV)
        String exportData = "action,format,date_range,order_count,total_amount,file_name,file_size,timestamp\n" +
                "export_order_history,PDF,Last 12 months,47,12547.89,order_history_2024.pdf,2.3 MB," + timestamp;
        
        // Export Log (Plain Text)
        String exportLog = "ORDER HISTORY EXPORT TESTING LOG\n" +
                "===================================\n\n" +
                "Action: Export Order History\n" +
                "Format: PDF\n" +
                "Date Range: Last 12 months\n" +
                "File Name: order_history_2024.pdf\n" +
                "File Size: 2.3 MB\n" +
                "Status: Completed\n\n" +
                "STATISTICS:\n" +
                "----------\n" +
                "Order Count: 47\n" +
                "Total Amount: $12,547.89\n" +
                "Average Order: $267.19\n" +
                "Date Range: January 2023 - January 2024\n\n" +
                "EXPORT DETAILS:\n" +
                "---------------\n" +
                "• PDF format with professional layout\n" +
                "• Includes order details, dates, and amounts\n" +
                "• Charts and graphs for visual analysis\n" +
                "• Ready for printing or digital sharing\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Order history export validation completed";
        
        Allure.attachment("Export Configuration (JSON)", exportConfig);
        Allure.attachment("Export Report (HTML)", exportReport);
        Allure.attachment("Export Data (CSV)", exportData);
        Allure.attachment("Export Log (Text)", exportLog);
        
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
        steps.createIssueWithTitle("Export Order History");
        steps.shouldSeeIssueWithTitle("Export Order History");
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
