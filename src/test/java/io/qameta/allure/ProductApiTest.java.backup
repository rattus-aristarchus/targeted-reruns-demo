package io.qameta.allure;

import org.junit.jupiter.api.Tag;
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
        steps.createIssueWithTitle("testuser", "testrepo", "Product Category: " + category);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product Category: " + category);
    }

    @ParameterizedTest(name = "Get product by ID: {0}")
    @ValueSource(ints = {21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35})
    @TM4J("PA-T2")
    @Story("Retrieve product by ID")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-2")})
    public void shouldGetProductById(int productId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Product ID: " + productId);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product ID: " + productId);
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
        steps.createIssueWithTitle("testuser", "testrepo", "Update " + productName + " Price: " + newPrice);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Update " + productName + " Price: " + newPrice);
    }

    @ParameterizedTest(name = "Delete product with ID: {0}")
    @ValueSource(ints = {36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50})
    @TM4J("PA-T4")
    @Story("Delete product by ID")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-4")})
    public void shouldDeleteProductById(int productId) {
        steps.createIssueWithTitle("testuser", "testrepo", "Delete Product ID: " + productId);
        steps.closeIssueWithTitle("testuser", "testrepo", "Delete Product ID: " + productId);
    }

    @ParameterizedTest(name = "Search products by brand: {0}")
    @ValueSource(strings = {"Apple", "Samsung", "Nike", "Adidas", "Sony", "LG", "Dell", "HP", "Canon", "Nikon"})
    @TM4J("PA-T5")
    @Story("Search products by brand")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-5")})
    public void shouldSearchProductsByBrand(String brand) {
        steps.createIssueWithTitle("testuser", "testrepo", "Search Brand: " + brand);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Search Brand: " + brand);
    }

    @ParameterizedTest(name = "Filter products by rating: {0}")
    @ValueSource(strings = {"5", "4", "3", "2", "1"})
    @TM4J("PA-T6")
    @Story("Filter products by rating")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-6")})
    public void shouldFilterProductsByRating(String rating) {
        steps.createIssueWithTitle("testuser", "testrepo", "Filter Rating: " + rating);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Filter Rating: " + rating);
    }

    @ParameterizedTest(name = "Validate product availability: {0}")
    @ValueSource(strings = {"in_stock", "out_of_stock", "pre_order", "discontinued"})
    @TM4J("PA-T7")
    @Story("Validate product availability")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-7")})
    public void shouldValidateProductAvailability(String availability) {
        steps.createIssueWithTitle("testuser", "testrepo", "Availability: " + availability);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Availability: " + availability);
    }

    @ParameterizedTest(name = "Check product dimensions for: {0}")
    @ValueSource(strings = {"Small", "Medium", "Large", "Extra Large", "Custom"})
    @TM4J("PA-T8")
    @Story("Check product dimensions")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-8")})
    public void shouldCheckProductDimensions(String size) {
        steps.createIssueWithTitle("testuser", "testrepo", "Dimensions: " + size);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Dimensions: " + size);
    }

    @ParameterizedTest(name = "Verify product images for: {0}")
    @ValueSource(strings = {"Front View", "Back View", "Side View", "Detail View", "Package View"})
    @TM4J("PA-T9")
    @Story("Verify product images")
    @Microservice("MediaService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-9")})
    public void shouldVerifyProductImages(String viewType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Image View: " + viewType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Image View: " + viewType);
    }

    @ParameterizedTest(name = "Test product variants for: {0}")
    @ValueSource(strings = {"Color", "Size", "Material", "Style", "Pattern"})
    @TM4J("PA-T10")
    @Story("Test product variants")
    @Microservice("ProductService")
    @Tags({@Tag("api"), @Tag("regression")})
    @JiraIssues({@JiraIssue("PA-10")})
    public void shouldTestProductVariants(String variantType) {
        steps.createIssueWithTitle("testuser", "testrepo", "Product Variant: " + variantType);
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Product Variant: " + variantType);
    }
}
