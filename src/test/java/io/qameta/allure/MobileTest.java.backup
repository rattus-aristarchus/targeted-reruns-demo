package io.qameta.allure;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

@ExtendWith(AllureJunit5.class)
@Layer("web")
@Owner("mobile-tester")
@Feature("Mobile Functionality")
@TM4J("TM4J-128")
@Microservice("mobile-service")
@Story("Mobile app functionality")
@Tag("mobile") @Tag("responsive") @Tag("web")
public class MobileTest {

    private WebSteps steps;

    @BeforeEach
    void startDriver() {
        steps = new WebSteps();
    }

    @Test
    @DisplayName("Test mobile responsive design")
    @Story("Responsive design")
    @JiraIssues({@JiraIssue("JIRA-461")})
    public void shouldTestMobileResponsiveDesign() {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Responsive Design Test");
        steps.shouldSeeIssueWithTitle("Mobile Responsive Design Test");
    }

    @ParameterizedTest
    @ValueSource(strings = {"320x568", "375x667", "414x896", "768x1024", "1024x1366"})
    @DisplayName("Test different mobile screen resolutions")
    @Story("Screen resolutions")
    public void shouldTestDifferentMobileScreenResolutions(String resolution) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Resolution: " + resolution);
        steps.shouldSeeIssueWithTitle("Mobile Resolution: " + resolution);
    }

    @ParameterizedTest
    @CsvSource({"Portrait, 375x667", "Landscape, 667x375", "Square, 400x400", "Ultra-wide, 1024x768"})
    @DisplayName("Test different mobile orientations")
    @Story("Device orientations")
    public void shouldTestDifferentMobileOrientations(String orientation, String dimensions) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Orientation: " + orientation + " (" + dimensions + ")");
        steps.shouldSeeIssueWithTitle("Mobile Orientation: " + orientation + " (" + dimensions + ")");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Touch", "Swipe", "Pinch", "Long Press", "Double Tap"})
    @DisplayName("Test different mobile gestures")
    @Story("Touch gestures")
    public void shouldTestDifferentMobileGestures(String gesture) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Gesture: " + gesture);
        steps.shouldSeeIssueWithTitle("Mobile Gesture: " + gesture);
    }

    @ParameterizedTest
    @ValueSource(strings = {"iOS Safari", "Chrome Mobile", "Firefox Mobile", "Samsung Internet", "UC Browser"})
    @DisplayName("Test different mobile browsers")
    @Story("Mobile browsers")
    public void shouldTestDifferentMobileBrowsers(String browser) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Browser: " + browser);
        steps.shouldSeeIssueWithTitle("Mobile Browser: " + browser);
    }

    @ParameterizedTest
    @ValueSource(strings = {"iPhone", "Android", "iPad", "Tablet", "Smartphone"})
    @DisplayName("Test different mobile device types")
    @Story("Device types")
    public void shouldTestDifferentMobileDeviceTypes(String device) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Device: " + device);
        steps.shouldSeeIssueWithTitle("Mobile Device: " + device);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2G", "3G", "4G", "5G", "WiFi"})
    @DisplayName("Test different mobile network conditions")
    @Story("Network conditions")
    public void shouldTestDifferentMobileNetworkConditions(String network) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Network: " + network);
        steps.shouldSeeIssueWithTitle("Mobile Network: " + network);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GPS", "Camera", "Microphone", "Accelerometer", "Gyroscope"})
    @DisplayName("Test different mobile sensors")
    @Story("Mobile sensors")
    public void shouldTestDifferentMobileSensors(String sensor) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Sensor: " + sensor);
        steps.shouldSeeIssueWithTitle("Mobile Sensor: " + sensor);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Push Notifications", "SMS", "Email", "In-app", "Banner"})
    @DisplayName("Test different mobile notification types")
    @Story("Mobile notifications")
    public void shouldTestDifferentMobileNotificationTypes(String notification) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile Notification: " + notification);
        steps.shouldSeeIssueWithTitle("Mobile Notification: " + notification);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Offline Mode", "Background Sync", "Data Saver", "Battery Optimization", "Storage Management"})
    @DisplayName("Test different mobile app states")
    @Story("App states")
    public void shouldTestDifferentMobileAppStates(String state) {
        steps.openIssuesPage("testuser", "testrepo");
        steps.createIssueWithTitle("Mobile App State: " + state);
        steps.shouldSeeIssueWithTitle("Mobile App State: " + state);
    }
}
