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
@Owner("api-specialist")
@Feature("User API")
@TM4J("TM4J-130")
@Microservice("user-service")
@Story("User API functionality")
@Tag("user") @Tag("api") @Tag("rest")
public class UserApiTest {

    private RestSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new RestSteps();
    }

    @Test
    @DisplayName("Create new user")
    @Story("User creation")
    @JiraIssues({@JiraIssue("JIRA-463")})
    public void shouldCreateNewUser() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String userData = "Name: John Doe\n" +
                "Email: john@example.com\n" +
                "Role: User\n" +
                "Status: Active\n" +
                "Created: " + timestamp;
        
        String result = "User created successfully at " + timestamp + "\n" +
                "Status: Active\n" +
                "ID: USR-001\n" +
                "Verification: Pending";
        
        Allure.attachment("User Data", userData);
        Allure.attachment("Creation Result", result);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "User Creation: John Doe");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "User Creation: John Doe");
    }

    @Test
    @DisplayName("Update user profile")
    @Story("User profile")
    public void shouldUpdateUserProfile() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String profileData = "Name: John Smith\n" +
                "Email: john.smith@example.com\n" +
                "Phone: +1-555-123-4567\n" +
                "Address: 123 Main St, City, State\n" +
                "Updated: " + timestamp;
        
        String result = "Profile updated successfully at " + timestamp + "\n" +
                "Status: Active\n" +
                "Changes: 3 fields\n" +
                "Verification: Required";
        
        Allure.attachment("Profile Data", profileData);
        Allure.attachment("Update Result", result);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "Profile Update: John Smith");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "Profile Update: John Smith");
    }

    @Test
    @DisplayName("Delete user account")
    @Story("User deletion")
    public void shouldDeleteUserAccount() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String deletionData = "User ID: USR-001\n" +
                "Reason: Account closure request\n" +
                "Data Retention: 30 days\n" +
                "Backup: Created\n" +
                "Deleted: " + timestamp;
        
        String result = "User account deleted successfully at " + timestamp + "\n" +
                "Status: Deleted\n" +
                "Data Retention: 30 days\n" +
                "Recovery: Possible within retention period";
        
        Allure.attachment("Deletion Data", deletionData);
        Allure.attachment("Deletion Result", result);
        
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
        
        steps.createIssueWithTitle("testuser", "testrepo", "User Deletion: USR-001");
        steps.shouldSeeIssueWithTitle("testuser", "testrepo", "User Deletion: USR-001");
    }
}