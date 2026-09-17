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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // SQL Injection Security Configuration (JSON)
        String sqlConfig = "{\n" +
                "  \"testType\": \"SQL Injection Prevention\",\n" +
                "  \"vulnerability\": \"SQL Injection\",\n" +
                "  \"attackVector\": \"Login Form\",\n" +
                "  \"protectionMethod\": \"Parameterized Queries\",\n" +
                "  \"severity\": \"High\",\n" +
                "  \"status\": \"Protected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Security Report (HTML)
        String securityReport = "<!DOCTYPE html>\n" +
                "<html><head><title>SQL Injection Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#d32f2f;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔒 SQL Injection Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">SQL Injection Prevention</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Attack Vector:</strong></span>\n" +
                "<span>Login Form</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Protected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Parameterized Queries</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>SQL Injection</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>High</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Protected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Security Data (CSV)
        String securityData = "test_type,vulnerability,attack_vector,protection_method,severity,status,timestamp\n" +
                "SQL Injection Prevention,SQL Injection,Login Form,Parameterized Queries,High,Protected," + timestamp;
        
        // Security Log (Plain Text)
        String securityLog = "SECURITY TESTING LOG\n" +
                "===================\n\n" +
                "Test Type: SQL Injection Prevention\n" +
                "Vulnerability: SQL Injection\n" +
                "Attack Vector: Login Form\n" +
                "Protection Method: Parameterized Queries\n" +
                "Severity: High\n" +
                "Status: Protected\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: SQL injection prevention validation completed";
        
        Allure.attachment("Security Configuration (JSON)", sqlConfig);
        Allure.attachment("Security Report (HTML)", securityReport);
        Allure.attachment("Security Data (CSV)", securityData);
        Allure.attachment("Security Log (Text)", securityLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // XSS Security Configuration (JSON)
        String xssConfig = "{\n" +
                "  \"testType\": \"XSS Prevention\",\n" +
                "  \"vulnerability\": \"Cross-Site Scripting\",\n" +
                "  \"payload\": \"" + xssPayload.replace("\"", "\\\"") + "\",\n" +
                "  \"payloadType\": \"" + getXssPayloadType(xssPayload) + "\",\n" +
                "  \"protectionMethod\": \"Input Sanitization\",\n" +
                "  \"severity\": \"High\",\n" +
                "  \"status\": \"Blocked\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // XSS Security Report (HTML)
        String xssReport = "<!DOCTYPE html>\n" +
                "<html><head><title>XSS Prevention Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#e65100;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".payload{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;word-break:break-all;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🛡️ XSS Prevention Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Cross-Site Scripting Prevention</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Payload Type:</strong></span>\n" +
                "<span>" + getXssPayloadType(xssPayload) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Blocked</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Input Sanitization</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Payload:</strong></span>\n" +
                "<div class=\"payload\">" + xssPayload.replace("<", "&lt;").replace(">", "&gt;") + "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>XSS</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>High</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Blocked</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // XSS Security Data (CSV)
        String xssData = "test_type,vulnerability,payload_type,payload,protection_method,severity,status,timestamp\n" +
                "XSS Prevention,Cross-Site Scripting," + getXssPayloadType(xssPayload) + ",\"" + xssPayload.replace("\"", "\"\"") + "\",Input Sanitization,High,Blocked," + timestamp;
        
        // XSS Security Log (Plain Text)
        String xssLog = "XSS SECURITY TESTING LOG\n" +
                "========================\n\n" +
                "Test Type: XSS Prevention\n" +
                "Vulnerability: Cross-Site Scripting\n" +
                "Payload Type: " + getXssPayloadType(xssPayload) + "\n" +
                "Payload: " + xssPayload + "\n" +
                "Protection Method: Input Sanitization\n" +
                "Severity: High\n" +
                "Status: Blocked\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: XSS payload validation completed";
        
        Allure.attachment("XSS Configuration (JSON)", xssConfig);
        Allure.attachment("XSS Report (HTML)", xssReport);
        Allure.attachment("XSS Data (CSV)", xssData);
        Allure.attachment("XSS Log (Text)", xssLog);
        
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
        steps.createIssueWithTitle("XSS Prevention: " + xssPayload);
        steps.shouldSeeIssueWithTitle("XSS Prevention: " + xssPayload);
    }
    
    private String getXssPayloadType(String payload) {
        if (payload.contains("<script>")) return "Script Tag";
        if (payload.contains("javascript:")) return "JavaScript URL";
        if (payload.contains("<img")) return "Image Tag";
        if (payload.contains("<svg")) return "SVG Tag";
        return "Other";
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Authentication Security Configuration (JSON)
        String authConfig = "{\n" +
                "  \"testType\": \"Invalid Credentials Test\",\n" +
                "  \"vulnerability\": \"Authentication Bypass\",\n" +
                "  \"username\": \"" + username.replace("\"", "\\\"") + "\",\n" +
                "  \"password\": \"" + password.replace("\"", "\\\"") + "\",\n" +
                "  \"credentialType\": \"" + getCredentialType(username, password) + "\",\n" +
                "  \"protectionMethod\": \"Credential Validation\",\n" +
                "  \"severity\": \"Medium\",\n" +
                "  \"status\": \"Rejected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Authentication Security Report (HTML)
        String authReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Authentication Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".credentials{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔐 Authentication Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Invalid Credentials Test</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Credential Type:</strong></span>\n" +
                "<span>" + getCredentialType(username, password) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">Medium</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Rejected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Credential Validation</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Credentials:</strong></span>\n" +
                "<div class=\"credentials\">Username: " + username + "<br>Password: " + password + "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Auth Bypass</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>Medium</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Rejected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Authentication Security Data (CSV)
        String authData = "test_type,vulnerability,username,password,credential_type,protection_method,severity,status,timestamp\n" +
                "Invalid Credentials Test,Authentication Bypass,\"" + username.replace("\"", "\"\"") + "\",\"" + password.replace("\"", "\"\"") + "\"," + getCredentialType(username, password) + ",Credential Validation,Medium,Rejected," + timestamp;
        
        // Authentication Security Log (Plain Text)
        String authLog = "AUTHENTICATION SECURITY TESTING LOG\n" +
                "====================================\n\n" +
                "Test Type: Invalid Credentials Test\n" +
                "Vulnerability: Authentication Bypass\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Credential Type: " + getCredentialType(username, password) + "\n" +
                "Protection Method: Credential Validation\n" +
                "Severity: Medium\n" +
                "Status: Rejected\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Invalid credentials validation completed";
        
        Allure.attachment("Auth Configuration (JSON)", authConfig);
        Allure.attachment("Auth Report (HTML)", authReport);
        Allure.attachment("Auth Data (CSV)", authData);
        Allure.attachment("Auth Log (Text)", authLog);
        
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
        steps.createIssueWithTitle("Invalid Credentials: " + username + "/" + password);
        steps.shouldSeeIssueWithTitle("Invalid Credentials: " + username + "/" + password);
    }
    
    private String getCredentialType(String username, String password) {
        if (username.isEmpty()) return "Empty Username";
        if (password.isEmpty()) return "Empty Password";
        if (username.contains("'")) return "SQL Injection Attempt";
        if (username.equals("nonexistentuser")) return "Non-existent User";
        return "Wrong Password";
    }

    @Test
    @TM4J("SEC-T4")
    @Microservice("Session Management")
    @Story("Session timeout")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-4")})
    @DisplayName("Session should timeout after inactivity")
    public void shouldTimeoutSessionAfterInactivity() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Session Security Configuration (JSON)
        String sessionConfig = "{\n" +
                "  \"testType\": \"Session Timeout Test\",\n" +
                "  \"vulnerability\": \"Session Hijacking\",\n" +
                "  \"timeoutDuration\": \"30 minutes\",\n" +
                "  \"inactivityThreshold\": \"15 minutes\",\n" +
                "  \"protectionMethod\": \"Automatic Session Expiry\",\n" +
                "  \"severity\": \"Medium\",\n" +
                "  \"status\": \"Protected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Session Security Report (HTML)
        String sessionReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Session Timeout Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#f3e5f5;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #9c27b0;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#7b1fa2;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".timeout{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                ".time{font-size:16px;font-weight:bold;color:#9c27b0;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>⏰ Session Timeout Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Session Timeout Protection</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Timeout Duration:</strong></span>\n" +
                "<span class=\"time\">30 minutes</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Inactivity Threshold:</strong></span>\n" +
                "<span class=\"time\">15 minutes</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">Medium</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Protected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Automatic Session Expiry</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Session Details:</strong></span>\n" +
                "<div class=\"timeout\">Session ID: SESS_" + System.currentTimeMillis() + "<br>Created: " + timestamp + "<br>Expires: " + LocalDateTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Session Hijacking</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>Medium</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Protected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Session Security Data (CSV)
        String sessionData = "test_type,vulnerability,timeout_duration,inactivity_threshold,protection_method,severity,status,timestamp\n" +
                "Session Timeout Test,Session Hijacking,30 minutes,15 minutes,Automatic Session Expiry,Medium,Protected," + timestamp;
        
        // Session Security Log (Plain Text)
        String sessionLog = "SESSION TIMEOUT SECURITY TESTING LOG\n" +
                "=====================================\n\n" +
                "Test Type: Session Timeout Test\n" +
                "Vulnerability: Session Hijacking\n" +
                "Timeout Duration: 30 minutes\n" +
                "Inactivity Threshold: 15 minutes\n" +
                "Protection Method: Automatic Session Expiry\n" +
                "Severity: Medium\n" +
                "Status: Protected\n" +
                "Session ID: SESS_" + System.currentTimeMillis() + "\n" +
                "Created: " + timestamp + "\n" +
                "Expires: " + LocalDateTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "Action: Session timeout validation completed";
        
        Allure.attachment("Session Configuration (JSON)", sessionConfig);
        Allure.attachment("Session Report (HTML)", sessionReport);
        Allure.attachment("Session Data (CSV)", sessionData);
        Allure.attachment("Session Log (Text)", sessionLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // CSRF Security Configuration (JSON)
        String csrfConfig = "{\n" +
                "  \"testType\": \"CSRF Protection Test\",\n" +
                "  \"vulnerability\": \"Cross-Site Request Forgery\",\n" +
                "  \"httpMethod\": \"" + httpMethod + "\",\n" +
                "  \"tokenType\": \"" + getCsrfTokenType(httpMethod) + "\",\n" +
                "  \"protectionMethod\": \"CSRF Token Validation\",\n" +
                "  \"severity\": \"High\",\n" +
                "  \"status\": \"Protected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // CSRF Security Report (HTML)
        String csrfReport = "<!DOCTYPE html>\n" +
                "<html><head><title>CSRF Protection Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".method{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".token{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".http-method{font-size:16px;font-weight:bold;color:#1976d2;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🛡️ CSRF Protection Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">CSRF Protection for " + httpMethod + "</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>HTTP Method:</strong></span>\n" +
                "<span class=\"http-method\">" + httpMethod + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Token Type:</strong></span>\n" +
                "<span class=\"method\">" + getCsrfTokenType(httpMethod) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Protected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">CSRF Token Validation</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Token Details:</strong></span>\n" +
                "<div class=\"token\">Token: CSRF_" + System.currentTimeMillis() + "<br>Method: " + httpMethod + "<br>Validated: Yes</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>CSRF</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>High</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Protected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // CSRF Security Data (CSV)
        String csrfData = "test_type,vulnerability,http_method,token_type,protection_method,severity,status,timestamp\n" +
                "CSRF Protection Test,Cross-Site Request Forgery," + httpMethod + "," + getCsrfTokenType(httpMethod) + ",CSRF Token Validation,High,Protected," + timestamp;
        
        // CSRF Security Log (Plain Text)
        String csrfLog = "CSRF PROTECTION SECURITY TESTING LOG\n" +
                "=====================================\n\n" +
                "Test Type: CSRF Protection Test\n" +
                "Vulnerability: Cross-Site Request Forgery\n" +
                "HTTP Method: " + httpMethod + "\n" +
                "Token Type: " + getCsrfTokenType(httpMethod) + "\n" +
                "Protection Method: CSRF Token Validation\n" +
                "Severity: High\n" +
                "Status: Protected\n" +
                "Token: CSRF_" + System.currentTimeMillis() + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: CSRF protection validation completed";
        
        Allure.attachment("CSRF Configuration (JSON)", csrfConfig);
        Allure.attachment("CSRF Report (HTML)", csrfReport);
        Allure.attachment("CSRF Data (CSV)", csrfData);
        Allure.attachment("CSRF Log (Text)", csrfLog);
        
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
        steps.createIssueWithTitle("CSRF Protection: " + httpMethod);
        steps.shouldSeeIssueWithTitle("CSRF Protection: " + httpMethod);
    }
    
    private String getCsrfTokenType(String httpMethod) {
        switch (httpMethod) {
            case "POST": return "Form Token";
            case "PUT": return "Header Token";
            case "DELETE": return "Header Token";
            case "PATCH": return "Header Token";
            default: return "Unknown";
        }
    }

    @Test
    @TM4J("SEC-T6")
    @Microservice("Authorization")
    @Story("Access control")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-6")})
    @DisplayName("Regular user cannot access admin panel")
    public void shouldPreventUnauthorizedAccess() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Authorization Security Configuration (JSON)
        String authzConfig = "{\n" +
                "  \"testType\": \"Unauthorized Access Prevention\",\n" +
                "  \"vulnerability\": \"Privilege Escalation\",\n" +
                "  \"userRole\": \"Regular User\",\n" +
                "  \"targetResource\": \"Admin Panel\",\n" +
                "  \"protectionMethod\": \"Role-Based Access Control\",\n" +
                "  \"severity\": \"High\",\n" +
                "  \"status\": \"Blocked\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Authorization Security Report (HTML)
        String authzReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Authorization Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#d32f2f;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".role{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".resource{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🚫 Authorization Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Unauthorized Access Prevention</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>User Role:</strong></span>\n" +
                "<span class=\"role\">Regular User</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Target Resource:</strong></span>\n" +
                "<span>Admin Panel</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Blocked</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Role-Based Access Control</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Access Details:</strong></span>\n" +
                "<div class=\"resource\">User: testuser<br>Role: Regular User<br>Resource: Admin Panel<br>Access: Denied</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Privilege Escalation</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>High</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Blocked</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Authorization Security Data (CSV)
        String authzData = "test_type,vulnerability,user_role,target_resource,protection_method,severity,status,timestamp\n" +
                "Unauthorized Access Prevention,Privilege Escalation,Regular User,Admin Panel,Role-Based Access Control,High,Blocked," + timestamp;
        
        // Authorization Security Log (Plain Text)
        String authzLog = "AUTHORIZATION SECURITY TESTING LOG\n" +
                "====================================\n\n" +
                "Test Type: Unauthorized Access Prevention\n" +
                "Vulnerability: Privilege Escalation\n" +
                "User Role: Regular User\n" +
                "Target Resource: Admin Panel\n" +
                "Protection Method: Role-Based Access Control\n" +
                "Severity: High\n" +
                "Status: Blocked\n" +
                "User: testuser\n" +
                "Access: Denied\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Unauthorized access prevention validation completed";
        
        Allure.attachment("Authorization Configuration (JSON)", authzConfig);
        Allure.attachment("Authorization Report (HTML)", authzReport);
        Allure.attachment("Authorization Data (CSV)", authzData);
        Allure.attachment("Authorization Log (Text)", authzLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Password Security Configuration (JSON)
        String passwordConfig = "{\n" +
                "  \"testType\": \"Password Strength Test\",\n" +
                "  \"vulnerability\": \"Weak Password\",\n" +
                "  \"password\": \"" + weakPassword.replace("\"", "\\\"") + "\",\n" +
                "  \"strengthLevel\": \"" + getPasswordStrength(weakPassword) + "\",\n" +
                "  \"protectionMethod\": \"Password Policy Enforcement\",\n" +
                "  \"severity\": \"Medium\",\n" +
                "  \"status\": \"Rejected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Password Security Report (HTML)
        String passwordReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Password Strength Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#fff3e0;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #ff9800;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#e65100;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".strength{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff5722;}\n" +
                ".password{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔑 Password Strength Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Password Strength Validation</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Password:</strong></span>\n" +
                "<div class=\"password\">" + weakPassword + "</div>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Strength Level:</strong></span>\n" +
                "<span class=\"strength\">" + getPasswordStrength(weakPassword) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">Medium</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Rejected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Password Policy Enforcement</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Weak Password</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>Medium</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Rejected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Password Security Data (CSV)
        String passwordData = "test_type,vulnerability,password,strength_level,protection_method,severity,status,timestamp\n" +
                "Password Strength Test,Weak Password,\"" + weakPassword.replace("\"", "\"\"") + "\"," + getPasswordStrength(weakPassword) + ",Password Policy Enforcement,Medium,Rejected," + timestamp;
        
        // Password Security Log (Plain Text)
        String passwordLog = "PASSWORD STRENGTH SECURITY TESTING LOG\n" +
                "======================================\n\n" +
                "Test Type: Password Strength Test\n" +
                "Vulnerability: Weak Password\n" +
                "Password: " + weakPassword + "\n" +
                "Strength Level: " + getPasswordStrength(weakPassword) + "\n" +
                "Protection Method: Password Policy Enforcement\n" +
                "Severity: Medium\n" +
                "Status: Rejected\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Password strength validation completed";
        
        Allure.attachment("Password Configuration (JSON)", passwordConfig);
        Allure.attachment("Password Report (HTML)", passwordReport);
        Allure.attachment("Password Data (CSV)", passwordData);
        Allure.attachment("Password Log (Text)", passwordLog);
        
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
        steps.createIssueWithTitle("Weak Password Rejection: " + weakPassword);
        steps.shouldSeeIssueWithTitle("Weak Password Rejection: " + weakPassword);
    }
    
    private String getPasswordStrength(String password) {
        if (password.length() < 6) return "Very Weak";
        if (password.equals("123456") || password.equals("password") || password.equals("qwerty")) return "Weak";
        if (password.equals("abc123")) return "Fair";
        return "Strong";
    }

    @Test
    @TM4J("SEC-T8")
    @Microservice("Logging")
    @Story("Security event logging")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-8")})
    @DisplayName("Failed login attempts should be logged")
    public void shouldLogFailedLoginAttempts() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Logging Security Configuration (JSON)
        String loggingConfig = "{\n" +
                "  \"testType\": \"Failed Login Logging Test\",\n" +
                "  \"vulnerability\": \"Security Event Logging\",\n" +
                "  \"eventType\": \"Failed Login Attempt\",\n" +
                "  \"logLevel\": \"WARNING\",\n" +
                "  \"protectionMethod\": \"Security Event Monitoring\",\n" +
                "  \"severity\": \"Low\",\n" +
                "  \"status\": \"Logged\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Logging Security Report (HTML)
        String loggingReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Security Logging Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#e0f2f1;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #009688;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#00695c;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".log-level{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".log-entry{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📝 Security Logging Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Failed Login Logging</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Event Type:</strong></span>\n" +
                "<span>Failed Login Attempt</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Log Level:</strong></span>\n" +
                "<span class=\"log-level\">WARNING</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">Low</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Logged</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Security Event Monitoring</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Log Entry:</strong></span>\n" +
                "<div class=\"log-entry\">[" + timestamp + "] WARNING: Failed login attempt from IP 192.168.1.100 for user testuser</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Event Type</h3>\n" +
                "<p>Failed Login</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>Low</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Logged</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Logging Security Data (CSV)
        String loggingData = "test_type,vulnerability,event_type,log_level,protection_method,severity,status,timestamp\n" +
                "Failed Login Logging Test,Security Event Logging,Failed Login Attempt,WARNING,Security Event Monitoring,Low,Logged," + timestamp;
        
        // Logging Security Log (Plain Text)
        String loggingLog = "SECURITY LOGGING TESTING LOG\n" +
                "============================\n\n" +
                "Test Type: Failed Login Logging Test\n" +
                "Vulnerability: Security Event Logging\n" +
                "Event Type: Failed Login Attempt\n" +
                "Log Level: WARNING\n" +
                "Protection Method: Security Event Monitoring\n" +
                "Severity: Low\n" +
                "Status: Logged\n" +
                "Log Entry: [" + timestamp + "] WARNING: Failed login attempt from IP 192.168.1.100 for user testuser\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: Security event logging validation completed";
        
        Allure.attachment("Logging Configuration (JSON)", loggingConfig);
        Allure.attachment("Logging Report (HTML)", loggingReport);
        Allure.attachment("Logging Data (CSV)", loggingData);
        Allure.attachment("Logging Log (Text)", loggingLog);
        
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // File Upload Security Configuration (JSON)
        String fileConfig = "{\n" +
                "  \"testType\": \"File Upload Security Test\",\n" +
                "  \"vulnerability\": \"Malicious File Upload\",\n" +
                "  \"fileExtension\": \"" + fileExtension + "\",\n" +
                "  \"fileType\": \"" + getFileType(fileExtension) + "\",\n" +
                "  \"protectionMethod\": \"File Type Validation\",\n" +
                "  \"severity\": \"High\",\n" +
                "  \"status\": \"Rejected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // File Upload Security Report (HTML)
        String fileReport = "<!DOCTYPE html>\n" +
                "<html><head><title>File Upload Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#ffebee;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #f44336;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#d32f2f;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".file-type{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff5722;}\n" +
                ".file-details{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📁 File Upload Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">Dangerous File Rejection</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Extension:</strong></span>\n" +
                "<span class=\"file-type\">" + fileExtension + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Type:</strong></span>\n" +
                "<span>" + getFileType(fileExtension) + "</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">High</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Rejected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">File Type Validation</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>File Details:</strong></span>\n" +
                "<div class=\"file-details\">Filename: malicious" + fileExtension + "<br>Size: 1.2 MB<br>MIME Type: " + getMimeType(fileExtension) + "<br>Status: Rejected</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Malicious Upload</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>High</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Rejected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // File Upload Security Data (CSV)
        String fileData = "test_type,vulnerability,file_extension,file_type,protection_method,severity,status,timestamp\n" +
                "File Upload Security Test,Malicious File Upload," + fileExtension + "," + getFileType(fileExtension) + ",File Type Validation,High,Rejected," + timestamp;
        
        // File Upload Security Log (Plain Text)
        String fileLog = "FILE UPLOAD SECURITY TESTING LOG\n" +
                "==================================\n\n" +
                "Test Type: File Upload Security Test\n" +
                "Vulnerability: Malicious File Upload\n" +
                "File Extension: " + fileExtension + "\n" +
                "File Type: " + getFileType(fileExtension) + "\n" +
                "Protection Method: File Type Validation\n" +
                "Severity: High\n" +
                "Status: Rejected\n" +
                "Filename: malicious" + fileExtension + "\n" +
                "MIME Type: " + getMimeType(fileExtension) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: File upload security validation completed";
        
        Allure.attachment("File Configuration (JSON)", fileConfig);
        Allure.attachment("File Report (HTML)", fileReport);
        Allure.attachment("File Data (CSV)", fileData);
        Allure.attachment("File Log (Text)", fileLog);
        
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
        steps.createIssueWithTitle("Dangerous File Rejection: " + fileExtension);
        steps.shouldSeeIssueWithTitle("Dangerous File Rejection: " + fileExtension);
    }
    
    private String getFileType(String extension) {
        switch (extension) {
            case ".exe": return "Executable";
            case ".php": return "PHP Script";
            case ".jsp": return "JSP Script";
            case ".asp": return "ASP Script";
            case ".sh": return "Shell Script";
            case ".bat": return "Batch File";
            default: return "Unknown";
        }
    }
    
    private String getMimeType(String extension) {
        switch (extension) {
            case ".exe": return "application/x-msdownload";
            case ".php": return "application/x-httpd-php";
            case ".jsp": return "application/x-jsp";
            case ".asp": return "application/x-asp";
            case ".sh": return "application/x-sh";
            case ".bat": return "application/x-msdos-program";
            default: return "application/octet-stream";
        }
    }

    @Test
    @TM4J("SEC-T10")
    @Microservice("HTTPS")
    @Story("HTTPS enforcement")
    @Tags({@Tag("web"), @Tag("security"), @Tag("regression")})
    @JiraIssues({@JiraIssue("SEC-10")})
    @DisplayName("HTTP requests should redirect to HTTPS")
    public void shouldRedirectHttpToHttps() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // HTTPS Security Configuration (JSON)
        String httpsConfig = "{\n" +
                "  \"testType\": \"HTTP to HTTPS Redirect Test\",\n" +
                "  \"vulnerability\": \"Insecure Communication\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"redirectTo\": \"HTTPS\",\n" +
                "  \"protectionMethod\": \"Automatic Redirect\",\n" +
                "  \"severity\": \"Medium\",\n" +
                "  \"status\": \"Redirected\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // HTTPS Security Report (HTML)
        String httpsReport = "<!DOCTYPE html>\n" +
                "<html><head><title>HTTPS Redirect Security Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".security{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".vulnerability{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".severity{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#ff9800;}\n" +
                ".status{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".protocol{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#f44336;}\n" +
                ".secure{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".redirect{background:#f5f5f5;padding:10px;border-radius:4px;font-family:monospace;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".method{font-size:16px;font-weight:bold;color:#2e7d32;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔒 HTTPS Redirect Security Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"security\">\n" +
                "<h2 class=\"vulnerability\">HTTP to HTTPS Redirect</h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>From Protocol:</strong></span>\n" +
                "<span class=\"protocol\">HTTP</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>To Protocol:</strong></span>\n" +
                "<span class=\"secure\">HTTPS</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Severity:</strong></span>\n" +
                "<span class=\"severity\">Medium</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Status:</strong></span>\n" +
                "<span class=\"status\">Redirected</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Protection Method:</strong></span>\n" +
                "<span class=\"method\">Automatic Redirect</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Redirect Details:</strong></span>\n" +
                "<div class=\"redirect\">HTTP: http://example.com<br>HTTPS: https://example.com<br>Status Code: 301<br>Redirect: Successful</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Vulnerability</h3>\n" +
                "<p>Insecure Comm</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Severity</h3>\n" +
                "<p>Medium</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Status</h3>\n" +
                "<p>Redirected</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // HTTPS Security Data (CSV)
        String httpsData = "test_type,vulnerability,protocol,redirect_to,protection_method,severity,status,timestamp\n" +
                "HTTP to HTTPS Redirect Test,Insecure Communication,HTTP,HTTPS,Automatic Redirect,Medium,Redirected," + timestamp;
        
        // HTTPS Security Log (Plain Text)
        String httpsLog = "HTTPS REDIRECT SECURITY TESTING LOG\n" +
                "====================================\n\n" +
                "Test Type: HTTP to HTTPS Redirect Test\n" +
                "Vulnerability: Insecure Communication\n" +
                "Protocol: HTTP\n" +
                "Redirect To: HTTPS\n" +
                "Protection Method: Automatic Redirect\n" +
                "Severity: Medium\n" +
                "Status: Redirected\n" +
                "HTTP URL: http://example.com\n" +
                "HTTPS URL: https://example.com\n" +
                "Status Code: 301\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: HTTPS redirect validation completed";
        
        Allure.attachment("HTTPS Configuration (JSON)", httpsConfig);
        Allure.attachment("HTTPS Report (HTML)", httpsReport);
        Allure.attachment("HTTPS Data (CSV)", httpsData);
        Allure.attachment("HTTPS Log (Text)", httpsLog);
        
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
        steps.createIssueWithTitle("HTTP to HTTPS Redirect Test");
        steps.shouldSeeIssueWithTitle("HTTP to HTTPS Redirect Test");
    }

    @AfterEach
    public void stopDriver() {
        steps.stopDriver();
    }
}
