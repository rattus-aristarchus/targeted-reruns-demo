package io.qameta.allure;

import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.qameta.allure.Allure;
import java.io.InputStream;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

@Layer("rest")
@Owner("product-api-team")
@Feature("Product API")
public class ProductApiTest {

    private final RestSteps steps = new RestSteps();

    @ParameterizedTest(name = "Create product with category: {0}")
    @ValueSource(strings = {"Electronics", "Clothing", "Books", "Home", "Sports", "Automotive", "Health", "Beauty", "Toys", "Garden"})
    @TM4J("PA-T1")
    @Story("Create new product via API")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-1")})
    public void shouldCreateProductViaApi(String category) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Product Creation Configuration (JSON)
        String productConfig = "{\n" +
                "  \"category\": \"" + category + "\",\n" +
                "  \"productId\": \"" + getProductId(category) + "\",\n" +
                "  \"name\": \"" + getProductName(category) + "\",\n" +
                "  \"price\": " + getProductPrice(category) + ",\n" +
                "  \"description\": \"" + getProductDescription(category) + "\",\n" +
                "  \"inventory\": " + getInventoryCount(category) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Product Dashboard (HTML)
        String productDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Creation Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".product{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".category{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".price{font-size:18px;font-weight:bold;color:#2e7d32;}\n" +
                ".inventory{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".in-stock{background:#4caf50;}\n" +
                ".low-stock{background:#ff9800;}\n" +
                ".out-of-stock{background:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📦 Product Creation Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"product\">\n" +
                "<h2><span class=\"category\">" + category + "</span> " + getProductName(category) + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product ID:</strong></span>\n" +
                "<span>" + getProductId(category) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price:</strong></span>\n" +
                "<span class=\"price\">$" + getProductPrice(category) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Description:</strong></span>\n" +
                "<span>" + getProductDescription(category) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Inventory:</strong></span>\n" +
                "<span class=\"inventory " + getInventoryClass(category) + "\">" + getInventoryCount(category) + " units</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Product Data (CSV)
        String productData = "category,product_id,name,price,description,inventory,timestamp\n" +
                category + "," + getProductId(category) + "," + getProductName(category) + "," + getProductPrice(category) + "," + 
                getProductDescription(category) + "," + getInventoryCount(category) + "," + timestamp;
        
        // Product Log (Plain Text)
        String productLog = "PRODUCT CREATION TESTING LOG\n" +
                "=============================\n\n" +
                "Category: " + category + "\n" +
                "Product ID: " + getProductId(category) + "\n" +
                "Name: " + getProductName(category) + "\n" +
                "Price: $" + getProductPrice(category) + "\n" +
                "Description: " + getProductDescription(category) + "\n" +
                "Inventory: " + getInventoryCount(category) + " units\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product creation validation completed";
        
        Allure.attachment("Product Configuration (JSON)", productConfig);
        Allure.attachment("Product Dashboard (HTML)", productDashboard);
        Allure.attachment("Product Data (CSV)", productData);
        Allure.attachment("Product Log (Text)", productLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Product Category: " + category);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product Category: " + category);
    }
    
    private String getProductId(String category) {
        return "PROD-" + category.substring(0, 3).toUpperCase() + "-" + System.currentTimeMillis() % 10000;
    }
    
    private String getProductName(String category) {
        switch (category) {
            case "Electronics": return "Smart Phone";
            case "Clothing": return "Cotton T-Shirt";
            case "Books": return "Programming Guide";
            case "Home": return "Coffee Maker";
            case "Sports": return "Running Shoes";
            case "Automotive": return "Car Charger";
            case "Health": return "Vitamin Supplements";
            case "Beauty": return "Face Cream";
            case "Toys": return "Building Blocks";
            case "Garden": return "Plant Pot";
            default: return "Generic Product";
        }
    }
    
    private double getProductPrice(String category) {
        switch (category) {
            case "Electronics": return 599.99;
            case "Clothing": return 29.99;
            case "Books": return 19.99;
            case "Home": return 89.99;
            case "Sports": return 129.99;
            case "Automotive": return 39.99;
            case "Health": return 24.99;
            case "Beauty": return 34.99;
            case "Toys": return 49.99;
            case "Garden": return 15.99;
            default: return 99.99;
        }
    }
    
    private String getProductDescription(String category) {
        return "High-quality " + category.toLowerCase() + " product with excellent features and durability.";
    }
    
    private int getInventoryCount(String category) {
        switch (category) {
            case "Electronics": return 25;
            case "Clothing": return 150;
            case "Books": return 75;
            case "Home": return 40;
            case "Sports": return 60;
            case "Automotive": return 30;
            case "Health": return 100;
            case "Beauty": return 80;
            case "Toys": return 120;
            case "Garden": return 200;
            default: return 50;
        }
    }
    
    private String getInventoryClass(String category) {
        int count = getInventoryCount(category);
        if (count > 50) return "in-stock";
        if (count > 10) return "low-stock";
        return "out-of-stock";
    }

    @ParameterizedTest(name = "Get product by ID: {0}")
    @ValueSource(ints = {21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35})
    @TM4J("PA-T2")
    @Story("Retrieve product by ID")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-2")})
    public void shouldGetProductById(int productId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Product ID Configuration (JSON)
        String productConfig = "{\n" +
                "  \"productId\": " + productId + ",\n" +
                "  \"name\": \"" + getProductNameById(productId) + "\",\n" +
                "  \"category\": \"" + getProductCategoryById(productId) + "\",\n" +
                "  \"price\": " + getProductPriceById(productId) + ",\n" +
                "  \"rating\": " + getProductRatingById(productId) + ",\n" +
                "  \"reviews\": " + getProductReviewsById(productId) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Product Report (HTML)
        String productReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product ID Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".product{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".rating{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".excellent{background:#4caf50;}\n" +
                ".good{background:#8bc34a;}\n" +
                ".average{background:#ff9800;}\n" +
                ".poor{background:#f44336;}\n" +
                ".price{font-size:18px;font-weight:bold;color:#1976d2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔍 Product ID Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"product\">\n" +
                "<h2>Product ID: " + productId + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Name:</strong></span>\n" +
                "<span>" + getProductNameById(productId) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Category:</strong></span>\n" +
                "<span>" + getProductCategoryById(productId) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price:</strong></span>\n" +
                "<span class=\"price\">$" + getProductPriceById(productId) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Rating:</strong></span>\n" +
                "<span class=\"rating " + getRatingClass(getProductRatingById(productId)) + "\">" + getProductRatingById(productId) + "/5</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Reviews:</strong></span>\n" +
                "<span>" + getProductReviewsById(productId) + " reviews</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Product Data (CSV)
        String productData = "product_id,name,category,price,rating,reviews,timestamp\n" +
                productId + "," + getProductNameById(productId) + "," + getProductCategoryById(productId) + "," + 
                getProductPriceById(productId) + "," + getProductRatingById(productId) + "," + getProductReviewsById(productId) + "," + timestamp;
        
        // Product Log (Plain Text)
        String productLog = "PRODUCT ID TESTING LOG\n" +
                "======================\n\n" +
                "Product ID: " + productId + "\n" +
                "Name: " + getProductNameById(productId) + "\n" +
                "Category: " + getProductCategoryById(productId) + "\n" +
                "Price: $" + getProductPriceById(productId) + "\n" +
                "Rating: " + getProductRatingById(productId) + "/5\n" +
                "Reviews: " + getProductReviewsById(productId) + " reviews\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product ID validation completed";
        
        Allure.attachment("Product Configuration (JSON)", productConfig);
        Allure.attachment("Product Report (HTML)", productReport);
        Allure.attachment("Product Data (CSV)", productData);
        Allure.attachment("Product Log (Text)", productLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Product ID: " + productId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product ID: " + productId);
    }
    
    private String getProductNameById(int productId) {
        String[] names = {"Smart Phone", "Laptop", "Headphones", "Tablet", "Camera", "Watch", "Speaker", "Charger", "Case", "Cable", "Mouse", "Keyboard", "Monitor", "Printer", "Router"};
        return names[productId % names.length];
    }
    
    private String getProductCategoryById(int productId) {
        String[] categories = {"Electronics", "Computers", "Audio", "Mobile", "Photography", "Wearables", "Accessories", "Peripherals", "Storage", "Networking"};
        return categories[productId % categories.length];
    }
    
    private double getProductPriceById(int productId) {
        double[] prices = {99.99, 299.99, 599.99, 899.99, 1299.99, 199.99, 49.99, 29.99, 79.99, 149.99, 399.99, 699.99, 999.99, 1599.99, 249.99};
        return prices[productId % prices.length];
    }
    
    private double getProductRatingById(int productId) {
        double[] ratings = {4.5, 4.2, 4.8, 3.9, 4.6, 4.1, 4.7, 3.8, 4.3, 4.0, 4.4, 4.9, 3.7, 4.5, 4.2};
        return ratings[productId % ratings.length];
    }
    
    private int getProductReviewsById(int productId) {
        int[] reviews = {125, 89, 234, 67, 156, 98, 201, 45, 178, 112, 145, 267, 78, 189, 134};
        return reviews[productId % reviews.length];
    }
    
    private String getRatingClass(double rating) {
        if (rating >= 4.5) return "excellent";
        if (rating >= 4.0) return "good";
        if (rating >= 3.5) return "average";
        return "poor";
    }

    @ParameterizedTest(name = "Update product {0} price to {1}")
    @CsvSource({
        "Product A, 29.99",
        "Product B, 49.99",
        "Product C, 79.99",
        "Product D, 129.99",
        "Product E, 199.99"
    })
    @TM4J("PA-T3")
    @Story("Update product price")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-3")})
    public void shouldUpdateProductPrice(String productName, double newPrice) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Price Update Configuration (JSON)
        String priceConfig = "{\n" +
                "  \"productName\": \"" + productName + "\",\n" +
                "  \"newPrice\": " + newPrice + ",\n" +
                "  \"previousPrice\": " + getPreviousPrice(newPrice) + ",\n" +
                "  \"priceChange\": " + (newPrice - getPreviousPrice(newPrice)) + ",\n" +
                "  \"changePercentage\": " + getChangePercentage(newPrice, getPreviousPrice(newPrice)) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Price Report (HTML)
        String priceReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Price Update Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".price{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".price-change{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".increase{background:#f44336;}\n" +
                ".decrease{background:#4caf50;}\n" +
                ".no-change{background:#9e9e9e;}\n" +
                ".price-value{font-size:18px;font-weight:bold;color:#ff9800;}\n" +
                ".percentage{font-size:14px;font-weight:bold;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>💰 Product Price Update Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"price\">\n" +
                "<h2>Product: " + productName + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Previous Price:</strong></span>\n" +
                "<span class=\"price-value\">$" + getPreviousPrice(newPrice) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>New Price:</strong></span>\n" +
                "<span class=\"price-value\">$" + newPrice + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price Change:</strong></span>\n" +
                "<span class=\"price-change " + getChangeClass(newPrice, getPreviousPrice(newPrice)) + "\">" + 
                String.format("%+.2f", newPrice - getPreviousPrice(newPrice)) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Change %:</strong></span>\n" +
                "<span class=\"percentage " + getChangeClass(newPrice, getPreviousPrice(newPrice)) + "\">" + 
                String.format("%+.1f", getChangePercentage(newPrice, getPreviousPrice(newPrice))) + "%</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Price Data (CSV)
        String priceData = "product_name,new_price,previous_price,price_change,change_percentage,timestamp\n" +
                productName + "," + newPrice + "," + getPreviousPrice(newPrice) + "," + 
                (newPrice - getPreviousPrice(newPrice)) + "," + getChangePercentage(newPrice, getPreviousPrice(newPrice)) + "," + timestamp;
        
        // Price Log (Plain Text)
        String priceLog = "PRODUCT PRICE UPDATE TESTING LOG\n" +
                "===================================\n\n" +
                "Product: " + productName + "\n" +
                "Previous Price: $" + getPreviousPrice(newPrice) + "\n" +
                "New Price: $" + newPrice + "\n" +
                "Price Change: " + String.format("%+.2f", newPrice - getPreviousPrice(newPrice)) + "\n" +
                "Change Percentage: " + String.format("%+.1f", getChangePercentage(newPrice, getPreviousPrice(newPrice))) + "%\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product price update validation completed";
        
        Allure.attachment("Price Configuration (JSON)", priceConfig);
        Allure.attachment("Price Report (HTML)", priceReport);
        Allure.attachment("Price Data (CSV)", priceData);
        Allure.attachment("Price Log (Text)", priceLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + productName + " Price: " + newPrice);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + productName + " Price: " + newPrice);
    }
    
    private double getPreviousPrice(double newPrice) {
        return newPrice * 0.8; // Simulate 20% discount from previous price
    }
    
    private double getChangePercentage(double newPrice, double previousPrice) {
        return ((newPrice - previousPrice) / previousPrice) * 100;
    }
    
    private String getChangeClass(double newPrice, double previousPrice) {
        double change = newPrice - previousPrice;
        if (change > 0) return "increase";
        if (change < 0) return "decrease";
        return "no-change";
    }

    @ParameterizedTest(name = "Delete product with ID: {0}")
    @ValueSource(ints = {36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50})
    @TM4J("PA-T4")
    @Story("Delete product by ID")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-4")})
    public void shouldDeleteProductById(int productId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Product Delete Configuration (JSON)
        String deleteConfig = "{\n" +
                "  \"productId\": " + productId + ",\n" +
                "  \"action\": \"delete\",\n" +
                "  \"productName\": \"" + getProductNameForDelete(productId) + "\",\n" +
                "  \"reason\": \"" + getDeleteReason(productId) + "\",\n" +
                "  \"backup\": " + getBackupRequired(productId) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Delete Report (HTML)
        String deleteReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Delete Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".delete{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".backup{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".required{background:#ff9800;}\n" +
                ".not-required{background:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🗑️ Product Delete Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"delete\">\n" +
                "<h2>Product ID: " + productId + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Name:</strong></span>\n" +
                "<span>" + getProductNameForDelete(productId) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Action:</strong></span>\n" +
                "<span class=\"status\">Delete</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Reason:</strong></span>\n" +
                "<span>" + getDeleteReason(productId) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Backup:</strong></span>\n" +
                "<span class=\"backup " + (getBackupRequired(productId) ? "required" : "not-required") + "\">" + 
                (getBackupRequired(productId) ? "Required" : "Not Required") + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Delete Data (CSV)
        String deleteData = "product_id,action,product_name,reason,backup_required,timestamp\n" +
                productId + ",delete," + getProductNameForDelete(productId) + "," + getDeleteReason(productId) + "," + 
                getBackupRequired(productId) + "," + timestamp;
        
        // Delete Log (Plain Text)
        String deleteLog = "PRODUCT DELETE TESTING LOG\n" +
                "==========================\n\n" +
                "Product ID: " + productId + "\n" +
                "Product Name: " + getProductNameForDelete(productId) + "\n" +
                "Action: Delete\n" +
                "Reason: " + getDeleteReason(productId) + "\n" +
                "Backup: " + (getBackupRequired(productId) ? "Required" : "Not Required") + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product delete validation completed";
        
        Allure.attachment("Delete Configuration (JSON)", deleteConfig);
        Allure.attachment("Delete Report (HTML)", deleteReport);
        Allure.attachment("Delete Data (CSV)", deleteData);
        Allure.attachment("Delete Log (Text)", deleteLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Product ID: " + productId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Product ID: " + productId);
    }
    
    private String getProductNameForDelete(int productId) {
        String[] names = {"Smart Watch", "Gaming Laptop", "Wireless Earbuds", "4K Monitor", "Mechanical Keyboard", "Gaming Mouse", "Webcam", "Microphone", "Headset", "Charger", "Cable", "Adapter", "Hub", "Stand", "Case"};
        return names[productId % names.length];
    }
    
    private String getDeleteReason(int productId) {
        if (productId % 3 == 0) return "Discontinued";
        if (productId % 3 == 1) return "Quality Issues";
        return "Low Sales";
    }
    
    private boolean getBackupRequired(int productId) {
        return productId % 2 == 0;
    }

    @ParameterizedTest(name = "Search products by brand: {0}")
    @ValueSource(strings = {"Apple", "Samsung", "Nike", "Adidas", "Sony", "LG", "Dell", "HP", "Canon", "Nikon"})
    @TM4J("PA-T5")
    @Story("Search products by brand")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-5")})
    public void shouldSearchProductsByBrand(String brand) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Brand Search Configuration (JSON)
        String searchConfig = "{\n" +
                "  \"brand\": \"" + brand + "\",\n" +
                "  \"category\": \"" + getBrandCategory(brand) + "\",\n" +
                "  \"productCount\": " + getProductCount(brand) + ",\n" +
                "  \"priceRange\": \"" + getPriceRange(brand) + "\",\n" +
                "  \"averageRating\": " + getAverageRating(brand) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Brand Report (HTML)
        String brandReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Brand Search Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".brand{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".brand-name{font-size:20px;font-weight:bold;color:#7b1fa2;}\n" +
                ".rating{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".excellent{background:#4caf50;}\n" +
                ".good{background:#8bc34a;}\n" +
                ".average{background:#ff9800;}\n" +
                ".poor{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔍 Brand Search Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"brand\">\n" +
                "<h2 class=\"brand-name\">" + brand + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Category:</strong></span>\n" +
                "<span>" + getBrandCategory(brand) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price Range:</strong></span>\n" +
                "<span>" + getPriceRange(brand) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Rating:</strong></span>\n" +
                "<span class=\"rating " + getRatingClass(getAverageRating(brand)) + "\">" + getAverageRating(brand) + "/5</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Product Count</h3>\n" +
                "<p>" + getProductCount(brand) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Price Range</h3>\n" +
                "<p>" + getPriceRange(brand) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Average Rating</h3>\n" +
                "<p>" + getAverageRating(brand) + "/5</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Brand Data (CSV)
        String brandData = "brand,category,product_count,price_range,average_rating,timestamp\n" +
                brand + "," + getBrandCategory(brand) + "," + getProductCount(brand) + "," + 
                getPriceRange(brand) + "," + getAverageRating(brand) + "," + timestamp;
        
        // Brand Log (Plain Text)
        String brandLog = "BRAND SEARCH TESTING LOG\n" +
                "========================\n\n" +
                "Brand: " + brand + "\n" +
                "Category: " + getBrandCategory(brand) + "\n" +
                "Product Count: " + getProductCount(brand) + "\n" +
                "Price Range: " + getPriceRange(brand) + "\n" +
                "Average Rating: " + getAverageRating(brand) + "/5\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Brand search validation completed";
        
        Allure.attachment("Search Configuration (JSON)", searchConfig);
        Allure.attachment("Brand Report (HTML)", brandReport);
        Allure.attachment("Brand Data (CSV)", brandData);
        Allure.attachment("Brand Log (Text)", brandLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Search Brand: " + brand);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Brand: " + brand);
    }
    
    private String getBrandCategory(String brand) {
        switch (brand) {
            case "Apple": return "Electronics";
            case "Samsung": return "Electronics";
            case "Nike": return "Sports";
            case "Adidas": return "Sports";
            case "Sony": return "Electronics";
            case "LG": return "Electronics";
            case "Dell": return "Computers";
            case "HP": return "Computers";
            case "Canon": return "Photography";
            case "Nikon": return "Photography";
            default: return "General";
        }
    }
    
    private int getProductCount(String brand) {
        switch (brand) {
            case "Apple": return 45;
            case "Samsung": return 38;
            case "Nike": return 67;
            case "Adidas": return 52;
            case "Sony": return 29;
            case "LG": return 34;
            case "Dell": return 41;
            case "HP": return 36;
            case "Canon": return 23;
            case "Nikon": return 19;
            default: return 25;
        }
    }
    
    private String getPriceRange(String brand) {
        switch (brand) {
            case "Apple": return "$99 - $2,999";
            case "Samsung": return "$49 - $1,999";
            case "Nike": return "$25 - $299";
            case "Adidas": return "$20 - $249";
            case "Sony": return "$79 - $1,499";
            case "LG": return "$89 - $1,299";
            case "Dell": return "$299 - $2,499";
            case "HP": return "$199 - $1,999";
            case "Canon": return "$149 - $4,999";
            case "Nikon": return "$199 - $5,999";
            default: return "$50 - $500";
        }
    }
    
    private double getAverageRating(String brand) {
        switch (brand) {
            case "Apple": return 4.6;
            case "Samsung": return 4.3;
            case "Nike": return 4.4;
            case "Adidas": return 4.2;
            case "Sony": return 4.5;
            case "LG": return 4.1;
            case "Dell": return 4.0;
            case "HP": return 3.9;
            case "Canon": return 4.7;
            case "Nikon": return 4.8;
            default: return 4.0;
        }
    }

    @ParameterizedTest(name = "Filter products by rating: {0}")
    @ValueSource(strings = {"5", "4", "3", "2", "1"})
    @TM4J("PA-T6")
    @Story("Filter products by rating")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-6")})
    public void shouldFilterProductsByRating(String rating) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int ratingValue = Integer.parseInt(rating);
        
        // Rating Filter Configuration (JSON)
        String filterConfig = "{\n" +
                "  \"rating\": " + ratingValue + ",\n" +
                "  \"productCount\": " + getProductCountByRating(ratingValue) + ",\n" +
                "  \"averagePrice\": " + getAveragePriceByRating(ratingValue) + ",\n" +
                "  \"topBrand\": \"" + getTopBrandByRating(ratingValue) + "\",\n" +
                "  \"category\": \"" + getTopCategoryByRating(ratingValue) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Rating Report (HTML)
        String ratingReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Rating Filter Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".rating{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".rating-badge{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:16px;}\n" +
                ".rating-5{background:#4caf50;}\n" +
                ".rating-4{background:#8bc34a;}\n" +
                ".rating-3{background:#ff9800;}\n" +
                ".rating-2{background:#ff5722;}\n" +
                ".rating-1{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:18px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>⭐ Rating Filter Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"rating\">\n" +
                "<h2>Filter: <span class=\"rating-badge rating-" + rating + "\">" + rating + " Stars</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span>" + getProductCountByRating(ratingValue) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price\">$" + getAveragePriceByRating(ratingValue) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Top Brand:</strong></span>\n" +
                "<span>" + getTopBrandByRating(ratingValue) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Top Category:</strong></span>\n" +
                "<span>" + getTopCategoryByRating(ratingValue) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products Found</h3>\n" +
                "<p>" + getProductCountByRating(ratingValue) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Average Price</h3>\n" +
                "<p>$" + getAveragePriceByRating(ratingValue) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Top Brand</h3>\n" +
                "<p>" + getTopBrandByRating(ratingValue) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Rating Data (CSV)
        String ratingData = "rating,product_count,average_price,top_brand,top_category,timestamp\n" +
                rating + "," + getProductCountByRating(ratingValue) + "," + getAveragePriceByRating(ratingValue) + "," + 
                getTopBrandByRating(ratingValue) + "," + getTopCategoryByRating(ratingValue) + "," + timestamp;
        
        // Rating Log (Plain Text)
        String ratingLog = "RATING FILTER TESTING LOG\n" +
                "=========================\n\n" +
                "Rating Filter: " + rating + " Stars\n" +
                "Product Count: " + getProductCountByRating(ratingValue) + "\n" +
                "Average Price: $" + getAveragePriceByRating(ratingValue) + "\n" +
                "Top Brand: " + getTopBrandByRating(ratingValue) + "\n" +
                "Top Category: " + getTopCategoryByRating(ratingValue) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Rating filter validation completed";
        
        Allure.attachment("Filter Configuration (JSON)", filterConfig);
        Allure.attachment("Rating Report (HTML)", ratingReport);
        Allure.attachment("Rating Data (CSV)", ratingData);
        Allure.attachment("Rating Log (Text)", ratingLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Rating: " + rating);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Rating: " + rating);
    }
    
    private int getProductCountByRating(int rating) {
        switch (rating) {
            case 5: return 89;
            case 4: return 156;
            case 3: return 234;
            case 2: return 67;
            case 1: return 23;
            default: return 0;
        }
    }
    
    private double getAveragePriceByRating(int rating) {
        switch (rating) {
            case 5: return 299.99;
            case 4: return 199.99;
            case 3: return 149.99;
            case 2: return 99.99;
            case 1: return 49.99;
            default: return 0.0;
        }
    }
    
    private String getTopBrandByRating(int rating) {
        switch (rating) {
            case 5: return "Apple";
            case 4: return "Samsung";
            case 3: return "Sony";
            case 2: return "Generic";
            case 1: return "Unknown";
            default: return "N/A";
        }
    }
    
    private String getTopCategoryByRating(int rating) {
        switch (rating) {
            case 5: return "Electronics";
            case 4: return "Computers";
            case 3: return "Accessories";
            case 2: return "General";
            case 1: return "Basic";
            default: return "N/A";
        }
    }

    @ParameterizedTest(name = "Validate product availability: {0}")
    @ValueSource(strings = {"in_stock", "out_of_stock", "pre_order", "discontinued"})
    @TM4J("PA-T7")
    @Story("Validate product availability")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-7")})
    public void shouldValidateProductAvailability(String availability) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Availability Configuration (JSON)
        String availabilityConfig = "{\n" +
                "  \"availability\": \"" + availability + "\",\n" +
                "  \"productCount\": " + getProductCountByAvailability(availability) + ",\n" +
                "  \"restockDate\": \"" + getRestockDate(availability) + "\",\n" +
                "  \"shippingTime\": \"" + getShippingTime(availability) + "\",\n" +
                "  \"priceChange\": " + getPriceChange(availability) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Availability Report (HTML)
        String availabilityReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Availability Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".availability{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".status{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".in-stock{background:#4caf50;}\n" +
                ".out-of-stock{background:#f44336;}\n" +
                ".pre-order{background:#ff9800;}\n" +
                ".discontinued{background:#9e9e9e;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:16px;font-weight:bold;color:#1976d2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📦 Product Availability Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"availability\">\n" +
                "<h2>Status: <span class=\"status " + getAvailabilityClass(availability) + "\">" + getAvailabilityDisplay(availability) + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span>" + getProductCountByAvailability(availability) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Restock Date:</strong></span>\n" +
                "<span>" + getRestockDate(availability) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Shipping Time:</strong></span>\n" +
                "<span>" + getShippingTime(availability) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price Change:</strong></span>\n" +
                "<span class=\"price\">" + getPriceChange(availability) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getProductCountByAvailability(availability) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Restock</h3>\n" +
                "<p>" + getRestockDate(availability) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Shipping</h3>\n" +
                "<p>" + getShippingTime(availability) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Availability Data (CSV)
        String availabilityData = "availability,product_count,restock_date,shipping_time,price_change,timestamp\n" +
                availability + "," + getProductCountByAvailability(availability) + "," + getRestockDate(availability) + "," + 
                getShippingTime(availability) + "," + getPriceChange(availability) + "," + timestamp;
        
        // Availability Log (Plain Text)
        String availabilityLog = "PRODUCT AVAILABILITY TESTING LOG\n" +
                "===================================\n\n" +
                "Availability: " + getAvailabilityDisplay(availability) + "\n" +
                "Product Count: " + getProductCountByAvailability(availability) + "\n" +
                "Restock Date: " + getRestockDate(availability) + "\n" +
                "Shipping Time: " + getShippingTime(availability) + "\n" +
                "Price Change: " + getPriceChange(availability) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product availability validation completed";
        
        Allure.attachment("Availability Configuration (JSON)", availabilityConfig);
        Allure.attachment("Availability Report (HTML)", availabilityReport);
        Allure.attachment("Availability Data (CSV)", availabilityData);
        Allure.attachment("Availability Log (Text)", availabilityLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Availability: " + availability);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Availability: " + availability);
    }
    
    private int getProductCountByAvailability(String availability) {
        switch (availability) {
            case "in_stock": return 234;
            case "out_of_stock": return 45;
            case "pre_order": return 12;
            case "discontinued": return 8;
            default: return 0;
        }
    }
    
    private String getRestockDate(String availability) {
        switch (availability) {
            case "in_stock": return "N/A";
            case "out_of_stock": return "2024-02-15";
            case "pre_order": return "2024-03-01";
            case "discontinued": return "N/A";
            default: return "Unknown";
        }
    }
    
    private String getShippingTime(String availability) {
        switch (availability) {
            case "in_stock": return "1-2 business days";
            case "out_of_stock": return "N/A";
            case "pre_order": return "3-5 business days";
            case "discontinued": return "N/A";
            default: return "Unknown";
        }
    }
    
    private String getPriceChange(String availability) {
        switch (availability) {
            case "in_stock": return "No change";
            case "out_of_stock": return "N/A";
            case "pre_order": return "Pre-order discount: 10%";
            case "discontinued": return "Clearance: 50% off";
            default: return "Unknown";
        }
    }
    
    private String getAvailabilityClass(String availability) {
        switch (availability) {
            case "in_stock": return "in-stock";
            case "out_of_stock": return "out-of-stock";
            case "pre_order": return "pre-order";
            case "discontinued": return "discontinued";
            default: return "unknown";
        }
    }
    
    private String getAvailabilityDisplay(String availability) {
        switch (availability) {
            case "in_stock": return "In Stock";
            case "out_of_stock": return "Out of Stock";
            case "pre_order": return "Pre-Order";
            case "discontinued": return "Discontinued";
            default: return "Unknown";
        }
    }

    @ParameterizedTest(name = "Check product dimensions for: {0}")
    @ValueSource(strings = {"Small", "Medium", "Large", "Extra Large", "Custom"})
    @TM4J("PA-T8")
    @Story("Check product dimensions")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-8")})
    public void shouldCheckProductDimensions(String size) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Dimensions Configuration (JSON)
        String dimensionsConfig = "{\n" +
                "  \"size\": \"" + size + "\",\n" +
                "  \"width\": " + getWidth(size) + ",\n" +
                "  \"height\": " + getHeight(size) + ",\n" +
                "  \"depth\": " + getDepth(size) + ",\n" +
                "  \"weight\": " + getWeight(size) + ",\n" +
                "  \"volume\": " + getVolume(size) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Dimensions Report (HTML)
        String dimensionsReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Dimensions Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".dimensions{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".size-badge{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".small{background:#4caf50;}\n" +
                ".medium{background:#2196f3;}\n" +
                ".large{background:#ff9800;}\n" +
                ".extra-large{background:#f44336;}\n" +
                ".custom{background:#9c27b0;}\n" +
                ".measurement{font-size:16px;font-weight:bold;color:#ff9800;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📏 Product Dimensions Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"dimensions\">\n" +
                "<h2>Size: <span class=\"size-badge " + getSizeClass(size) + "\">" + size + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Width:</strong></span>\n" +
                "<span class=\"measurement\">" + getWidth(size) + " cm</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Height:</strong></span>\n" +
                "<span class=\"measurement\">" + getHeight(size) + " cm</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Depth:</strong></span>\n" +
                "<span class=\"measurement\">" + getDepth(size) + " cm</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Weight:</strong></span>\n" +
                "<span class=\"measurement\">" + getWeight(size) + " kg</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Volume:</strong></span>\n" +
                "<span class=\"measurement\">" + getVolume(size) + " cm³</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Width</h3>\n" +
                "<p>" + getWidth(size) + " cm</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Height</h3>\n" +
                "<p>" + getHeight(size) + " cm</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Weight</h3>\n" +
                "<p>" + getWeight(size) + " kg</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Dimensions Data (CSV)
        String dimensionsData = "size,width,height,depth,weight,volume,timestamp\n" +
                size + "," + getWidth(size) + "," + getHeight(size) + "," + getDepth(size) + "," + 
                getWeight(size) + "," + getVolume(size) + "," + timestamp;
        
        // Dimensions Log (Plain Text)
        String dimensionsLog = "PRODUCT DIMENSIONS TESTING LOG\n" +
                "==================================\n\n" +
                "Size: " + size + "\n" +
                "Width: " + getWidth(size) + " cm\n" +
                "Height: " + getHeight(size) + " cm\n" +
                "Depth: " + getDepth(size) + " cm\n" +
                "Weight: " + getWeight(size) + " kg\n" +
                "Volume: " + getVolume(size) + " cm³\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product dimensions validation completed";
        
        Allure.attachment("Dimensions Configuration (JSON)", dimensionsConfig);
        Allure.attachment("Dimensions Report (HTML)", dimensionsReport);
        Allure.attachment("Dimensions Data (CSV)", dimensionsData);
        Allure.attachment("Dimensions Log (Text)", dimensionsLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Dimensions: " + size);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Dimensions: " + size);
    }
    
    private double getWidth(String size) {
        switch (size) {
            case "Small": return 10.0;
            case "Medium": return 15.0;
            case "Large": return 20.0;
            case "Extra Large": return 25.0;
            case "Custom": return 18.5;
            default: return 0.0;
        }
    }
    
    private double getHeight(String size) {
        switch (size) {
            case "Small": return 8.0;
            case "Medium": return 12.0;
            case "Large": return 16.0;
            case "Extra Large": return 20.0;
            case "Custom": return 14.2;
            default: return 0.0;
        }
    }
    
    private double getDepth(String size) {
        switch (size) {
            case "Small": return 5.0;
            case "Medium": return 8.0;
            case "Large": return 12.0;
            case "Extra Large": return 15.0;
            case "Custom": return 9.8;
            default: return 0.0;
        }
    }
    
    private double getWeight(String size) {
        switch (size) {
            case "Small": return 0.5;
            case "Medium": return 1.2;
            case "Large": return 2.1;
            case "Extra Large": return 3.5;
            case "Custom": return 1.8;
            default: return 0.0;
        }
    }
    
    private double getVolume(String size) {
        return getWidth(size) * getHeight(size) * getDepth(size);
    }
    
    private String getSizeClass(String size) {
        switch (size) {
            case "Small": return "small";
            case "Medium": return "medium";
            case "Large": return "large";
            case "Extra Large": return "extra-large";
            case "Custom": return "custom";
            default: return "unknown";
        }
    }

    @ParameterizedTest(name = "Verify product images for: {0}")
    @ValueSource(strings = {"Front View", "Back View", "Side View", "Detail View", "Package View"})
    @TM4J("PA-T9")
    @Story("Verify product images")
    @Microservice("MediaService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-9")})
    public void shouldVerifyProductImages(String viewType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Image Configuration (JSON)
        String imageConfig = "{\n" +
                "  \"viewType\": \"" + viewType + "\",\n" +
                "  \"imageCount\": " + getImageCount(viewType) + ",\n" +
                "  \"resolution\": \"" + getResolution(viewType) + "\",\n" +
                "  \"fileSize\": \"" + getFileSize(viewType) + "\",\n" +
                "  \"format\": \"" + getImageFormat(viewType) + "\",\n" +
                "  \"quality\": \"" + getImageQuality(viewType) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Image Report (HTML)
        String imageReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Images Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".image{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".view-type{font-size:18px;font-weight:bold;color:#7b1fa2;}\n" +
                ".quality{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".resolution{font-size:16px;font-weight:bold;color:#7b1fa2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🖼️ Product Images Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"image\">\n" +
                "<h2 class=\"view-type\">" + viewType + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Image Count:</strong></span>\n" +
                "<span>" + getImageCount(viewType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Resolution:</strong></span>\n" +
                "<span class=\"resolution\">" + getResolution(viewType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Size:</strong></span>\n" +
                "<span>" + getFileSize(viewType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Format:</strong></span>\n" +
                "<span>" + getImageFormat(viewType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Quality:</strong></span>\n" +
                "<span class=\"quality " + getQualityClass(viewType) + "\">" + getImageQuality(viewType) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Images</h3>\n" +
                "<p>" + getImageCount(viewType) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Resolution</h3>\n" +
                "<p>" + getResolution(viewType) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>File Size</h3>\n" +
                "<p>" + getFileSize(viewType) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Image Data (CSV)
        String imageData = "view_type,image_count,resolution,file_size,format,quality,timestamp\n" +
                viewType + "," + getImageCount(viewType) + "," + getResolution(viewType) + "," + 
                getFileSize(viewType) + "," + getImageFormat(viewType) + "," + getImageQuality(viewType) + "," + timestamp;
        
        // Image Log (Plain Text)
        String imageLog = "PRODUCT IMAGES TESTING LOG\n" +
                "===========================\n\n" +
                "View Type: " + viewType + "\n" +
                "Image Count: " + getImageCount(viewType) + "\n" +
                "Resolution: " + getResolution(viewType) + "\n" +
                "File Size: " + getFileSize(viewType) + "\n" +
                "Format: " + getImageFormat(viewType) + "\n" +
                "Quality: " + getImageQuality(viewType) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product images validation completed";
        
        Allure.attachment("Image Configuration (JSON)", imageConfig);
        Allure.attachment("Image Report (HTML)", imageReport);
        Allure.attachment("Image Data (CSV)", imageData);
        Allure.attachment("Image Log (Text)", imageLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Image View: " + viewType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Image View: " + viewType);
    }
    
    private int getImageCount(String viewType) {
        switch (viewType) {
            case "Front View": return 3;
            case "Back View": return 2;
            case "Side View": return 4;
            case "Detail View": return 6;
            case "Package View": return 2;
            default: return 1;
        }
    }
    
    private String getResolution(String viewType) {
        switch (viewType) {
            case "Front View": return "1920x1080";
            case "Back View": return "1920x1080";
            case "Side View": return "1600x1200";
            case "Detail View": return "2560x1440";
            case "Package View": return "1280x720";
            default: return "1024x768";
        }
    }
    
    private String getFileSize(String viewType) {
        switch (viewType) {
            case "Front View": return "2.3 MB";
            case "Back View": return "2.1 MB";
            case "Side View": return "1.8 MB";
            case "Detail View": return "4.2 MB";
            case "Package View": return "1.2 MB";
            default: return "1.0 MB";
        }
    }
    
    private String getImageFormat(String viewType) {
        switch (viewType) {
            case "Front View": return "JPEG";
            case "Back View": return "JPEG";
            case "Side View": return "PNG";
            case "Detail View": return "PNG";
            case "Package View": return "JPEG";
            default: return "JPEG";
        }
    }
    
    private String getImageQuality(String viewType) {
        switch (viewType) {
            case "Front View": return "High";
            case "Back View": return "High";
            case "Side View": return "Medium";
            case "Detail View": return "High";
            case "Package View": return "Medium";
            default: return "Medium";
        }
    }
    
    private String getQualityClass(String viewType) {
        switch (viewType) {
            case "Front View": return "high";
            case "Back View": return "high";
            case "Side View": return "medium";
            case "Detail View": return "high";
            case "Package View": return "medium";
            default: return "medium";
        }
    }

    @ParameterizedTest(name = "Test product variants for: {0}")
    @ValueSource(strings = {"Color", "Size", "Material", "Style", "Pattern"})
    @TM4J("PA-T10")
    @Story("Test product variants")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-10")})
    public void shouldTestProductVariants(String variantType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Variant Configuration (JSON)
        String variantConfig = "{\n" +
                "  \"variantType\": \"" + variantType + "\",\n" +
                "  \"options\": " + getVariantOptions(variantType) + ",\n" +
                "  \"defaultOption\": \"" + getDefaultOption(variantType) + "\",\n" +
                "  \"priceImpact\": \"" + getPriceImpact(variantType) + "\",\n" +
                "  \"availability\": " + getVariantAvailability(variantType) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Variant Report (HTML)
        String variantReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Variants Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".variant{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".variant-type{font-size:18px;font-weight:bold;color:#2e7d32;}\n" +
                ".option{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;margin:2px;}\n" +
                ".color{background:#e91e63;}\n" +
                ".size{background:#2196f3;}\n" +
                ".material{background:#ff9800;}\n" +
                ".style{background:#9c27b0;}\n" +
                ".pattern{background:#607d8b;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".options{display:flex;flex-wrap:wrap;gap:5px;margin:10px 0;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🎨 Product Variants Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"variant\">\n" +
                "<h2 class=\"variant-type\">" + variantType + " Variants</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Options:</strong></span>\n" +
                "<div class=\"options\">" + getVariantOptionsHtml(variantType) + "</div>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Default:</strong></span>\n" +
                "<span>" + getDefaultOption(variantType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price Impact:</strong></span>\n" +
                "<span>" + getPriceImpact(variantType) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Availability:</strong></span>\n" +
                "<span>" + (getVariantAvailability(variantType) ? "Available" : "Not Available") + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Options</h3>\n" +
                "<p>" + getOptionCount(variantType) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Default</h3>\n" +
                "<p>" + getDefaultOption(variantType) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Available</h3>\n" +
                "<p>" + (getVariantAvailability(variantType) ? "Yes" : "No") + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Variant Data (CSV)
        String variantData = "variant_type,options,default_option,price_impact,availability,timestamp\n" +
                variantType + "," + getVariantOptions(variantType) + "," + getDefaultOption(variantType) + "," + 
                getPriceImpact(variantType) + "," + getVariantAvailability(variantType) + "," + timestamp;
        
        // Variant Log (Plain Text)
        String variantLog = "PRODUCT VARIANTS TESTING LOG\n" +
                "==============================\n\n" +
                "Variant Type: " + variantType + "\n" +
                "Options: " + getVariantOptions(variantType) + "\n" +
                "Default Option: " + getDefaultOption(variantType) + "\n" +
                "Price Impact: " + getPriceImpact(variantType) + "\n" +
                "Availability: " + (getVariantAvailability(variantType) ? "Available" : "Not Available") + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product variants validation completed";
        
        Allure.attachment("Variant Configuration (JSON)", variantConfig);
        Allure.attachment("Variant Report (HTML)", variantReport);
        Allure.attachment("Variant Data (CSV)", variantData);
        Allure.attachment("Variant Log (Text)", variantLog);
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Product Variant: " + variantType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product Variant: " + variantType);
    }
    
    private String getVariantOptions(String variantType) {
        switch (variantType) {
            case "Color": return "[\"Red\", \"Blue\", \"Green\", \"Black\", \"White\"]";
            case "Size": return "[\"XS\", \"S\", \"M\", \"L\", \"XL\", \"XXL\"]";
            case "Material": return "[\"Cotton\", \"Polyester\", \"Wool\", \"Silk\", \"Linen\"]";
            case "Style": return "[\"Classic\", \"Modern\", \"Vintage\", \"Casual\", \"Formal\"]";
            case "Pattern": return "[\"Solid\", \"Striped\", \"Polka Dot\", \"Floral\", \"Geometric\"]";
            default: return "[]";
        }
    }
    
    private String getVariantOptionsHtml(String variantType) {
        String[] options = getVariantOptionsArray(variantType);
        StringBuilder html = new StringBuilder();
        for (String option : options) {
            html.append("<span class=\"option ").append(getVariantClass(variantType)).append("\">").append(option).append("</span>");
        }
        return html.toString();
    }
    
    private String[] getVariantOptionsArray(String variantType) {
        switch (variantType) {
            case "Color": return new String[]{"Red", "Blue", "Green", "Black", "White"};
            case "Size": return new String[]{"XS", "S", "M", "L", "XL", "XXL"};
            case "Material": return new String[]{"Cotton", "Polyester", "Wool", "Silk", "Linen"};
            case "Style": return new String[]{"Classic", "Modern", "Vintage", "Casual", "Formal"};
            case "Pattern": return new String[]{"Solid", "Striped", "Polka Dot", "Floral", "Geometric"};
            default: return new String[]{};
        }
    }
    
    private String getDefaultOption(String variantType) {
        switch (variantType) {
            case "Color": return "Black";
            case "Size": return "M";
            case "Material": return "Cotton";
            case "Style": return "Classic";
            case "Pattern": return "Solid";
            default: return "Default";
        }
    }
    
    private String getPriceImpact(String variantType) {
        switch (variantType) {
            case "Color": return "No impact";
            case "Size": return "No impact";
            case "Material": return "±$10-50";
            case "Style": return "±$5-25";
            case "Pattern": return "No impact";
            default: return "Unknown";
        }
    }
    
    private boolean getVariantAvailability(String variantType) {
        return !variantType.equals("Pattern");
    }
    
    private int getOptionCount(String variantType) {
        return getVariantOptionsArray(variantType).length;
    }
    
    private String getVariantClass(String variantType) {
        switch (variantType) {
            case "Color": return "color";
            case "Size": return "size";
            case "Material": return "material";
            case "Style": return "style";
            case "Pattern": return "pattern";
            default: return "default";
        }
    }
}
