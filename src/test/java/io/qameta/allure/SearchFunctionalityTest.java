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
@Owner("search-team")
@Feature("Search Functionality")
public class SearchFunctionalityTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startBadDriver();
    }

    @Test
    @TM4J("SF-T1")
    @Microservice("Search")
    @Story("Basic search functionality")
    @Tags({@Tag("web"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-1")})
    @DisplayName("Search for existing product")
    public void shouldSearchForExistingProduct() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Search Configuration (JSON)
        String searchConfig = "{\n" +
                "  \"searchQuery\": \"laptop\",\n" +
                "  \"searchType\": \"basic\",\n" +
                "  \"expectedResults\": 15,\n" +
                "  \"searchTime\": \"0.3 seconds\",\n" +
                "  \"filters\": [],\n" +
                "  \"sortBy\": \"relevance\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Search Dashboard (HTML)
        String searchDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Search Functionality Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".search{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".query{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                ".results{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#2196f3;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔍 Search Functionality Dashboard</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"search\">\n" +
                "<h2 class=\"query\">Search Query: laptop</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Type:</strong></span>\n" +
                "<span>Basic Search</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Expected Results:</strong></span>\n" +
                "<span class=\"results\">15 products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span class=\"time\">0.3 seconds</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Sort By:</strong></span>\n" +
                "<span>Relevance</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Results Found</h3>\n" +
                "<p>15</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>0.3s</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Success Rate</h3>\n" +
                "<p>100%</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Search Data (CSV)
        String searchData = "search_query,search_type,expected_results,search_time,filters,sort_by,timestamp\n" +
                "laptop,basic,15,0.3 seconds,,relevance," + timestamp;
        
        // Search Log (Plain Text)
        String searchLog = "SEARCH FUNCTIONALITY TESTING LOG\n" +
                "===================================\n\n" +
                "Search Query: laptop\n" +
                "Search Type: Basic Search\n" +
                "Expected Results: 15 products\n" +
                "Search Time: 0.3 seconds\n" +
                "Filters: None\n" +
                "Sort By: Relevance\n" +
                "Success Rate: 100%\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Basic search validation completed";
        
        Allure.attachment("Search Configuration (JSON)", searchConfig);
        Allure.attachment("Search Dashboard (HTML)", searchDashboard);
        Allure.attachment("Search Data (CSV)", searchData);
        Allure.attachment("Search Log (Text)", searchLog);
        
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
        steps.createIssueWithTitle("Search for laptop");
        steps.shouldSeeIssueWithTitle("Search for laptop");
    }

    @ParameterizedTest(name = "Search for product: {0}")
    @ValueSource(strings = {"phone", "shoes", "book", "watch", "camera", "headphones", "tablet", "keyboard", "mouse", "monitor"})
    @TM4J("SF-T2")
    @Microservice("Search")
    @Story("Search different products")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-2")})
    public void shouldSearchForDifferentProducts(String product) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Product Search Configuration (JSON)
        String searchConfig = "{\n" +
                "  \"searchQuery\": \"" + product + "\",\n" +
                "  \"category\": \"" + getProductCategory(product) + "\",\n" +
                "  \"expectedResults\": " + getExpectedResults(product) + ",\n" +
                "  \"searchTime\": \"" + getSearchTime(product) + "\",\n" +
                "  \"priceRange\": \"" + getPriceRange(product) + "\",\n" +
                "  \"popularity\": \"" + getPopularity(product) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Product Search Report (HTML)
        String searchReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Product Search Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".search{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".product{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".results{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔍 Product Search Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"search\">\n" +
                "<h2 class=\"product\">Search Query: " + product + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Category:</strong></span>\n" +
                "<span>" + getProductCategory(product) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Expected Results:</strong></span>\n" +
                "<span class=\"results " + getResultsClass(product) + "\">" + getExpectedResults(product) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span class=\"time\">" + getSearchTime(product) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Price Range:</strong></span>\n" +
                "<span>" + getPriceRange(product) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Popularity:</strong></span>\n" +
                "<span>" + getPopularity(product) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Results</h3>\n" +
                "<p>" + getExpectedResults(product) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getSearchTime(product) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Category</h3>\n" +
                "<p>" + getProductCategory(product) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Product Search Data (CSV)
        String searchData = "search_query,category,expected_results,search_time,price_range,popularity,timestamp\n" +
                product + "," + getProductCategory(product) + "," + getExpectedResults(product) + "," + 
                getSearchTime(product) + "," + getPriceRange(product) + "," + getPopularity(product) + "," + timestamp;
        
        // Product Search Log (Plain Text)
        String searchLog = "PRODUCT SEARCH TESTING LOG\n" +
                "===========================\n\n" +
                "Search Query: " + product + "\n" +
                "Category: " + getProductCategory(product) + "\n" +
                "Expected Results: " + getExpectedResults(product) + " products\n" +
                "Search Time: " + getSearchTime(product) + "\n" +
                "Price Range: " + getPriceRange(product) + "\n" +
                "Popularity: " + getPopularity(product) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Product search validation completed";
        
        Allure.attachment("Search Configuration (JSON)", searchConfig);
        Allure.attachment("Search Report (HTML)", searchReport);
        Allure.attachment("Search Data (CSV)", searchData);
        Allure.attachment("Search Log (Text)", searchLog);
        
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
        steps.createIssueWithTitle("Search for: " + product);
        steps.shouldSeeIssueWithTitle("Search for: " + product);
    }
    
    private String getProductCategory(String product) {
        switch (product) {
            case "phone": return "Electronics";
            case "shoes": return "Clothing";
            case "book": return "Books";
            case "watch": return "Electronics";
            case "camera": return "Electronics";
            case "headphones": return "Electronics";
            case "tablet": return "Electronics";
            case "keyboard": return "Computers";
            case "mouse": return "Computers";
            case "monitor": return "Computers";
            default: return "General";
        }
    }
    
    private int getExpectedResults(String product) {
        switch (product) {
            case "phone": return 25;
            case "shoes": return 18;
            case "book": return 32;
            case "watch": return 12;
            case "camera": return 8;
            case "headphones": return 15;
            case "tablet": return 10;
            case "keyboard": return 20;
            case "mouse": return 22;
            case "monitor": return 14;
            default: return 5;
        }
    }
    
    private String getSearchTime(String product) {
        switch (product) {
            case "phone": return "0.2 seconds";
            case "shoes": return "0.4 seconds";
            case "book": return "0.3 seconds";
            case "watch": return "0.5 seconds";
            case "camera": return "0.6 seconds";
            case "headphones": return "0.3 seconds";
            case "tablet": return "0.4 seconds";
            case "keyboard": return "0.2 seconds";
            case "mouse": return "0.3 seconds";
            case "monitor": return "0.4 seconds";
            default: return "0.5 seconds";
        }
    }
    
    private String getPriceRange(String product) {
        switch (product) {
            case "phone": return "$200 - $1200";
            case "shoes": return "$30 - $200";
            case "book": return "$10 - $50";
            case "watch": return "$50 - $500";
            case "camera": return "$300 - $2000";
            case "headphones": return "$20 - $300";
            case "tablet": return "$200 - $800";
            case "keyboard": return "$20 - $200";
            case "mouse": return "$10 - $100";
            case "monitor": return "$100 - $800";
            default: return "$10 - $100";
        }
    }
    
    private String getPopularity(String product) {
        switch (product) {
            case "phone": return "Very High";
            case "shoes": return "High";
            case "book": return "Medium";
            case "watch": return "Medium";
            case "camera": return "Low";
            case "headphones": return "High";
            case "tablet": return "Medium";
            case "keyboard": return "High";
            case "mouse": return "High";
            case "monitor": return "Medium";
            default: return "Low";
        }
    }
    
    private String getResultsClass(String product) {
        int results = getExpectedResults(product);
        if (results >= 20) return "high";
        if (results >= 10) return "medium";
        return "low";
    }

    @ParameterizedTest(name = "Search with category filter: {0}")
    @ValueSource(strings = {"Electronics", "Clothing", "Books", "Home", "Sports", "Automotive", "Health", "Beauty"})
    @TM4J("SF-T3")
    @Microservice("Search")
    @Story("Search with category filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-3")})
    public void shouldSearchWithCategoryFilter(String category) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Category Filter Configuration (JSON)
        String filterConfig = "{\n" +
                "  \"category\": \"" + category + "\",\n" +
                "  \"filterType\": \"category\",\n" +
                "  \"productCount\": " + getCategoryProductCount(category) + ",\n" +
                "  \"averagePrice\": \"" + getCategoryAveragePrice(category) + "\",\n" +
                "  \"searchTime\": \"" + getCategorySearchTime(category) + "\",\n" +
                "  \"popularity\": \"" + getCategoryPopularity(category) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Category Filter Report (HTML)
        String filterReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Category Filter Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".filter{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".category{font-size:20px;font-weight:bold;color:#ff9800;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:16px;font-weight:bold;color:#ff9800;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🏷️ Category Filter Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"filter\">\n" +
                "<h2 class=\"category\">Category: " + category + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Filter Type:</strong></span>\n" +
                "<span>Category Filter</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getCategoryCountClass(category) + "\">" + getCategoryProductCount(category) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price\">" + getCategoryAveragePrice(category) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span>" + getCategorySearchTime(category) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Popularity:</strong></span>\n" +
                "<span>" + getCategoryPopularity(category) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getCategoryProductCount(category) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Avg Price</h3>\n" +
                "<p>" + getCategoryAveragePrice(category) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getCategorySearchTime(category) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Category Filter Data (CSV)
        String filterData = "category,filter_type,product_count,average_price,search_time,popularity,timestamp\n" +
                category + ",category," + getCategoryProductCount(category) + "," + getCategoryAveragePrice(category) + "," + 
                getCategorySearchTime(category) + "," + getCategoryPopularity(category) + "," + timestamp;
        
        // Category Filter Log (Plain Text)
        String filterLog = "CATEGORY FILTER TESTING LOG\n" +
                "=============================\n\n" +
                "Category: " + category + "\n" +
                "Filter Type: Category Filter\n" +
                "Product Count: " + getCategoryProductCount(category) + " products\n" +
                "Average Price: " + getCategoryAveragePrice(category) + "\n" +
                "Search Time: " + getCategorySearchTime(category) + "\n" +
                "Popularity: " + getCategoryPopularity(category) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Category filter validation completed";
        
        Allure.attachment("Filter Configuration (JSON)", filterConfig);
        Allure.attachment("Filter Report (HTML)", filterReport);
        Allure.attachment("Filter Data (CSV)", filterData);
        Allure.attachment("Filter Log (Text)", filterLog);
        
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
        steps.createIssueWithTitle("Category Filter: " + category);
        steps.shouldSeeIssueWithTitle("Category Filter: " + category);
    }
    
    private int getCategoryProductCount(String category) {
        switch (category) {
            case "Electronics": return 45;
            case "Clothing": return 38;
            case "Books": return 52;
            case "Home": return 28;
            case "Sports": return 35;
            case "Automotive": return 22;
            case "Health": return 31;
            case "Beauty": return 26;
            default: return 10;
        }
    }
    
    private String getCategoryAveragePrice(String category) {
        switch (category) {
            case "Electronics": return "$299.99";
            case "Clothing": return "$49.99";
            case "Books": return "$19.99";
            case "Home": return "$89.99";
            case "Sports": return "$79.99";
            case "Automotive": return "$149.99";
            case "Health": return "$34.99";
            case "Beauty": return "$24.99";
            default: return "$50.00";
        }
    }
    
    private String getCategorySearchTime(String category) {
        switch (category) {
            case "Electronics": return "0.2 seconds";
            case "Clothing": return "0.3 seconds";
            case "Books": return "0.4 seconds";
            case "Home": return "0.5 seconds";
            case "Sports": return "0.3 seconds";
            case "Automotive": return "0.6 seconds";
            case "Health": return "0.4 seconds";
            case "Beauty": return "0.3 seconds";
            default: return "0.5 seconds";
        }
    }
    
    private String getCategoryPopularity(String category) {
        switch (category) {
            case "Electronics": return "Very High";
            case "Clothing": return "High";
            case "Books": return "Medium";
            case "Home": return "Medium";
            case "Sports": return "High";
            case "Automotive": return "Low";
            case "Health": return "Medium";
            case "Beauty": return "High";
            default: return "Low";
        }
    }
    
    private String getCategoryCountClass(String category) {
        int count = getCategoryProductCount(category);
        if (count >= 40) return "high";
        if (count >= 25) return "medium";
        return "low";
    }

    @ParameterizedTest(name = "Search with price range: {0} to {1}")
    @CsvSource({
        "0, 50, Low Price",
        "51, 100, Medium Price",
        "101, 200, High Price",
        "201, 500, Premium Price"
    })
    @TM4J("SF-T4")
    @Microservice("Search")
    @Story("Search with price filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-4")})
    public void shouldSearchWithPriceRange(int minPrice, int maxPrice, String priceRange) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Price Range Configuration (JSON)
        String priceConfig = "{\n" +
                "  \"minPrice\": " + minPrice + ",\n" +
                "  \"maxPrice\": " + maxPrice + ",\n" +
                "  \"priceRange\": \"" + priceRange + "\",\n" +
                "  \"productCount\": " + getPriceRangeProductCount(minPrice, maxPrice) + ",\n" +
                "  \"averagePrice\": \"" + getPriceRangeAverage(minPrice, maxPrice) + "\",\n" +
                "  \"searchTime\": \"" + getPriceRangeSearchTime(minPrice, maxPrice) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Price Range Report (HTML)
        String priceReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Price Range Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".price{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".range{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price-value{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                ".range-badge{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".low-price{background:#4caf50;}\n" +
                ".medium-price{background:#ff9800;}\n" +
                ".high-price{background:#f44336;}\n" +
                ".premium-price{background:#9c27b0;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>💰 Price Range Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"price\">\n" +
                "<h2 class=\"range\">Price Range: $" + minPrice + " - $" + maxPrice + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Range Type:</strong></span>\n" +
                "<span class=\"range-badge " + getPriceRangeClass(priceRange) + "\">" + priceRange + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getPriceCountClass(minPrice, maxPrice) + "\">" + getPriceRangeProductCount(minPrice, maxPrice) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price-value\">" + getPriceRangeAverage(minPrice, maxPrice) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span>" + getPriceRangeSearchTime(minPrice, maxPrice) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getPriceRangeProductCount(minPrice, maxPrice) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Avg Price</h3>\n" +
                "<p>" + getPriceRangeAverage(minPrice, maxPrice) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getPriceRangeSearchTime(minPrice, maxPrice) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Price Range Data (CSV)
        String priceData = "min_price,max_price,price_range,product_count,average_price,search_time,timestamp\n" +
                minPrice + "," + maxPrice + "," + priceRange + "," + getPriceRangeProductCount(minPrice, maxPrice) + "," + 
                getPriceRangeAverage(minPrice, maxPrice) + "," + getPriceRangeSearchTime(minPrice, maxPrice) + "," + timestamp;
        
        // Price Range Log (Plain Text)
        String priceLog = "PRICE RANGE TESTING LOG\n" +
                "=======================\n\n" +
                "Price Range: $" + minPrice + " - $" + maxPrice + "\n" +
                "Range Type: " + priceRange + "\n" +
                "Product Count: " + getPriceRangeProductCount(minPrice, maxPrice) + " products\n" +
                "Average Price: " + getPriceRangeAverage(minPrice, maxPrice) + "\n" +
                "Search Time: " + getPriceRangeSearchTime(minPrice, maxPrice) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Price range filter validation completed";
        
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
        steps.shouldSeeIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
    }
    
    private int getPriceRangeProductCount(int minPrice, int maxPrice) {
        if (minPrice <= 50) return 45;
        if (minPrice <= 100) return 38;
        if (minPrice <= 200) return 28;
        return 15;
    }
    
    private String getPriceRangeAverage(int minPrice, int maxPrice) {
        if (minPrice <= 50) return "$35.99";
        if (minPrice <= 100) return "$75.99";
        if (minPrice <= 200) return "$149.99";
        return "$349.99";
    }
    
    private String getPriceRangeSearchTime(int minPrice, int maxPrice) {
        if (minPrice <= 50) return "0.2 seconds";
        if (minPrice <= 100) return "0.3 seconds";
        if (minPrice <= 200) return "0.4 seconds";
        return "0.5 seconds";
    }
    
    private String getPriceRangeClass(String priceRange) {
        switch (priceRange) {
            case "Low Price": return "low-price";
            case "Medium Price": return "medium-price";
            case "High Price": return "high-price";
            case "Premium Price": return "premium-price";
            default: return "low-price";
        }
    }
    
    private String getPriceCountClass(int minPrice, int maxPrice) {
        int count = getPriceRangeProductCount(minPrice, maxPrice);
        if (count >= 40) return "high";
        if (count >= 25) return "medium";
        return "low";
    }

    @ParameterizedTest(name = "Search with brand filter: {0}")
    @ValueSource(strings = {"Apple", "Samsung", "Nike", "Adidas", "Sony", "LG", "Dell", "HP"})
    @TM4J("SF-T5")
    @Microservice("Search")
    @Story("Search with brand filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-5")})
    public void shouldSearchWithBrandFilter(String brand) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Brand Filter Configuration (JSON)
        String brandConfig = "{\n" +
                "  \"brand\": \"" + brand + "\",\n" +
                "  \"filterType\": \"brand\",\n" +
                "  \"productCount\": " + getBrandProductCount(brand) + ",\n" +
                "  \"averagePrice\": \"" + getBrandAveragePrice(brand) + "\",\n" +
                "  \"searchTime\": \"" + getBrandSearchTime(brand) + "\",\n" +
                "  \"category\": \"" + getBrandCategory(brand) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Brand Filter Report (HTML)
        String brandReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Brand Filter Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".brand{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".brand-name{font-size:20px;font-weight:bold;color:#7b1fa2;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:16px;font-weight:bold;color:#7b1fa2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🏷️ Brand Filter Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"brand\">\n" +
                "<h2 class=\"brand-name\">Brand: " + brand + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Filter Type:</strong></span>\n" +
                "<span>Brand Filter</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getBrandCountClass(brand) + "\">" + getBrandProductCount(brand) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price\">" + getBrandAveragePrice(brand) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span>" + getBrandSearchTime(brand) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Category:</strong></span>\n" +
                "<span>" + getBrandCategory(brand) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getBrandProductCount(brand) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Avg Price</h3>\n" +
                "<p>" + getBrandAveragePrice(brand) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getBrandSearchTime(brand) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Brand Filter Data (CSV)
        String brandData = "brand,filter_type,product_count,average_price,search_time,category,timestamp\n" +
                brand + ",brand," + getBrandProductCount(brand) + "," + getBrandAveragePrice(brand) + "," + 
                getBrandSearchTime(brand) + "," + getBrandCategory(brand) + "," + timestamp;
        
        // Brand Filter Log (Plain Text)
        String brandLog = "BRAND FILTER TESTING LOG\n" +
                "=========================\n\n" +
                "Brand: " + brand + "\n" +
                "Filter Type: Brand Filter\n" +
                "Product Count: " + getBrandProductCount(brand) + " products\n" +
                "Average Price: " + getBrandAveragePrice(brand) + "\n" +
                "Search Time: " + getBrandSearchTime(brand) + "\n" +
                "Category: " + getBrandCategory(brand) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Brand filter validation completed";
        
        Allure.attachment("Brand Configuration (JSON)", brandConfig);
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Brand Filter: " + brand);
        steps.shouldSeeIssueWithTitle("Brand Filter: " + brand);
    }
    
    private int getBrandProductCount(String brand) {
        switch (brand) {
            case "Apple": return 25;
            case "Samsung": return 32;
            case "Nike": return 28;
            case "Adidas": return 24;
            case "Sony": return 18;
            case "LG": return 22;
            case "Dell": return 20;
            case "HP": return 26;
            default: return 10;
        }
    }
    
    private String getBrandAveragePrice(String brand) {
        switch (brand) {
            case "Apple": return "$599.99";
            case "Samsung": return "$399.99";
            case "Nike": return "$89.99";
            case "Adidas": return "$79.99";
            case "Sony": return "$299.99";
            case "LG": return "$249.99";
            case "Dell": return "$699.99";
            case "HP": return "$549.99";
            default: return "$199.99";
        }
    }
    
    private String getBrandSearchTime(String brand) {
        switch (brand) {
            case "Apple": return "0.2 seconds";
            case "Samsung": return "0.3 seconds";
            case "Nike": return "0.4 seconds";
            case "Adidas": return "0.4 seconds";
            case "Sony": return "0.5 seconds";
            case "LG": return "0.3 seconds";
            case "Dell": return "0.4 seconds";
            case "HP": return "0.3 seconds";
            default: return "0.5 seconds";
        }
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
            default: return "General";
        }
    }
    
    private String getBrandCountClass(String brand) {
        int count = getBrandProductCount(brand);
        if (count >= 30) return "high";
        if (count >= 20) return "medium";
        return "low";
    }

    @Test
    @TM4J("SF-T6")
    @Microservice("Search")
    @Story("Search with no results")
    @Tags({@Tag("web"), @Tag("negative"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-6")})
    @DisplayName("Search for non-existent product")
    public void shouldShowNoResultsForInvalidSearch() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // No Results Configuration (JSON)
        String noResultsConfig = "{\n" +
                "  \"searchQuery\": \"xyz123nonexistent\",\n" +
                "  \"searchType\": \"negative\",\n" +
                "  \"expectedResults\": 0,\n" +
                "  \"searchTime\": \"0.1 seconds\",\n" +
                "  \"errorMessage\": \"No results found\",\n" +
                "  \"suggestions\": [\"Try different keywords\", \"Check spelling\", \"Use fewer filters\"],\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // No Results Report (HTML)
        String noResultsReport = "<!DOCTYPE html>\n" +
                "<html><head><title>No Results Search Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".no-results{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".query{font-size:20px;font-weight:bold;color:#d32f2f;}\n" +
                ".status{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;background:#f44336;}\n" +
                ".suggestions{background:#f5f5f5;padding:15px;border-radius:4px;margin:15px 0;}\n" +
                ".suggestion{display:block;padding:5px 0;color:#666;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#f44336;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>❌ No Results Search Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"no-results\">\n" +
                "<h2 class=\"query\">Search Query: xyz123nonexistent</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Type:</strong></span>\n" +
                "<span>Negative Test</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Results Found:</strong></span>\n" +
                "<span class=\"status\">0 results</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span class=\"time\">0.1 seconds</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Error Message:</strong></span>\n" +
                "<span>No results found</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"suggestions\">\n" +
                "<h3>Suggestions:</h3>\n" +
                "<span class=\"suggestion\">• Try different keywords</span>\n" +
                "<span class=\"suggestion\">• Check spelling</span>\n" +
                "<span class=\"suggestion\">• Use fewer filters</span>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Results</h3>\n" +
                "<p>0</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>0.1s</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>No Results</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // No Results Data (CSV)
        String noResultsData = "search_query,search_type,expected_results,search_time,error_message,timestamp\n" +
                "xyz123nonexistent,negative,0,0.1 seconds,No results found," + timestamp;
        
        // No Results Log (Plain Text)
        String noResultsLog = "NO RESULTS SEARCH TESTING LOG\n" +
                "===============================\n\n" +
                "Search Query: xyz123nonexistent\n" +
                "Search Type: Negative Test\n" +
                "Expected Results: 0\n" +
                "Search Time: 0.1 seconds\n" +
                "Error Message: No results found\n\n" +
                "SUGGESTIONS:\n" +
                "------------\n" +
                "• Try different keywords\n" +
                "• Check spelling\n" +
                "• Use fewer filters\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: No results search validation completed";
        
        Allure.attachment("No Results Configuration (JSON)", noResultsConfig);
        Allure.attachment("No Results Report (HTML)", noResultsReport);
        Allure.attachment("No Results Data (CSV)", noResultsData);
        Allure.attachment("No Results Log (Text)", noResultsLog);
        
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
        steps.createIssueWithTitle("No Results Search");
        steps.shouldSeeIssueWithTitle("No Results Search");
    }

    @ParameterizedTest(name = "Search with rating filter: {0} stars")
    @ValueSource(strings = {"5", "4", "3", "2", "1"})
    @TM4J("SF-T7")
    @Microservice("Search")
    @Story("Search with rating filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-7")})
    public void shouldSearchWithRatingFilter(String rating) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int ratingValue = Integer.parseInt(rating);
        
        // Rating Filter Configuration (JSON)
        String ratingConfig = "{\n" +
                "  \"rating\": " + ratingValue + ",\n" +
                "  \"filterType\": \"rating\",\n" +
                "  \"productCount\": " + getRatingProductCount(ratingValue) + ",\n" +
                "  \"averagePrice\": \"" + getRatingAveragePrice(ratingValue) + "\",\n" +
                "  \"searchTime\": \"" + getRatingSearchTime(ratingValue) + "\",\n" +
                "  \"quality\": \"" + getRatingQuality(ratingValue) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Rating Filter Report (HTML)
        String ratingReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Rating Filter Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".rating{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".rating-stars{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".stars{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".rating-5{background:#4caf50;}\n" +
                ".rating-4{background:#8bc34a;}\n" +
                ".rating-3{background:#ff9800;}\n" +
                ".rating-2{background:#ff5722;}\n" +
                ".rating-1{background:#f44336;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>⭐ Rating Filter Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"rating\">\n" +
                "<h2 class=\"rating-stars\">Rating: <span class=\"stars rating-" + rating + "\">" + rating + " Stars</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Filter Type:</strong></span>\n" +
                "<span>Rating Filter</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getRatingCountClass(ratingValue) + "\">" + getRatingProductCount(ratingValue) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price\">" + getRatingAveragePrice(ratingValue) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span>" + getRatingSearchTime(ratingValue) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Quality Level:</strong></span>\n" +
                "<span>" + getRatingQuality(ratingValue) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getRatingProductCount(ratingValue) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Avg Price</h3>\n" +
                "<p>" + getRatingAveragePrice(ratingValue) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getRatingSearchTime(ratingValue) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Rating Filter Data (CSV)
        String ratingData = "rating,filter_type,product_count,average_price,search_time,quality,timestamp\n" +
                rating + ",rating," + getRatingProductCount(ratingValue) + "," + getRatingAveragePrice(ratingValue) + "," + 
                getRatingSearchTime(ratingValue) + "," + getRatingQuality(ratingValue) + "," + timestamp;
        
        // Rating Filter Log (Plain Text)
        String ratingLog = "RATING FILTER TESTING LOG\n" +
                "==========================\n\n" +
                "Rating: " + rating + " Stars\n" +
                "Filter Type: Rating Filter\n" +
                "Product Count: " + getRatingProductCount(ratingValue) + " products\n" +
                "Average Price: " + getRatingAveragePrice(ratingValue) + "\n" +
                "Search Time: " + getRatingSearchTime(ratingValue) + "\n" +
                "Quality Level: " + getRatingQuality(ratingValue) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Rating filter validation completed";
        
        Allure.attachment("Rating Configuration (JSON)", ratingConfig);
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Rating Filter: " + rating + " stars");
        steps.shouldSeeIssueWithTitle("Rating Filter: " + rating + " stars");
    }
    
    private int getRatingProductCount(int rating) {
        switch (rating) {
            case 5: return 12;
            case 4: return 28;
            case 3: return 45;
            case 2: return 18;
            case 1: return 8;
            default: return 0;
        }
    }
    
    private String getRatingAveragePrice(int rating) {
        switch (rating) {
            case 5: return "$299.99";
            case 4: return "$199.99";
            case 3: return "$149.99";
            case 2: return "$99.99";
            case 1: return "$49.99";
            default: return "$0.00";
        }
    }
    
    private String getRatingSearchTime(int rating) {
        switch (rating) {
            case 5: return "0.2 seconds";
            case 4: return "0.3 seconds";
            case 3: return "0.4 seconds";
            case 2: return "0.5 seconds";
            case 1: return "0.6 seconds";
            default: return "0.5 seconds";
        }
    }
    
    private String getRatingQuality(int rating) {
        switch (rating) {
            case 5: return "Excellent";
            case 4: return "Very Good";
            case 3: return "Good";
            case 2: return "Fair";
            case 1: return "Poor";
            default: return "Unknown";
        }
    }
    
    private String getRatingCountClass(int rating) {
        int count = getRatingProductCount(rating);
        if (count >= 30) return "high";
        if (count >= 15) return "medium";
        return "low";
    }

    @ParameterizedTest(name = "Search with availability filter: {0}")
    @ValueSource(strings = {"In Stock", "Out of Stock", "Pre-order", "Coming Soon"})
    @TM4J("SF-T8")
    @Microservice("Search")
    @Story("Search with availability filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-8")})
    public void shouldSearchWithAvailabilityFilter(String availability) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Availability Filter Configuration (JSON)
        String availabilityConfig = "{\n" +
                "  \"availability\": \"" + availability + "\",\n" +
                "  \"filterType\": \"availability\",\n" +
                "  \"productCount\": " + getAvailabilityProductCount(availability) + ",\n" +
                "  \"averagePrice\": \"" + getAvailabilityAveragePrice(availability) + "\",\n" +
                "  \"searchTime\": \"" + getAvailabilitySearchTime(availability) + "\",\n" +
                "  \"shippingTime\": \"" + getAvailabilityShippingTime(availability) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Availability Filter Report (HTML)
        String availabilityReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Availability Filter Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".availability{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".availability-status{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                ".status{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".in-stock{background:#4caf50;}\n" +
                ".out-of-stock{background:#f44336;}\n" +
                ".pre-order{background:#ff9800;}\n" +
                ".coming-soon{background:#9c27b0;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".price{font-size:16px;font-weight:bold;color:#1976d2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📦 Availability Filter Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"availability\">\n" +
                "<h2 class=\"availability-status\">Status: <span class=\"status " + getAvailabilityClass(availability) + "\">" + availability + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Filter Type:</strong></span>\n" +
                "<span>Availability Filter</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getAvailabilityCountClass(availability) + "\">" + getAvailabilityProductCount(availability) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Average Price:</strong></span>\n" +
                "<span class=\"price\">" + getAvailabilityAveragePrice(availability) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span>" + getAvailabilitySearchTime(availability) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Shipping Time:</strong></span>\n" +
                "<span>" + getAvailabilityShippingTime(availability) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getAvailabilityProductCount(availability) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Avg Price</h3>\n" +
                "<p>" + getAvailabilityAveragePrice(availability) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getAvailabilitySearchTime(availability) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Availability Filter Data (CSV)
        String availabilityData = "availability,filter_type,product_count,average_price,search_time,shipping_time,timestamp\n" +
                availability + ",availability," + getAvailabilityProductCount(availability) + "," + getAvailabilityAveragePrice(availability) + "," + 
                getAvailabilitySearchTime(availability) + "," + getAvailabilityShippingTime(availability) + "," + timestamp;
        
        // Availability Filter Log (Plain Text)
        String availabilityLog = "AVAILABILITY FILTER TESTING LOG\n" +
                "==================================\n\n" +
                "Availability: " + availability + "\n" +
                "Filter Type: Availability Filter\n" +
                "Product Count: " + getAvailabilityProductCount(availability) + " products\n" +
                "Average Price: " + getAvailabilityAveragePrice(availability) + "\n" +
                "Search Time: " + getAvailabilitySearchTime(availability) + "\n" +
                "Shipping Time: " + getAvailabilityShippingTime(availability) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Availability filter validation completed";
        
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
        
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Availability Filter: " + availability);
        steps.shouldSeeIssueWithTitle("Availability Filter: " + availability);
    }
    
    private int getAvailabilityProductCount(String availability) {
        switch (availability) {
            case "In Stock": return 35;
            case "Out of Stock": return 8;
            case "Pre-order": return 12;
            case "Coming Soon": return 5;
            default: return 0;
        }
    }
    
    private String getAvailabilityAveragePrice(String availability) {
        switch (availability) {
            case "In Stock": return "$199.99";
            case "Out of Stock": return "$149.99";
            case "Pre-order": return "$249.99";
            case "Coming Soon": return "$299.99";
            default: return "$0.00";
        }
    }
    
    private String getAvailabilitySearchTime(String availability) {
        switch (availability) {
            case "In Stock": return "0.2 seconds";
            case "Out of Stock": return "0.3 seconds";
            case "Pre-order": return "0.4 seconds";
            case "Coming Soon": return "0.5 seconds";
            default: return "0.5 seconds";
        }
    }
    
    private String getAvailabilityShippingTime(String availability) {
        switch (availability) {
            case "In Stock": return "1-2 business days";
            case "Out of Stock": return "N/A";
            case "Pre-order": return "3-5 business days";
            case "Coming Soon": return "TBD";
            default: return "Unknown";
        }
    }
    
    private String getAvailabilityClass(String availability) {
        switch (availability) {
            case "In Stock": return "in-stock";
            case "Out of Stock": return "out-of-stock";
            case "Pre-order": return "pre-order";
            case "Coming Soon": return "coming-soon";
            default: return "unknown";
        }
    }
    
    private String getAvailabilityCountClass(String availability) {
        int count = getAvailabilityProductCount(availability);
        if (count >= 30) return "high";
        if (count >= 10) return "medium";
        return "low";
    }

    @Test
    @TM4J("SF-T9")
    @Microservice("Search")
    @Story("Advanced search options")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-9")})
    @DisplayName("Use advanced search options")
    public void shouldUseAdvancedSearchOptions() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Advanced Search Configuration (JSON)
        String advancedConfig = "{\n" +
                "  \"searchType\": \"advanced\",\n" +
                "  \"filters\": [\"category\", \"price\", \"brand\", \"rating\", \"availability\"],\n" +
                "  \"sortOptions\": [\"relevance\", \"price\", \"rating\", \"newest\"],\n" +
                "  \"searchTime\": \"0.5 seconds\",\n" +
                "  \"resultCount\": 25,\n" +
                "  \"features\": [\"autocomplete\", \"suggestions\", \"filters\", \"sorting\"],\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Advanced Search Report (HTML)
        String advancedReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Advanced Search Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".advanced{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".search-type{font-size:20px;font-weight:bold;color:#ff9800;}\n" +
                ".filter{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;margin:2px;background:#2196f3;}\n" +
                ".sort{display:inline-block;padding:3px 8px;border-radius:12px;color:white;font-size:11px;margin:2px;background:#4caf50;}\n" +
                ".feature-list{display:flex;flex-wrap:wrap;gap:5px;margin:10px 0;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#ff9800;}\n" +
                ".count{font-size:18px;font-weight:bold;color:#ff9800;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔧 Advanced Search Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"advanced\">\n" +
                "<h2 class=\"search-type\">Advanced Search Options</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Type:</strong></span>\n" +
                "<span>Advanced Search</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Available Filters:</strong></span>\n" +
                "<div class=\"feature-list\">\n" +
                "<span class=\"filter\">Category</span>\n" +
                "<span class=\"filter\">Price</span>\n" +
                "<span class=\"filter\">Brand</span>\n" +
                "<span class=\"filter\">Rating</span>\n" +
                "<span class=\"filter\">Availability</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Sort Options:</strong></span>\n" +
                "<div class=\"feature-list\">\n" +
                "<span class=\"sort\">Relevance</span>\n" +
                "<span class=\"sort\">Price</span>\n" +
                "<span class=\"sort\">Rating</span>\n" +
                "<span class=\"sort\">Newest</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span class=\"time\">0.5 seconds</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Result Count:</strong></span>\n" +
                "<span class=\"count\">25 results</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Filters</h3>\n" +
                "<p>5</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Sort Options</h3>\n" +
                "<p>4</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>0.5s</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Advanced Search Data (CSV)
        String advancedData = "search_type,filters,sort_options,search_time,result_count,features,timestamp\n" +
                "advanced,\"category,price,brand,rating,availability\",\"relevance,price,rating,newest\",0.5 seconds,25,\"autocomplete,suggestions,filters,sorting\"," + timestamp;
        
        // Advanced Search Log (Plain Text)
        String advancedLog = "ADVANCED SEARCH TESTING LOG\n" +
                "=============================\n\n" +
                "Search Type: Advanced Search\n" +
                "Available Filters: Category, Price, Brand, Rating, Availability\n" +
                "Sort Options: Relevance, Price, Rating, Newest\n" +
                "Search Time: 0.5 seconds\n" +
                "Result Count: 25 results\n" +
                "Features: Autocomplete, Suggestions, Filters, Sorting\n\n" +
                "FILTERS AVAILABLE:\n" +
                "------------------\n" +
                "• Category Filter\n" +
                "• Price Range Filter\n" +
                "• Brand Filter\n" +
                "• Rating Filter\n" +
                "• Availability Filter\n\n" +
                "SORT OPTIONS:\n" +
                "-------------\n" +
                "• Relevance (Default)\n" +
                "• Price (Low to High)\n" +
                "• Price (High to Low)\n" +
                "• Rating (High to Low)\n" +
                "• Newest First\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Advanced search options validation completed";
        
        Allure.attachment("Advanced Configuration (JSON)", advancedConfig);
        Allure.attachment("Advanced Report (HTML)", advancedReport);
        Allure.attachment("Advanced Data (CSV)", advancedData);
        Allure.attachment("Advanced Log (Text)", advancedLog);
        
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
        steps.createIssueWithTitle("Advanced Search Options");
        steps.shouldSeeIssueWithTitle("Advanced Search Options");
    }

    @ParameterizedTest(name = "Search with sort option: {0}")
    @ValueSource(strings = {"Relevance", "Price Low to High", "Price High to Low", "Newest", "Popularity", "Rating"})
    @TM4J("SF-T10")
    @Microservice("Search")
    @Story("Search with sorting options")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-10")})
    public void shouldSearchWithSortOption(String sortOption) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Sort Option Configuration (JSON)
        String sortConfig = "{\n" +
                "  \"sortOption\": \"" + sortOption + "\",\n" +
                "  \"sortType\": \"" + getSortType(sortOption) + "\",\n" +
                "  \"productCount\": " + getSortProductCount(sortOption) + ",\n" +
                "  \"searchTime\": \"" + getSortSearchTime(sortOption) + "\",\n" +
                "  \"order\": \"" + getSortOrder(sortOption) + "\",\n" +
                "  \"performance\": \"" + getSortPerformance(sortOption) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Sort Option Report (HTML)
        String sortReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Sort Option Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".sort{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".sort-option{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".sort-badge{display:inline-block;padding:8px 16px;border-radius:20px;color:white;font-weight:bold;font-size:14px;}\n" +
                ".relevance{background:#4caf50;}\n" +
                ".price{background:#2196f3;}\n" +
                ".newest{background:#ff9800;}\n" +
                ".popularity{background:#9c27b0;}\n" +
                ".rating{background:#f44336;}\n" +
                ".count{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;}\n" +
                ".high{background:#4caf50;}\n" +
                ".medium{background:#ff9800;}\n" +
                ".low{background:#f44336;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                ".performance{font-size:16px;font-weight:bold;color:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔄 Sort Option Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"sort\">\n" +
                "<h2 class=\"sort-option\">Sort: <span class=\"sort-badge " + getSortClass(sortOption) + "\">" + sortOption + "</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Sort Type:</strong></span>\n" +
                "<span>" + getSortType(sortOption) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Product Count:</strong></span>\n" +
                "<span class=\"count " + getSortCountClass(sortOption) + "\">" + getSortProductCount(sortOption) + " products</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Search Time:</strong></span>\n" +
                "<span class=\"time\">" + getSortSearchTime(sortOption) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Order:</strong></span>\n" +
                "<span>" + getSortOrder(sortOption) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Performance:</strong></span>\n" +
                "<span class=\"performance\">" + getSortPerformance(sortOption) + "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Products</h3>\n" +
                "<p>" + getSortProductCount(sortOption) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Search Time</h3>\n" +
                "<p>" + getSortSearchTime(sortOption) + "</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Performance</h3>\n" +
                "<p>" + getSortPerformance(sortOption) + "</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Sort Option Data (CSV)
        String sortData = "sort_option,sort_type,product_count,search_time,order,performance,timestamp\n" +
                sortOption + "," + getSortType(sortOption) + "," + getSortProductCount(sortOption) + "," + 
                getSortSearchTime(sortOption) + "," + getSortOrder(sortOption) + "," + getSortPerformance(sortOption) + "," + timestamp;
        
        // Sort Option Log (Plain Text)
        String sortLog = "SORT OPTION TESTING LOG\n" +
                "=======================\n\n" +
                "Sort Option: " + sortOption + "\n" +
                "Sort Type: " + getSortType(sortOption) + "\n" +
                "Product Count: " + getSortProductCount(sortOption) + " products\n" +
                "Search Time: " + getSortSearchTime(sortOption) + "\n" +
                "Order: " + getSortOrder(sortOption) + "\n" +
                "Performance: " + getSortPerformance(sortOption) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Sort option validation completed";
        
        Allure.attachment("Sort Configuration (JSON)", sortConfig);
        Allure.attachment("Sort Report (HTML)", sortReport);
        Allure.attachment("Sort Data (CSV)", sortData);
        Allure.attachment("Sort Log (Text)", sortLog);
        
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
        steps.createIssueWithTitle("Sort Option: " + sortOption);
        steps.shouldSeeIssueWithTitle("Sort Option: " + sortOption);
    }
    
    private String getSortType(String sortOption) {
        switch (sortOption) {
            case "Relevance": return "Algorithmic";
            case "Price Low to High": return "Numeric";
            case "Price High to Low": return "Numeric";
            case "Newest": return "Date";
            case "Popularity": return "Metric";
            case "Rating": return "Numeric";
            default: return "Unknown";
        }
    }
    
    private int getSortProductCount(String sortOption) {
        switch (sortOption) {
            case "Relevance": return 30;
            case "Price Low to High": return 30;
            case "Price High to Low": return 30;
            case "Newest": return 25;
            case "Popularity": return 28;
            case "Rating": return 32;
            default: return 0;
        }
    }
    
    private String getSortSearchTime(String sortOption) {
        switch (sortOption) {
            case "Relevance": return "0.3 seconds";
            case "Price Low to High": return "0.2 seconds";
            case "Price High to Low": return "0.2 seconds";
            case "Newest": return "0.4 seconds";
            case "Popularity": return "0.5 seconds";
            case "Rating": return "0.3 seconds";
            default: return "0.5 seconds";
        }
    }
    
    private String getSortOrder(String sortOption) {
        switch (sortOption) {
            case "Relevance": return "Best Match First";
            case "Price Low to High": return "Ascending";
            case "Price High to Low": return "Descending";
            case "Newest": return "Most Recent First";
            case "Popularity": return "Most Popular First";
            case "Rating": return "Highest Rated First";
            default: return "Unknown";
        }
    }
    
    private String getSortPerformance(String sortOption) {
        switch (sortOption) {
            case "Relevance": return "Excellent";
            case "Price Low to High": return "Very Good";
            case "Price High to Low": return "Very Good";
            case "Newest": return "Good";
            case "Popularity": return "Good";
            case "Rating": return "Very Good";
            default: return "Unknown";
        }
    }
    
    private String getSortClass(String sortOption) {
        switch (sortOption) {
            case "Relevance": return "relevance";
            case "Price Low to High": return "price";
            case "Price High to Low": return "price";
            case "Newest": return "newest";
            case "Popularity": return "popularity";
            case "Rating": return "rating";
            default: return "unknown";
        }
    }
    
    private String getSortCountClass(String sortOption) {
        int count = getSortProductCount(sortOption);
        if (count >= 30) return "high";
        if (count >= 25) return "medium";
        return "low";
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
