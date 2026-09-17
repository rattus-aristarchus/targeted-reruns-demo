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
import io.qameta.allure.Allure;
import java.io.InputStream;

@ExtendWith(AllureJunit5.class)
@Layer("rest")
@Owner("database-specialist")
@Feature("Database Operations")
@TM4J("TM4J-131")
@Microservice("database-service")
@Story("Database functionality")
@Tag("database") @Tag("api") @Tag("rest")
public class DatabaseTest {

    private RestSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new RestSteps();
    }

    @Test
    @DisplayName("Test database connection")
    @Story("Database connection")
    @JiraIssues({@JiraIssue("JIRA-464")})
    public void shouldTestDatabaseConnection() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String connectionData = "Database: PostgreSQL\n" +
                "Host: localhost:5432\n" +
                "Database: testdb\n" +
                "User: testuser\n" +
                "Status: Connected\n" +
                "Response Time: 15ms";
        
        String result = "Database connection established successfully at " + timestamp + "\n" +
                "Status: Active\n" +
                "Pool Size: 10\n" +
                "Active Connections: 3";
        
        Allure.attachment("Connection Data", connectionData);
        Allure.attachment("Connection Result", result);
        
        // Add big.json file attachment
        try {
            InputStream bigJsonStream = getClass().getClassLoader().getResourceAsStream("big.json");
            if (bigJsonStream != null) {
                Allure.attachment("Big JSON Data", bigJsonStream);
            }
        } catch (Exception e) {
            // If big.json file is not found, continue with test
        }
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Database Connection: PostgreSQL");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Database Connection: PostgreSQL");
    }

    @Test
    @DisplayName("Test data insertion")
    @Story("Data insertion")
    public void shouldTestDataInsertion() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String insertData = "Table: users\n" +
                "Records: 1\n" +
                "Fields: 5\n" +
                "Size: 256 bytes\n" +
                "Index: Updated\n" +
                "Timestamp: " + timestamp;
        
        String result = "Data inserted successfully at " + timestamp + "\n" +
                "Status: Committed\n" +
                "Transaction ID: TXN-001\n" +
                "Duration: 25ms";
        
        Allure.attachment("Insert Data", insertData);
        Allure.attachment("Insert Result", result);
        
        // Add big.json file attachment
        try {
            InputStream bigJsonStream = getClass().getClassLoader().getResourceAsStream("big.json");
            if (bigJsonStream != null) {
                Allure.attachment("Big JSON Data", bigJsonStream);
            }
        } catch (Exception e) {
            // If big.json file is not found, continue with test
        }
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Data Insertion: users table");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Data Insertion: users table");
    }

    @Test
    @DisplayName("Test data query")
    @Story("Data query")
    public void shouldTestDataQuery() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String queryData = "Query: SELECT * FROM users WHERE active = true\n" +
                "Rows: 1,250\n" +
                "Execution Time: 45ms\n" +
                "Cache Hit: Yes\n" +
                "Index Used: idx_users_active\n" +
                "Timestamp: " + timestamp;
        
        String result = "Query executed successfully at " + timestamp + "\n" +
                "Status: Completed\n" +
                "Rows Returned: 1,250\n" +
                "Performance: Good";
        
        Allure.attachment("Query Data", queryData);
        Allure.attachment("Query Result", result);
        
        // Add big.json file attachment
        try {
            InputStream bigJsonStream = getClass().getClassLoader().getResourceAsStream("big.json");
            if (bigJsonStream != null) {
                Allure.attachment("Big JSON Data", bigJsonStream);
            }
        } catch (Exception e) {
            // If big.json file is not found, continue with test
        }
        
        // Add video file attachment
        try {
            InputStream videoStream = getClass().getClassLoader().getResourceAsStream("file_example.mp4");
            if (videoStream != null) {
                Allure.attachment("Test Demo Video", videoStream);
            }
        } catch (Exception e) {
            // If video file is not found, continue with test
        }
        
        steps.createIssueWithTitle("testuser", "testrepo", "Data Query: users table");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Data Query: users table");
    }
}