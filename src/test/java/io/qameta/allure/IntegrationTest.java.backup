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
@Owner("integration-team")
@Feature("Integration")
public class IntegrationTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("INT-T1")
    @Microservice("Payment Gateway")
    @Story("Payment integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-1")})
    @DisplayName("Payment gateway integration should work")
    public void shouldIntegrateWithPaymentGateway() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Payment Gateway Integration");
        steps.shouldSeeIssueWithTitle("Payment Gateway Integration");
    }

    @ParameterizedTest(name = "Test payment method integration: {0}")
    @ValueSource(strings = {"Stripe", "PayPal", "Square", "Adyen", "Braintree", "Authorize.net", "Worldpay", "2Checkout"})
    @TM4J("INT-T2")
    @Microservice("Payment Gateway")
    @Story("Multiple payment gateways")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-2")})
    public void shouldIntegrateWithPaymentMethod(String paymentMethod) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Payment Method Integration: " + paymentMethod);
        steps.shouldSeeIssueWithTitle("Payment Method Integration: " + paymentMethod);
    }

    @ParameterizedTest(name = "Test shipping provider integration: {0}")
    @ValueSource(strings = {"FedEx", "UPS", "DHL", "USPS", "Canada Post", "Royal Mail", "Australia Post", "Deutsche Post"})
    @TM4J("INT-T3")
    @Microservice("Shipping")
    @Story("Shipping provider integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-3")})
    public void shouldIntegrateWithShippingProvider(String shippingProvider) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Shipping Provider Integration: " + shippingProvider);
        steps.shouldSeeIssueWithTitle("Shipping Provider Integration: " + shippingProvider);
    }

    @Test
    @TM4J("INT-T4")
    @Microservice("Email Service")
    @Story("Email service integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-4")})
    @DisplayName("Email service integration should work")
    public void shouldIntegrateWithEmailService() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Email Service Integration");
        steps.shouldSeeIssueWithTitle("Email Service Integration");
    }

    @ParameterizedTest(name = "Test social media integration: {0}")
    @ValueSource(strings = {"Facebook", "Twitter", "Instagram", "LinkedIn", "YouTube", "TikTok", "Pinterest", "Snapchat"})
    @TM4J("INT-T5")
    @Microservice("Social Media")
    @Story("Social media integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-5")})
    public void shouldIntegrateWithSocialMedia(String platform) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Social Media Integration: " + platform);
        steps.shouldSeeIssueWithTitle("Social Media Integration: " + platform);
    }

    @ParameterizedTest(name = "Test analytics integration: {0}")
    @ValueSource(strings = {"Google Analytics", "Adobe Analytics", "Mixpanel", "Amplitude", "Hotjar", "Crazy Egg", "Optimizely", "VWO"})
    @TM4J("INT-T6")
    @Microservice("Analytics")
    @Story("Analytics integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-6")})
    public void shouldIntegrateWithAnalytics(String analyticsTool) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Analytics Integration: " + analyticsTool);
        steps.shouldSeeIssueWithTitle("Analytics Integration: " + analyticsTool);
    }

    @Test
    @TM4J("INT-T7")
    @Microservice("CRM")
    @Story("CRM integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-7")})
    @DisplayName("CRM integration should work")
    public void shouldIntegrateWithCRM() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("CRM Integration");
        steps.shouldSeeIssueWithTitle("CRM Integration");
    }

    @ParameterizedTest(name = "Test inventory system integration: {0}")
    @ValueSource(strings = {"SAP", "Oracle", "Microsoft Dynamics", "NetSuite", "QuickBooks", "Xero", "FreshBooks", "Wave"})
    @TM4J("INT-T8")
    @Microservice("Inventory")
    @Story("Inventory system integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-8")})
    public void shouldIntegrateWithInventorySystem(String inventorySystem) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Inventory System Integration: " + inventorySystem);
        steps.shouldSeeIssueWithTitle("Inventory System Integration: " + inventorySystem);
    }

    @Test
    @TM4J("INT-T9")
    @Microservice("Webhook")
    @Story("Webhook integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-9")})
    @DisplayName("Webhook integration should work")
    public void shouldIntegrateWithWebhooks() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Webhook Integration");
        steps.shouldSeeIssueWithTitle("Webhook Integration");
    }

    @ParameterizedTest(name = "Test third-party API integration: {0}")
    @ValueSource(strings = {"Weather API", "Currency API", "Maps API", "Translation API", "News API", "Stock API", "Sports API", "Music API"})
    @TM4J("INT-T10")
    @Microservice("Third Party APIs")
    @Story("Third-party API integration")
    @Tags({@Tag("web"), @Tag("integration"), @Tag("regression")})
    @JiraIssues({@JiraIssue("INT-10")})
    public void shouldIntegrateWithThirdPartyAPI(String apiType) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Third Party API Integration: " + apiType);
        steps.shouldSeeIssueWithTitle("Third Party API Integration: " + apiType);
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
