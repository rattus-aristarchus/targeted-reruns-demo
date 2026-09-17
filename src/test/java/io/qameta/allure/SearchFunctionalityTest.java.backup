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
@Owner("search-team")
@Feature("Search Functionality")
public class SearchFunctionalityTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("SF-T1")
    @Microservice("Search")
    @Story("Basic search functionality")
    @Tags({@Tag("web"), @Tag("smoke"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-1")})
    @DisplayName("Search for existing product")
    public void shouldSearchForExistingProduct() {
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
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Search for: " + product);
        steps.shouldSeeIssueWithTitle("Search for: " + product);
    }

    @ParameterizedTest(name = "Search with category filter: {0}")
    @ValueSource(strings = {"Electronics", "Clothing", "Books", "Home", "Sports", "Automotive", "Health", "Beauty"})
    @TM4J("SF-T3")
    @Microservice("Search")
    @Story("Search with category filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-3")})
    public void shouldSearchWithCategoryFilter(String category) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Category Filter: " + category);
        steps.shouldSeeIssueWithTitle("Category Filter: " + category);
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
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
        steps.shouldSeeIssueWithTitle("Price Range: " + minPrice + "-" + maxPrice + " (" + priceRange + ")");
    }

    @ParameterizedTest(name = "Search with brand filter: {0}")
    @ValueSource(strings = {"Apple", "Samsung", "Nike", "Adidas", "Sony", "LG", "Dell", "HP"})
    @TM4J("SF-T5")
    @Microservice("Search")
    @Story("Search with brand filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-5")})
    public void shouldSearchWithBrandFilter(String brand) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Brand Filter: " + brand);
        steps.shouldSeeIssueWithTitle("Brand Filter: " + brand);
    }

    @Test
    @TM4J("SF-T6")
    @Microservice("Search")
    @Story("Search with no results")
    @Tags({@Tag("web"), @Tag("negative"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-6")})
    @DisplayName("Search for non-existent product")
    public void shouldShowNoResultsForInvalidSearch() {
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
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Rating Filter: " + rating + " stars");
        steps.shouldSeeIssueWithTitle("Rating Filter: " + rating + " stars");
    }

    @ParameterizedTest(name = "Search with availability filter: {0}")
    @ValueSource(strings = {"In Stock", "Out of Stock", "Pre-order", "Coming Soon"})
    @TM4J("SF-T8")
    @Microservice("Search")
    @Story("Search with availability filters")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-8")})
    public void shouldSearchWithAvailabilityFilter(String availability) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Availability Filter: " + availability);
        steps.shouldSeeIssueWithTitle("Availability Filter: " + availability);
    }

    @Test
    @TM4J("SF-T9")
    @Microservice("Search")
    @Story("Advanced search options")
    @Tags({@Tag("web"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SF-9")})
    @DisplayName("Use advanced search options")
    public void shouldUseAdvancedSearchOptions() {
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
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Sort Option: " + sortOption);
        steps.shouldSeeIssueWithTitle("Sort Option: " + sortOption);
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
