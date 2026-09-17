package io.qameta.allure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.qameta.allure.Allure.step;

@org.junit.jupiter.api.DisplayName("TTempTests.class unit tests")
    public class MyExamples {


    /**
     * Uses a basic '@Description' annotation and a javaDoc method that allows you to pass text commented out over the test
     * Pros: Very handy if you're used to keeping documentation in code.
     * Cons: The description is not strongly tied to the test, there is a chance to make a mistake.
     */

    @Test
    @AllureId("9251")
    @DisplayName("Some test")
        @Description(useJavaDoc = true)
        public void test1667306661111() {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // Test Configuration (JSON)
            String testConfig = "{\n" +
                    "  \"testId\": \"9251\",\n" +
                    "  \"testName\": \"Some test\",\n" +
                    "  \"testType\": \"Temporary Redirect Test\",\n" +
                    "  \"serviceId\": \"temporaryRedirectWith www\",\n" +
                    "  \"sleepDuration\": 60,\n" +
                    "  \"timestamp\": \"" + timestamp + "\",\n" +
                    "  \"testing\": true\n" +
                    "}";
            
            // Test Report (HTML)
            String testReport = "<!DOCTYPE html>\n" +
                    "<html><head><title>Temporary Redirect Test Report</title>\n" +
                    "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                    ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                    ".test{background:#e3f2fd;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #2196f3;}\n" +
                    ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                    ".feature:last-child{border-bottom:none;}\n" +
                    ".test-name{font-size:20px;font-weight:bold;color:#1976d2;}\n" +
                    ".test-id{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                    ".duration{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                    ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                    ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                    ".time{font-size:16px;font-weight:bold;color:#2196f3;}\n" +
                    "</style></head>\n" +
                    "<body><div class=\"container\">\n" +
                    "<h1>🔄 Temporary Redirect Test Report</h1>\n" +
                    "<p>Generated: " + timestamp + "</p>\n" +
                    "<div class=\"test\">\n" +
                    "<h2 class=\"test-name\">Some test <span class=\"test-id\">ID: 9251</span></h2>\n" +
                    "<div class=\"feature\">\n" +
                    "<span><strong>Test Type:</strong></span>\n" +
                    "<span>Temporary Redirect Test</span>\n" +
                    "</div>\n" +
                    "<div class=\"feature\">\n" +
                    "<span><strong>Service ID:</strong></span>\n" +
                    "<span>temporaryRedirectWith www</span>\n" +
                    "</div>\n" +
                    "<div class=\"feature\">\n" +
                    "<span><strong>Sleep Duration:</strong></span>\n" +
                    "<span class=\"duration\">60 seconds</span>\n" +
                    "</div>\n" +
                    "<div class=\"feature\">\n" +
                    "<span><strong>Status:</strong></span>\n" +
                    "<span>Running</span>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "<div class=\"stats\">\n" +
                    "<div class=\"stat\">\n" +
                    "<h3>Test ID</h3>\n" +
                    "<p>9251</p>\n" +
                    "</div>\n" +
                    "<div class=\"stat\">\n" +
                    "<h3>Duration</h3>\n" +
                    "<p>60s</p>\n" +
                    "</div>\n" +
                    "<div class=\"stat\">\n" +
                    "<h3>Status</h3>\n" +
                    "<p>Running</p>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</div></body></html>";
            
            // Test Data (CSV)
            String testData = "test_id,test_name,test_type,service_id,sleep_duration,timestamp\n" +
                    "9251,Some test,Temporary Redirect Test,temporaryRedirectWith www,60," + timestamp;
            
            // Test Log (Plain Text)
            String testLog = "TEMPORARY REDIRECT TESTING LOG\n" +
                    "==============================\n\n" +
                    "Test ID: 9251\n" +
                    "Test Name: Some test\n" +
                    "Test Type: Temporary Redirect Test\n" +
                    "Service ID: temporaryRedirectWith www\n" +
                    "Sleep Duration: 60 seconds\n" +
                    "Timestamp: " + timestamp + "\n" +
                    "Action: Temporary redirect test execution started";
            
            Allure.attachment("Test Configuration (JSON)", testConfig);
            Allure.attachment("Test Report (HTML)", testReport);
            Allure.attachment("Test Data (CSV)", testData);
            Allure.attachment("Test Log (Text)", testLog);
            
            step("some step name https://www");
            step("some step name www without https");
            step("* var serviceId1=\"temporaryRedirectWith www\"");
        step("step sleep 60", () -> {
            Thread.sleep(60000);
            step("sub step");
        });
        step(" temporaryRedirectWith www\n ");
        }

    @Test
    @DisplayName("new test")
    @Owner("daniil@qameta.io")
    @Feature("Issues")
    void testFromTestops() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Test Configuration (JSON)
        String testConfig = "{\n" +
                "  \"testName\": \"new test\",\n" +
                "  \"owner\": \"daniil@qameta.io\",\n" +
                "  \"feature\": \"Issues\",\n" +
                "  \"testType\": \"TestOps Integration Test\",\n" +
                "  \"sleepDuration\": 60,\n" +
                "  \"steps\": 4,\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"testing\": true\n" +
                "}";
        
        // Test Report (HTML)
        String testReport = "<!DOCTYPE html>\n" +
                "<html><head><title>TestOps Integration Test Report</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".test{background:#e8f5e8;padding:20px;border-radius:4px;margin:20px 0;border-left:4px solid #4caf50;}\n" +
                ".feature{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #ddd;}\n" +
                ".feature:last-child{border-bottom:none;}\n" +
                ".test-name{font-size:20px;font-weight:bold;color:#2e7d32;}\n" +
                ".owner{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#4caf50;}\n" +
                ".feature-tag{display:inline-block;padding:5px 10px;border-radius:15px;color:white;font-size:12px;background:#2196f3;}\n" +
                ".stats{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".stat{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".steps{font-size:16px;font-weight:bold;color:#4caf50;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>🔧 TestOps Integration Test Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<div class=\"test\">\n" +
                "<h2 class=\"test-name\">new test <span class=\"owner\">daniil@qameta.io</span></h2>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Feature:</strong></span>\n" +
                "<span class=\"feature-tag\">Issues</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Test Type:</strong></span>\n" +
                "<span>TestOps Integration Test</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Steps:</strong></span>\n" +
                "<span class=\"steps\">4 steps</span>\n" +
                "</div>\n" +
                "<div class=\"feature\">\n" +
                "<span><strong>Sleep Duration:</strong></span>\n" +
                "<span>60 seconds</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"stats\">\n" +
                "<div class=\"stat\">\n" +
                "<h3>Owner</h3>\n" +
                "<p>daniil@qameta.io</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Steps</h3>\n" +
                "<p>4</p>\n" +
                "</div>\n" +
                "<div class=\"stat\">\n" +
                "<h3>Duration</h3>\n" +
                "<p>60s</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div></body></html>";
        
        // Test Data (CSV)
        String testData = "test_name,owner,feature,test_type,sleep_duration,steps,timestamp\n" +
                "new test,daniil@qameta.io,Issues,TestOps Integration Test,60,4," + timestamp;
        
        // Test Log (Plain Text)
        String testLog = "TESTOPS INTEGRATION TESTING LOG\n" +
                "================================\n\n" +
                "Test Name: new test\n" +
                "Owner: daniil@qameta.io\n" +
                "Feature: Issues\n" +
                "Test Type: TestOps Integration Test\n" +
                "Steps: 4\n" +
                "Sleep Duration: 60 seconds\n" +
                "Timestamp: " + timestamp + "\n" +
                "Action: TestOps integration test execution started";
        
        Allure.attachment("TestOps Configuration (JSON)", testConfig);
        Allure.attachment("TestOps Report (HTML)", testReport);
        Allure.attachment("TestOps Data (CSV)", testData);
        Allure.attachment("TestOps Log (Text)", testLog);
        
        step("step 1");
        step("step 2");
        step("step 3", () -> {
            step("sub step");
        });
        step("step sleep 60", () -> {
            Thread.sleep(60000);
            step("sub step");
        });

    }


}
