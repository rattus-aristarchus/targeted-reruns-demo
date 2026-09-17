package io.qameta.allure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import io.qameta.allure.Allure;
import java.io.InputStream;

@Layer("web")
@Owner("product-team")
@Feature("Product Management")
public class ProductManagementTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("PM-T1")
    @Microservice("Catalog")
    @Story("Add new product")
    @Tags({@Tag("web"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-1")})
    @DisplayName("Add new product to catalog")
    public void shouldAddNewProduct() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Product Configuration (JSON)
        String productConfig = "{\n" +
                "  \"productId\": \"PM-T1\",\n" +
                "  \"name\": \"Test Product\",\n" +
                "  \"category\": \"Electronics\",\n" +
                "  \"price\": 99.99,\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"active\"\n" +
                "}";
        
        // Product Report (HTML)
        String productReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Management Report</title></head>\n" +
                "<body><h1>Product Management Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Product Details</h2>\n" +
                "<ul>\n" +
                "<li>Name: Test Product</li>\n" +
                "<li>Category: Electronics</li>\n" +
                "<li>Price: $99.99</li>\n" +
                "<li>Status: Active</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Product Data (CSV)
        String productData = "product_id,name,category,price,status,timestamp\n" +
                "PM-T1,Test Product,Electronics,99.99,active," + timestamp;
        
        // Product Log (Plain Text)
        String productLog = "PRODUCT MANAGEMENT LOG\n" +
                "=====================\n\n" +
                "Product ID: PM-T1\n" +
                "Name: Test Product\n" +
                "Category: Electronics\n" +
                "Price: $99.99\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Active\n" +
                "Action: Product added successfully";
        
        Allure.attachment("Product Configuration (JSON)", productConfig);
        Allure.attachment("Product Report (HTML)", productReport);
        Allure.attachment("Product Data (CSV)", productData);
        Allure.attachment("Product Log (Text)", productLog);
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Add Product Test");
        steps.shouldSeeIssueWithTitle("Add Product Test");
    }

    @Test
    @TM4J("PM-T2")
    @Microservice("Catalog")
    @Story("Edit existing product")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-2")})
    @DisplayName("Edit product information")
    public void shouldEditProduct() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Edit Report (HTML)
        String editReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Edit Report</title></head>\n" +
                "<body><h1>Product Edit Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Edit Details</h2>\n" +
                "<ul>\n" +
                "<li>Product: Test Product</li>\n" +
                "<li>Action: Updated</li>\n" +
                "<li>Status: Success</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Edit Metrics (CSV)
        String editMetrics = "action,product,status,timestamp\n" +
                "edit,Test Product,success," + timestamp;
        
        Allure.attachment("Edit Report (HTML)", editReport);
        Allure.attachment("Edit Metrics (CSV)", editMetrics);
        Allure.attachment("Edit Timestamp", timestamp);
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Edit Product Test");
        steps.shouldSeeIssueWithTitle("Edit Product Test");
    }

    @Test
    @TM4J("PM-T3")
    @Microservice("Catalog")
    @Story("Delete product")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-3")})
    @DisplayName("Delete product from catalog")
    public void shouldDeleteProduct() {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Deletion Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Delete Product Test");
        steps.closeIssueWithTitle("Delete Product Test");
    }

    @ParameterizedTest(name = "Search product by category: {0}")
    @ValueSource(strings = {"Electronics", "Clothing", "Books", "Home", "Sports", "Automotive", "Health", "Beauty"})
    @TM4J("PM-T4")
    @Microservice("Catalog")
    @Story("Search products by category")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-4")})
    public void shouldSearchProductsByCategory(String category) {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Search Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Category: " + category);
        steps.shouldSeeIssueWithTitle("Category: " + category);
    }

    @ParameterizedTest(name = "Filter products by price range: {0}")
    @CsvSource({
        "0, 50, Low Price",
        "51, 100, Medium Price",
        "101, 200, High Price",
        "201, 1000, Premium Price"
    })
    @TM4J("PM-T5")
    @Microservice("Catalog")
    @Story("Filter products by price")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-5")})
    public void shouldFilterProductsByPrice(int minPrice, int maxPrice, String priceRange) {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Filter Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
        steps.shouldSeeIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
    }

    @ParameterizedTest(name = "Sort products by: {0}")
    @ValueSource(strings = {"Name", "Price", "Rating", "Date Added", "Popularity"})
    @TM4J("PM-T6")
    @Microservice("Catalog")
    @Story("Sort products")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-6")})
    public void shouldSortProductsBy(String sortBy) {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Sort Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Sort By: " + sortBy);
        steps.shouldSeeIssueWithTitle("Sort By: " + sortBy);
    }

    @Test
    @TM4J("PM-T7")
    @Microservice("Inventory")
    @Story("Check product stock")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-7")})
    @DisplayName("Check product inventory levels")
    public void shouldCheckProductStock() {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Stock Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Check Stock Test");
        steps.shouldSeeIssueWithTitle("Check Stock Test");
    }

    @Test
    @TM4J("PM-T8")
    @Microservice("Inventory")
    @Story("Update product stock")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-8")})
    @DisplayName("Update product inventory quantity")
    public void shouldUpdateProductStock() {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Stock Update Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Update Stock Test");
        steps.shouldSeeIssueWithTitle("Update Stock Test");
    }

    @ParameterizedTest(name = "Validate product image for: {0}")
    @ValueSource(strings = {"Product A", "Product B", "Product C", "Product D", "Product E"})
    @TM4J("PM-T9")
    @Microservice("Media")
    @Story("Validate product images")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-9")})
    public void shouldValidateProductImage(String productName) {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Image Validation Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Image Validation: " + productName);
        steps.shouldSeeIssueWithTitle("Image Validation: " + productName);
    }

    @Test
    @TM4J("PM-T10")
    @Microservice("Reviews")
    @Story("Add product review")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PM-10")})
    @DisplayName("Add review to product")
    public void shouldAddProductReview() {
        // Add video attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Product Review Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Add Review Test");
        steps.shouldSeeIssueWithTitle("Add Review Test");
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
