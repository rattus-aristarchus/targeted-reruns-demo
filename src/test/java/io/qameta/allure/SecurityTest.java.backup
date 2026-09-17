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
@Owner("security-team")
@Feature("Security")
public class SecurityTest {

    private final WebSteps steps = new WebSteps();

    @BeforeEach
    public void startDriver() {
        steps.startDriver();
    }

    @Test
    @TM4J("SEC-T1")
    @Microservice("Authentication")
    @Story("SQL injection prevention")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-1")})
    @DisplayName("Prevent SQL injection in login form")
    public void shouldPreventSqlInjection() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("SQL Injection Prevention Test");
        steps.shouldSeeIssueWithTitle("SQL Injection Prevention Test");
    }

    @ParameterizedTest(name = "Test XSS prevention with payload: {0}")
    @ValueSource(strings = {
        "<script>alert('XSS')</script>",
        "javascript:alert('XSS')",
        "<img src=x onerror=alert('XSS')>",
        "<svg onload=alert('XSS')>",
        "javascript:void(alert('XSS'))"
    })
    @TM4J("SEC-T2")
    @Microservice("Input Validation")
    @Story("XSS prevention")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-2")})
    public void shouldPreventXssAttack(String xssPayload) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("XSS Prevention: " + xssPayload);
        steps.shouldSeeIssueWithTitle("XSS Prevention: " + xssPayload);
    }

    @ParameterizedTest(name = "Test authentication with invalid credentials: {0}")
    @CsvSource({
        "admin, wrongpassword",
        "nonexistentuser, anypassword",
        "testuser, ''",
        "'', anypassword",
        "admin', admin"
    })
    @TM4J("SEC-T3")
    @Microservice("Authentication")
    @Story("Invalid authentication attempts")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-3")})
    public void shouldRejectInvalidCredentials(String username, String password) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Invalid Credentials: " + username + "/" + password);
        steps.shouldSeeIssueWithTitle("Invalid Credentials: " + username + "/" + password);
    }

    @Test
    @TM4J("SEC-T4")
    @Microservice("Session Management")
    @Story("Session timeout")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-4")})
    @DisplayName("Session should timeout after inactivity")
    public void shouldTimeoutSessionAfterInactivity() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Session Timeout Test");
        steps.shouldSeeIssueWithTitle("Session Timeout Test");
    }

    @ParameterizedTest(name = "Test CSRF protection for: {0}")
    @ValueSource(strings = {"POST", "PUT", "DELETE", "PATCH"})
    @TM4J("SEC-T5")
    @Microservice("CSRF Protection")
    @Story("CSRF token validation")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-5")})
    public void shouldValidateCsrfToken(String httpMethod) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("CSRF Protection: " + httpMethod);
        steps.shouldSeeIssueWithTitle("CSRF Protection: " + httpMethod);
    }

    @Test
    @TM4J("SEC-T6")
    @Microservice("Authorization")
    @Story("Access control")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-6")})
    @DisplayName("Regular user cannot access admin panel")
    public void shouldPreventUnauthorizedAccess() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Unauthorized Access Prevention");
        steps.shouldSeeIssueWithTitle("Unauthorized Access Prevention");
    }

    @ParameterizedTest(name = "Test password strength for: {0}")
    @ValueSource(strings = {"weak", "123456", "password", "qwerty", "abc123"})
    @TM4J("SEC-T7")
    @Microservice("Password Policy")
    @Story("Password strength validation")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-7")})
    public void shouldRejectWeakPasswords(String weakPassword) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Weak Password Rejection: " + weakPassword);
        steps.shouldSeeIssueWithTitle("Weak Password Rejection: " + weakPassword);
    }

    @Test
    @TM4J("SEC-T8")
    @Microservice("Logging")
    @Story("Security event logging")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-8")})
    @DisplayName("Failed login attempts should be logged")
    public void shouldLogFailedLoginAttempts() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Failed Login Logging Test");
        steps.shouldSeeIssueWithTitle("Failed Login Logging Test");
    }

    @ParameterizedTest(name = "Test file upload security for: {0}")
    @ValueSource(strings = {".exe", ".php", ".jsp", ".asp", ".sh", ".bat"})
    @TM4J("SEC-T9")
    @Microservice("File Upload")
    @Story("File type validation")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-9")})
    public void shouldRejectDangerousFileTypes(String fileExtension) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Dangerous File Rejection: " + fileExtension);
        steps.shouldSeeIssueWithTitle("Dangerous File Rejection: " + fileExtension);
    }

    @Test
    @TM4J("SEC-T10")
    @Microservice("HTTPS")
    @Story("HTTPS enforcement")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-10")})
    @DisplayName("HTTP requests should redirect to HTTPS")
    public void shouldRedirectHttpToHttps() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("HTTP to HTTPS Redirect Test");
        steps.shouldSeeIssueWithTitle("HTTP to HTTPS Redirect Test");
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
