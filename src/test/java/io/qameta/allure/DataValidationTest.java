package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.InputStream;

@ExtendWith(AllureJunit5.class)
@Layer("web")
@Owner("data-specialist")
@Feature("Data Validation")
@TM4J("TM4J-127")
@Microservice("validation-service")
@Story("Data validation functionality")
@Tag("validation") @Tag("data") @Tag("web")
public class DataValidationTest {

    private WebSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new WebSteps();
    }

    @Test
    @DisplayName("Validate user input data")
    @Story("Input validation")
    @JiraIssues({@JiraIssue("JIRA-460")})
    public void shouldValidateUserInputData() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // JSON Configuration
        String jsonConfig = "{\n" +
                "  \"validationRules\": [\n" +
                "    {\"field\": \"email\", \"type\": \"email\", \"required\": true},\n" +
                "    {\"field\": \"password\", \"type\": \"string\", \"minLength\": 8},\n" +
                "    {\"field\": \"age\", \"type\": \"integer\", \"min\": 18, \"max\": 120}\n" +
                "  ]\n" +
                "}";
        
        // HTML Report
        String htmlReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Data Validation Report</title></head>\n" +
                "<body><h1>Data Validation Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<ul><li>Email: ✅ Valid</li><li>Password: ✅ Valid</li><li>Age: ✅ Valid</li></ul>\n" +
                "</body></html>";
        
        // CSV Results
        String csvResults = "field,status,value,timestamp\n" +
                "email,valid,user@example.com," + timestamp + "\n" +
                "password,valid,********," + timestamp + "\n" +
                "age,valid,25," + timestamp;
        
        // XML Schema
        String xmlSchema = "<?xml version=\"1.0\"?>\n" +
                "<validation-schema>\n" +
                "  <field name=\"email\" type=\"email\" required=\"true\"/>\n" +
                "  <field name=\"password\" type=\"string\" minLength=\"8\"/>\n" +
                "  <field name=\"age\" type=\"integer\" min=\"18\" max=\"120\"/>\n" +
                "</validation-schema>";
        
        // Plain Text Log
        String textLog = "DATA VALIDATION LOG\n" +
                "==================\n\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: ✅ PASSED\n" +
                "Valid Fields: 3\n" +
                "Errors: 0";
        
        // JavaScript Code
        String jsCode = "function validateData(data) {\n" +
                "  const results = [];\n" +
                "  // Validation logic here\n" +
                "  return results;\n" +
                "}";
        
        Allure.attachment("Validation Rules (JSON)", jsonConfig);
        Allure.attachment("Validation Report (HTML)", htmlReport);
        Allure.attachment("Validation Results (CSV)", csvResults);
        Allure.attachment("Validation Schema (XML)", xmlSchema);
        Allure.attachment("Validation Log (Text)", textLog);
        Allure.attachment("Validation JavaScript", jsCode);
        
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
        steps.createIssueWithTitle("Data Validation Test");
        steps.shouldSeeIssueWithTitle("Data Validation Test");
    }

    @Test
    @DisplayName("Test data format validation")
    @Story("Format validation")
    public void shouldTestDataFormatValidation() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String formatResults = "Data Format Validation Results\n" +
                "Generated: " + timestamp + "\n\n" +
                "JSON Format: ✅ Valid\n" +
                "XML Format: ✅ Valid\n" +
                "CSV Format: ✅ Valid";

        Allure.attachment("Format Validation Results", formatResults);
        Allure.attachment("Validation Timestamp", timestamp);
        
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
        steps.createIssueWithTitle("Data Format Validation");
        steps.shouldSeeIssueWithTitle("Data Format Validation");
    }
}