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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.qameta.allure.Allure;
import java.io.InputStream;

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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Mobile Responsive Configuration (JSON)
        String mobileConfig = "{\n" +
                "  \"testId\": \"mobile-responsive\",\n" +
                "  \"breakpoints\": [\n" +
                "    {\"name\": \"mobile\", \"min\": 320, \"max\": 767, \"status\": \"active\"},\n" +
                "    {\"name\": \"tablet\", \"min\": 768, \"max\": 1023, \"status\": \"active\"},\n" +
                "    {\"name\": \"desktop\", \"min\": 1024, \"max\": 1920, \"status\": \"active\"}\n" +
                "  ],\n" +
                "  \"features\": [\n" +
                "    \"flexible-grid\",\n" +
                "    \"flexible-images\",\n" +
                "    \"media-queries\",\n" +
                "    \"touch-friendly\"\n" +
                "  ],\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Mobile Dashboard (HTML)
        String mobileDashboard = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Responsive Dashboard</title>\n" +
                "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f8f9fa;}\n" +
                ".container{background:white;padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}\n" +
                ".breakpoint{display:flex;justify-content:space-between;align-items:center;padding:15px;margin:10px 0;border-radius:4px;}\n" +
                ".mobile{background:#e8f5e8;border-left:4px solid #4caf50;}\n" +
                ".tablet{background:#e3f2fd;border-left:4px solid #2196f3;}\n" +
                ".desktop{background:#fff3e0;border-left:4px solid #ff9800;}\n" +
                ".status{font-weight:bold;color:#2e7d32;}\n" +
                ".metrics{display:grid;grid-template-columns:1fr 1fr 1fr;gap:20px;margin:20px 0;}\n" +
                ".metric{background:#f5f5f5;padding:15px;border-radius:4px;text-align:center;}\n" +
                ".feature{background:#f3e5f5;padding:8px;margin:5px;border-radius:4px;display:inline-block;}\n" +
                "</style></head>\n" +
                "<body><div class=\"container\">\n" +
                "<h1>📱 Mobile Responsive Design Dashboard</h1>\n" +
                "<div class=\"breakpoint mobile\">\n" +
                "<div><strong>Mobile</strong><br/>320px - 767px</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<div class=\"breakpoint tablet\">\n" +
                "<div><strong>Tablet</strong><br/>768px - 1023px</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<div class=\"breakpoint desktop\">\n" +
                "<div><strong>Desktop</strong><br/>1024px - 1920px</div>\n" +
                "<div class=\"status\">✅ Active</div>\n" +
                "</div>\n" +
                "<h2>Responsive Features</h2>\n" +
                "<div class=\"feature\">Flexible Grid</div>\n" +
                "<div class=\"feature\">Flexible Images</div>\n" +
                "<div class=\"feature\">Media Queries</div>\n" +
                "<div class=\"feature\">Touch Friendly</div>\n" +
                "<div class=\"metrics\">\n" +
                "<div class=\"metric\"><h3>Test Coverage</h3><p>95%</p></div>\n" +
                "<div class=\"metric\"><h3>Performance Score</h3><p>92/100</p></div>\n" +
                "<div class=\"metric\"><h3>Accessibility</h3><p>88/100</p></div>\n" +
                "</div>\n" +
                "<p><em>Last updated: " + timestamp + "</em></p>\n" +
                "</div></body></html>";
        
        // Mobile Metrics (CSV)
        String mobileMetrics = "breakpoint,min_width,max_width,test_coverage,performance,accessibility,timestamp\n" +
                "mobile,320,767,95%,92,88," + timestamp + "\n" +
                "tablet,768,1023,92%,89,85," + timestamp + "\n" +
                "desktop,1024,1920,98%,95,91," + timestamp;
        
        // Mobile Schema (XML)
        String mobileSchema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<mobile-responsive-config>\n" +
                "  <breakpoints>\n" +
                "    <breakpoint name=\"mobile\" min=\"320\" max=\"767\">\n" +
                "      <status>active</status>\n" +
                "      <features>\n" +
                "        <feature>flexible-grid</feature>\n" +
                "        <feature>flexible-images</feature>\n" +
                "        <feature>media-queries</feature>\n" +
                "        <feature>touch-friendly</feature>\n" +
                "      </features>\n" +
                "    </breakpoint>\n" +
                "    <breakpoint name=\"tablet\" min=\"768\" max=\"1023\">\n" +
                "      <status>active</status>\n" +
                "    </breakpoint>\n" +
                "    <breakpoint name=\"desktop\" min=\"1024\" max=\"1920\">\n" +
                "      <status>active</status>\n" +
                "    </breakpoint>\n" +
                "  </breakpoints>\n" +
                "</mobile-responsive-config>";
        
        // Mobile Log (Plain Text)
        String mobileLog = "MOBILE RESPONSIVE DESIGN LOG\n" +
                "=============================\n\n" +
                "Test ID: mobile-responsive\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n\n" +
                "BREAKPOINT CONFIGURATION:\n" +
                "------------------------\n" +
                "1. Mobile (320px - 767px) - ✅ Active\n" +
                "2. Tablet (768px - 1023px) - ✅ Active\n" +
                "3. Desktop (1024px - 1920px) - ✅ Active\n\n" +
                "RESPONSIVE FEATURES:\n" +
                "-------------------\n" +
                "• Flexible Grid System\n" +
                "• Flexible Images\n" +
                "• Media Queries\n" +
                "• Touch-Friendly Interface\n\n" +
                "PERFORMANCE METRICS:\n" +
                "-------------------\n" +
                "Test Coverage: 95%\n" +
                "Performance Score: 92/100\n" +
                "Accessibility Score: 88/100\n" +
                "Mobile Usability: 94/100\n\n" +
                "RECENT ACTIVITY:\n" +
                "---------------\n" +
                "• Responsive design testing started\n" +
                "• All breakpoints validated\n" +
                "• Touch interactions tested\n" +
                "• Performance metrics collected";
        
        // Mobile JavaScript
        String mobileJS = "// Mobile Responsive Design JavaScript\n" +
                "class MobileResponsive {\n" +
                "    constructor(config) {\n" +
                "        this.breakpoints = config.breakpoints;\n" +
                "        this.features = config.features;\n" +
                "        this.currentBreakpoint = this.detectBreakpoint();\n" +
                "        this.metrics = {\n" +
                "            testCoverage: 0,\n" +
                "            performance: 0,\n" +
                "            accessibility: 0\n" +
                "        };\n" +
                "    }\n\n" +
                "    detectBreakpoint() {\n" +
                "        const width = window.innerWidth;\n" +
                "        \n" +
                "        for (const breakpoint of this.breakpoints) {\n" +
                "            if (width >= breakpoint.min && width <= breakpoint.max) {\n" +
                "                return breakpoint.name;\n" +
                "            }\n        }\n        \n" +
                "        return 'unknown';\n    }\n\n" +
                "    isMobile() {\n" +
                "        return this.currentBreakpoint === 'mobile';\n    }\n\n" +
                "    isTablet() {\n" +
                "        return this.currentBreakpoint === 'tablet';\n    }\n\n" +
                "    isDesktop() {\n" +
                "        return this.currentBreakpoint === 'desktop';\n    }\n\n" +
                "    applyResponsiveFeatures() {\n" +
                "        this.features.forEach(feature => {\n" +
                "            switch (feature) {\n" +
                "                case 'flexible-grid':\n" +
                "                    this.applyFlexibleGrid();\n" +
                "                    break;\n" +
                "                case 'flexible-images':\n" +
                "                    this.applyFlexibleImages();\n" +
                "                    break;\n" +
                "                case 'media-queries':\n" +
                "                    this.applyMediaQueries();\n" +
                "                    break;\n" +
                "                case 'touch-friendly':\n" +
                "                    this.applyTouchFriendly();\n" +
                "                    break;\n            }\n        });\n    }\n\n" +
                "    applyFlexibleGrid() {\n" +
                "        const containers = document.querySelectorAll('.container');\n" +
                "        containers.forEach(container => {\n" +
                "            container.style.display = 'flex';\n" +
                "            container.style.flexWrap = 'wrap';\n" +
                "        });\n    }\n\n" +
                "    applyFlexibleImages() {\n" +
                "        const images = document.querySelectorAll('img');\n" +
                "        images.forEach(img => {\n" +
                "            img.style.maxWidth = '100%';\n" +
                "            img.style.height = 'auto';\n" +
                "        });\n    }\n\n" +
                "    applyMediaQueries() {\n" +
                "        const style = document.createElement('style');\n" +
                "        style.textContent = `\n" +
                "            @media (max-width: 767px) {\n" +
                "                .container { padding: 10px; }\n" +
                "                .feature { width: 100%; margin: 5px 0; }\n" +
                "            }\n" +
                "            @media (min-width: 768px) and (max-width: 1023px) {\n" +
                "                .feature { width: 50%; }\n" +
                "            }\n" +
                "            @media (min-width: 1024px) {\n" +
                "                .feature { width: 33.33%; }\n" +
                "            }\n        `;\n" +
                "        document.head.appendChild(style);\n    }\n\n" +
                "    applyTouchFriendly() {\n" +
                "        const buttons = document.querySelectorAll('button, .button');\n" +
                "        buttons.forEach(button => {\n" +
                "            button.style.minHeight = '44px';\n" +
                "            button.style.minWidth = '44px';\n" +
                "        });\n    }\n\n" +
                "    updateMetrics() {\n" +
                "        this.metrics.testCoverage = this.calculateTestCoverage();\n" +
                "        this.metrics.performance = this.calculatePerformance();\n" +
                "        this.metrics.accessibility = this.calculateAccessibility();\n" +
                "        \n" +
                "        return this.metrics;\n    }\n\n" +
                "    calculateTestCoverage() {\n" +
                "        // Simulate test coverage calculation\n" +
                "        return Math.floor(Math.random() * 20) + 80; // 80-100%\n    }\n\n" +
                "    calculatePerformance() {\n" +
                "        // Simulate performance calculation\n" +
                "        return Math.floor(Math.random() * 20) + 80; // 80-100\n    }\n\n" +
                "    calculateAccessibility() {\n" +
                "        // Simulate accessibility calculation\n" +
                "        return Math.floor(Math.random() * 20) + 80; // 80-100\n    }\n}\n\n" +
                "// Usage example\n" +
                "const mobileResponsive = new MobileResponsive(mobileConfig);\n" +
                "mobileResponsive.applyResponsiveFeatures();\n" +
                "console.log('Current breakpoint:', mobileResponsive.currentBreakpoint);\n" +
                "console.log('Metrics:', mobileResponsive.updateMetrics());";
        
        Allure.attachment("Mobile Configuration (JSON)", mobileConfig);
        Allure.attachment("Mobile Dashboard (HTML)", mobileDashboard);
        Allure.attachment("Mobile Metrics (CSV)", mobileMetrics);
        Allure.attachment("Mobile Schema (XML)", mobileSchema);
        Allure.attachment("Mobile Log (Text)", mobileLog);
        Allure.attachment("Mobile JavaScript", mobileJS);
        
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
        steps.createIssueWithTitle("Mobile Responsive Design Test");
        steps.shouldSeeIssueWithTitle("Mobile Responsive Design Test");
    }

    @ParameterizedTest
    @ValueSource(strings = {"320x568", "375x667", "414x896", "768x1024", "1024x1366"})
    @DisplayName("Test different mobile screen resolutions")
    @Story("Screen resolutions")
    public void shouldTestDifferentMobileScreenResolutions(String resolution) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String[] dimensions = resolution.split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        
        // Screen Resolution Configuration (JSON)
        String resolutionConfig = "{\n" +
                "  \"resolution\": \"" + resolution + "\",\n" +
                "  \"width\": " + width + ",\n" +
                "  \"height\": " + height + ",\n" +
                "  \"aspectRatio\": \"" + getAspectRatio(width, height) + "\",\n" +
                "  \"deviceType\": \"" + getDeviceType(width, height) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Resolution Report (HTML)
        String resolutionReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Screen Resolution Report</title></head>\n" +
                "<body><h1>Mobile Screen Resolution Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Resolution Details</h2>\n" +
                "<ul>\n" +
                "<li>Resolution: " + resolution + "</li>\n" +
                "<li>Width: " + width + "px</li>\n" +
                "<li>Height: " + height + "px</li>\n" +
                "<li>Aspect Ratio: " + getAspectRatio(width, height) + "</li>\n" +
                "<li>Device Type: " + getDeviceType(width, height) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Resolution Data (CSV)
        String resolutionData = "resolution,width,height,aspect_ratio,device_type,status,timestamp\n" +
                resolution + "," + width + "," + height + "," + getAspectRatio(width, height) + "," + getDeviceType(width, height) + ",testing," + timestamp;
        
        // Resolution Log (Plain Text)
        String resolutionLog = "MOBILE SCREEN RESOLUTION LOG\n" +
                "============================\n\n" +
                "Resolution: " + resolution + "\n" +
                "Width: " + width + "px\n" +
                "Height: " + height + "px\n" +
                "Aspect Ratio: " + getAspectRatio(width, height) + "\n" +
                "Device Type: " + getDeviceType(width, height) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Screen resolution validation completed";
        
        Allure.attachment("Resolution Configuration (JSON)", resolutionConfig);
        Allure.attachment("Resolution Report (HTML)", resolutionReport);
        Allure.attachment("Resolution Data (CSV)", resolutionData);
        Allure.attachment("Resolution Log (Text)", resolutionLog);
        
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
        steps.createIssueWithTitle("Mobile Resolution: " + resolution);
        steps.shouldSeeIssueWithTitle("Mobile Resolution: " + resolution);
    }
    
    private String getAspectRatio(int width, int height) {
        int gcd = gcd(width, height);
        int ratioWidth = width / gcd;
        int ratioHeight = height / gcd;
        return ratioWidth + ":" + ratioHeight;
    }
    
    private String getDeviceType(int width, int height) {
        if (width <= 480) return "Mobile";
        if (width <= 768) return "Tablet";
        return "Desktop";
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @ParameterizedTest
    @CsvSource({"Portrait, 375x667", "Landscape, 667x375", "Square, 400x400", "Ultra-wide, 1024x768"})
    @DisplayName("Test different mobile orientations")
    @Story("Device orientations")
    public void shouldTestDifferentMobileOrientations(String orientation, String dimensions) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String[] dims = dimensions.split("x");
        int width = Integer.parseInt(dims[0]);
        int height = Integer.parseInt(dims[1]);
        
        // Orientation Configuration (JSON)
        String orientationConfig = "{\n" +
                "  \"orientation\": \"" + orientation + "\",\n" +
                "  \"dimensions\": \"" + dimensions + "\",\n" +
                "  \"width\": " + width + ",\n" +
                "  \"height\": " + height + ",\n" +
                "  \"isLandscape\": " + (width > height) + ",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Orientation Report (HTML)
        String orientationReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Device Orientation Report</title></head>\n" +
                "<body><h1>Mobile Device Orientation Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Orientation Details</h2>\n" +
                "<ul>\n" +
                "<li>Orientation: " + orientation + "</li>\n" +
                "<li>Dimensions: " + dimensions + "</li>\n" +
                "<li>Width: " + width + "px</li>\n" +
                "<li>Height: " + height + "px</li>\n" +
                "<li>Is Landscape: " + (width > height) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Orientation Data (CSV)
        String orientationData = "orientation,dimensions,width,height,is_landscape,status,timestamp\n" +
                orientation + "," + dimensions + "," + width + "," + height + "," + (width > height) + ",testing," + timestamp;
        
        // Orientation Log (Plain Text)
        String orientationLog = "MOBILE DEVICE ORIENTATION LOG\n" +
                "==============================\n\n" +
                "Orientation: " + orientation + "\n" +
                "Dimensions: " + dimensions + "\n" +
                "Width: " + width + "px\n" +
                "Height: " + height + "px\n" +
                "Is Landscape: " + (width > height) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Device orientation validation completed";
        
        Allure.attachment("Orientation Configuration (JSON)", orientationConfig);
        Allure.attachment("Orientation Report (HTML)", orientationReport);
        Allure.attachment("Orientation Data (CSV)", orientationData);
        Allure.attachment("Orientation Log (Text)", orientationLog);
        
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
        steps.createIssueWithTitle("Mobile Orientation: " + orientation + " (" + dimensions + ")");
        steps.shouldSeeIssueWithTitle("Mobile Orientation: " + orientation + " (" + dimensions + ")");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Touch", "Swipe", "Pinch", "Long Press", "Double Tap"})
    @DisplayName("Test different mobile gestures")
    @Story("Touch gestures")
    public void shouldTestDifferentMobileGestures(String gesture) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Gesture Configuration (JSON)
        String gestureConfig = "{\n" +
                "  \"gesture\": \"" + gesture + "\",\n" +
                "  \"type\": \"" + getGestureType(gesture) + "\",\n" +
                "  \"fingers\": " + getGestureFingers(gesture) + ",\n" +
                "  \"duration\": \"" + getGestureDuration(gesture) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Gesture Report (HTML)
        String gestureReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Touch Gesture Report</title></head>\n" +
                "<body><h1>Mobile Touch Gesture Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Gesture Details</h2>\n" +
                "<ul>\n" +
                "<li>Gesture: " + gesture + "</li>\n" +
                "<li>Type: " + getGestureType(gesture) + "</li>\n" +
                "<li>Fingers: " + getGestureFingers(gesture) + "</li>\n" +
                "<li>Duration: " + getGestureDuration(gesture) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Gesture Data (CSV)
        String gestureData = "gesture,type,fingers,duration,status,timestamp\n" +
                gesture + "," + getGestureType(gesture) + "," + getGestureFingers(gesture) + "," + getGestureDuration(gesture) + ",testing," + timestamp;
        
        // Gesture Log (Plain Text)
        String gestureLog = "MOBILE TOUCH GESTURE LOG\n" +
                "=========================\n\n" +
                "Gesture: " + gesture + "\n" +
                "Type: " + getGestureType(gesture) + "\n" +
                "Fingers: " + getGestureFingers(gesture) + "\n" +
                "Duration: " + getGestureDuration(gesture) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Touch gesture validation completed";
        
        Allure.attachment("Gesture Configuration (JSON)", gestureConfig);
        Allure.attachment("Gesture Report (HTML)", gestureReport);
        Allure.attachment("Gesture Data (CSV)", gestureData);
        Allure.attachment("Gesture Log (Text)", gestureLog);
        
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
        steps.createIssueWithTitle("Mobile Gesture: " + gesture);
        steps.shouldSeeIssueWithTitle("Mobile Gesture: " + gesture);
    }
    
    private String getGestureType(String gesture) {
        switch (gesture) {
            case "Touch": return "single-tap";
            case "Swipe": return "drag";
            case "Pinch": return "zoom";
            case "Long Press": return "hold";
            case "Double Tap": return "double-tap";
            default: return "unknown";
        }
    }
    
    private int getGestureFingers(String gesture) {
        switch (gesture) {
            case "Touch": return 1;
            case "Swipe": return 1;
            case "Pinch": return 2;
            case "Long Press": return 1;
            case "Double Tap": return 1;
            default: return 1;
        }
    }
    
    private String getGestureDuration(String gesture) {
        switch (gesture) {
            case "Touch": return "instant";
            case "Swipe": return "short";
            case "Pinch": return "medium";
            case "Long Press": return "long";
            case "Double Tap": return "instant";
            default: return "unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"iOS Safari", "Chrome Mobile", "Firefox Mobile", "Samsung Internet", "UC Browser"})
    @DisplayName("Test different mobile browsers")
    @Story("Mobile browsers")
    public void shouldTestDifferentMobileBrowsers(String browser) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Browser Configuration (JSON)
        String browserConfig = "{\n" +
                "  \"browser\": \"" + browser + "\",\n" +
                "  \"engine\": \"" + getBrowserEngine(browser) + "\",\n" +
                "  \"version\": \"" + getBrowserVersion(browser) + "\",\n" +
                "  \"platform\": \"" + getBrowserPlatform(browser) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Browser Report (HTML)
        String browserReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Browser Report</title></head>\n" +
                "<body><h1>Mobile Browser Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Browser Details</h2>\n" +
                "<ul>\n" +
                "<li>Browser: " + browser + "</li>\n" +
                "<li>Engine: " + getBrowserEngine(browser) + "</li>\n" +
                "<li>Version: " + getBrowserVersion(browser) + "</li>\n" +
                "<li>Platform: " + getBrowserPlatform(browser) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Browser Data (CSV)
        String browserData = "browser,engine,version,platform,status,timestamp\n" +
                browser + "," + getBrowserEngine(browser) + "," + getBrowserVersion(browser) + "," + getBrowserPlatform(browser) + ",testing," + timestamp;
        
        // Browser Log (Plain Text)
        String browserLog = "MOBILE BROWSER TESTING LOG\n" +
                "===========================\n\n" +
                "Browser: " + browser + "\n" +
                "Engine: " + getBrowserEngine(browser) + "\n" +
                "Version: " + getBrowserVersion(browser) + "\n" +
                "Platform: " + getBrowserPlatform(browser) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile browser validation completed";
        
        Allure.attachment("Browser Configuration (JSON)", browserConfig);
        Allure.attachment("Browser Report (HTML)", browserReport);
        Allure.attachment("Browser Data (CSV)", browserData);
        Allure.attachment("Browser Log (Text)", browserLog);
        
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
        steps.createIssueWithTitle("Mobile Browser: " + browser);
        steps.shouldSeeIssueWithTitle("Mobile Browser: " + browser);
    }
    
    private String getBrowserEngine(String browser) {
        switch (browser) {
            case "iOS Safari": return "WebKit";
            case "Chrome Mobile": return "Blink";
            case "Firefox Mobile": return "Gecko";
            case "Samsung Internet": return "Blink";
            case "UC Browser": return "Blink";
            default: return "Unknown";
        }
    }
    
    private String getBrowserVersion(String browser) {
        switch (browser) {
            case "iOS Safari": return "17.0";
            case "Chrome Mobile": return "119.0";
            case "Firefox Mobile": return "119.0";
            case "Samsung Internet": return "23.0";
            case "UC Browser": return "15.0";
            default: return "Unknown";
        }
    }
    
    private String getBrowserPlatform(String browser) {
        switch (browser) {
            case "iOS Safari": return "iOS";
            case "Chrome Mobile": return "Android";
            case "Firefox Mobile": return "Android";
            case "Samsung Internet": return "Android";
            case "UC Browser": return "Android";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"iPhone", "Android", "iPad", "Tablet", "Smartphone"})
    @DisplayName("Test different mobile device types")
    @Story("Device types")
    public void shouldTestDifferentMobileDeviceTypes(String device) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Device Configuration (JSON)
        String deviceConfig = "{\n" +
                "  \"device\": \"" + device + "\",\n" +
                "  \"os\": \"" + getDeviceOS(device) + "\",\n" +
                "  \"category\": \"" + getDeviceCategory(device) + "\",\n" +
                "  \"screenSize\": \"" + getDeviceScreenSize(device) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Device Report (HTML)
        String deviceReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Device Report</title></head>\n" +
                "<body><h1>Mobile Device Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Device Details</h2>\n" +
                "<ul>\n" +
                "<li>Device: " + device + "</li>\n" +
                "<li>OS: " + getDeviceOS(device) + "</li>\n" +
                "<li>Category: " + getDeviceCategory(device) + "</li>\n" +
                "<li>Screen Size: " + getDeviceScreenSize(device) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Device Data (CSV)
        String deviceData = "device,os,category,screen_size,status,timestamp\n" +
                device + "," + getDeviceOS(device) + "," + getDeviceCategory(device) + "," + getDeviceScreenSize(device) + ",testing," + timestamp;
        
        // Device Log (Plain Text)
        String deviceLog = "MOBILE DEVICE TESTING LOG\n" +
                "=========================\n\n" +
                "Device: " + device + "\n" +
                "OS: " + getDeviceOS(device) + "\n" +
                "Category: " + getDeviceCategory(device) + "\n" +
                "Screen Size: " + getDeviceScreenSize(device) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile device validation completed";
        
        Allure.attachment("Device Configuration (JSON)", deviceConfig);
        Allure.attachment("Device Report (HTML)", deviceReport);
        Allure.attachment("Device Data (CSV)", deviceData);
        Allure.attachment("Device Log (Text)", deviceLog);
        
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
        steps.createIssueWithTitle("Mobile Device: " + device);
        steps.shouldSeeIssueWithTitle("Mobile Device: " + device);
    }
    
    private String getDeviceOS(String device) {
        switch (device) {
            case "iPhone": return "iOS";
            case "Android": return "Android";
            case "iPad": return "iPadOS";
            case "Tablet": return "Android/iOS";
            case "Smartphone": return "Android/iOS";
            default: return "Unknown";
        }
    }
    
    private String getDeviceCategory(String device) {
        switch (device) {
            case "iPhone": return "Smartphone";
            case "Android": return "Smartphone";
            case "iPad": return "Tablet";
            case "Tablet": return "Tablet";
            case "Smartphone": return "Smartphone";
            default: return "Unknown";
        }
    }
    
    private String getDeviceScreenSize(String device) {
        switch (device) {
            case "iPhone": return "4.7-6.7 inches";
            case "Android": return "4.5-7.0 inches";
            case "iPad": return "9.7-12.9 inches";
            case "Tablet": return "7.0-12.9 inches";
            case "Smartphone": return "4.5-7.0 inches";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"2G", "3G", "4G", "5G", "WiFi"})
    @DisplayName("Test different mobile network conditions")
    @Story("Network conditions")
    public void shouldTestDifferentMobileNetworkConditions(String network) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Network Configuration (JSON)
        String networkConfig = "{\n" +
                "  \"network\": \"" + network + "\",\n" +
                "  \"speed\": \"" + getNetworkSpeed(network) + "\",\n" +
                "  \"latency\": \"" + getNetworkLatency(network) + "\",\n" +
                "  \"reliability\": \"" + getNetworkReliability(network) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Network Report (HTML)
        String networkReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Network Report</title></head>\n" +
                "<body><h1>Mobile Network Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Network Details</h2>\n" +
                "<ul>\n" +
                "<li>Network: " + network + "</li>\n" +
                "<li>Speed: " + getNetworkSpeed(network) + "</li>\n" +
                "<li>Latency: " + getNetworkLatency(network) + "</li>\n" +
                "<li>Reliability: " + getNetworkReliability(network) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Network Data (CSV)
        String networkData = "network,speed,latency,reliability,status,timestamp\n" +
                network + "," + getNetworkSpeed(network) + "," + getNetworkLatency(network) + "," + getNetworkReliability(network) + ",testing," + timestamp;
        
        // Network Log (Plain Text)
        String networkLog = "MOBILE NETWORK TESTING LOG\n" +
                "===========================\n\n" +
                "Network: " + network + "\n" +
                "Speed: " + getNetworkSpeed(network) + "\n" +
                "Latency: " + getNetworkLatency(network) + "\n" +
                "Reliability: " + getNetworkReliability(network) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile network validation completed";
        
        Allure.attachment("Network Configuration (JSON)", networkConfig);
        Allure.attachment("Network Report (HTML)", networkReport);
        Allure.attachment("Network Data (CSV)", networkData);
        Allure.attachment("Network Log (Text)", networkLog);
        
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
        steps.createIssueWithTitle("Mobile Network: " + network);
        steps.shouldSeeIssueWithTitle("Mobile Network: " + network);
    }
    
    private String getNetworkSpeed(String network) {
        switch (network) {
            case "2G": return "50-100 kbps";
            case "3G": return "1-3 Mbps";
            case "4G": return "10-50 Mbps";
            case "5G": return "100-1000 Mbps";
            case "WiFi": return "50-1000 Mbps";
            default: return "Unknown";
        }
    }
    
    private String getNetworkLatency(String network) {
        switch (network) {
            case "2G": return "500-1000ms";
            case "3G": return "200-500ms";
            case "4G": return "50-200ms";
            case "5G": return "1-10ms";
            case "WiFi": return "1-50ms";
            default: return "Unknown";
        }
    }
    
    private String getNetworkReliability(String network) {
        switch (network) {
            case "2G": return "Low";
            case "3G": return "Medium";
            case "4G": return "High";
            case "5G": return "Very High";
            case "WiFi": return "High";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"GPS", "Camera", "Microphone", "Accelerometer", "Gyroscope"})
    @DisplayName("Test different mobile sensors")
    @Story("Mobile sensors")
    public void shouldTestDifferentMobileSensors(String sensor) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Sensor Configuration (JSON)
        String sensorConfig = "{\n" +
                "  \"sensor\": \"" + sensor + "\",\n" +
                "  \"type\": \"" + getSensorType(sensor) + "\",\n" +
                "  \"accuracy\": \"" + getSensorAccuracy(sensor) + "\",\n" +
                "  \"permission\": \"" + getSensorPermission(sensor) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Sensor Report (HTML)
        String sensorReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Sensor Report</title></head>\n" +
                "<body><h1>Mobile Sensor Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Sensor Details</h2>\n" +
                "<ul>\n" +
                "<li>Sensor: " + sensor + "</li>\n" +
                "<li>Type: " + getSensorType(sensor) + "</li>\n" +
                "<li>Accuracy: " + getSensorAccuracy(sensor) + "</li>\n" +
                "<li>Permission: " + getSensorPermission(sensor) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Sensor Data (CSV)
        String sensorData = "sensor,type,accuracy,permission,status,timestamp\n" +
                sensor + "," + getSensorType(sensor) + "," + getSensorAccuracy(sensor) + "," + getSensorPermission(sensor) + ",testing," + timestamp;
        
        // Sensor Log (Plain Text)
        String sensorLog = "MOBILE SENSOR TESTING LOG\n" +
                "==========================\n\n" +
                "Sensor: " + sensor + "\n" +
                "Type: " + getSensorType(sensor) + "\n" +
                "Accuracy: " + getSensorAccuracy(sensor) + "\n" +
                "Permission: " + getSensorPermission(sensor) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile sensor validation completed";
        
        Allure.attachment("Sensor Configuration (JSON)", sensorConfig);
        Allure.attachment("Sensor Report (HTML)", sensorReport);
        Allure.attachment("Sensor Data (CSV)", sensorData);
        Allure.attachment("Sensor Log (Text)", sensorLog);
        
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
        steps.createIssueWithTitle("Mobile Sensor: " + sensor);
        steps.shouldSeeIssueWithTitle("Mobile Sensor: " + sensor);
    }
    
    private String getSensorType(String sensor) {
        switch (sensor) {
            case "GPS": return "Location";
            case "Camera": return "Image";
            case "Microphone": return "Audio";
            case "Accelerometer": return "Motion";
            case "Gyroscope": return "Motion";
            default: return "Unknown";
        }
    }
    
    private String getSensorAccuracy(String sensor) {
        switch (sensor) {
            case "GPS": return "High";
            case "Camera": return "High";
            case "Microphone": return "High";
            case "Accelerometer": return "Medium";
            case "Gyroscope": return "High";
            default: return "Unknown";
        }
    }
    
    private String getSensorPermission(String sensor) {
        switch (sensor) {
            case "GPS": return "Location";
            case "Camera": return "Camera";
            case "Microphone": return "Microphone";
            case "Accelerometer": return "None";
            case "Gyroscope": return "None";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Push Notifications", "SMS", "Email", "In-app", "Banner"})
    @DisplayName("Test different mobile notification types")
    @Story("Mobile notifications")
    public void shouldTestDifferentMobileNotificationTypes(String notification) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Notification Configuration (JSON)
        String notificationConfig = "{\n" +
                "  \"notification\": \"" + notification + "\",\n" +
                "  \"type\": \"" + getNotificationType(notification) + "\",\n" +
                "  \"priority\": \"" + getNotificationPriority(notification) + "\",\n" +
                "  \"delivery\": \"" + getNotificationDelivery(notification) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // Notification Report (HTML)
        String notificationReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile Notification Report</title></head>\n" +
                "<body><h1>Mobile Notification Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>Notification Details</h2>\n" +
                "<ul>\n" +
                "<li>Notification: " + notification + "</li>\n" +
                "<li>Type: " + getNotificationType(notification) + "</li>\n" +
                "<li>Priority: " + getNotificationPriority(notification) + "</li>\n" +
                "<li>Delivery: " + getNotificationDelivery(notification) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // Notification Data (CSV)
        String notificationData = "notification,type,priority,delivery,status,timestamp\n" +
                notification + "," + getNotificationType(notification) + "," + getNotificationPriority(notification) + "," + getNotificationDelivery(notification) + ",testing," + timestamp;
        
        // Notification Log (Plain Text)
        String notificationLog = "MOBILE NOTIFICATION TESTING LOG\n" +
                "===============================\n\n" +
                "Notification: " + notification + "\n" +
                "Type: " + getNotificationType(notification) + "\n" +
                "Priority: " + getNotificationPriority(notification) + "\n" +
                "Delivery: " + getNotificationDelivery(notification) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile notification validation completed";
        
        Allure.attachment("Notification Configuration (JSON)", notificationConfig);
        Allure.attachment("Notification Report (HTML)", notificationReport);
        Allure.attachment("Notification Data (CSV)", notificationData);
        Allure.attachment("Notification Log (Text)", notificationLog);
        
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
        steps.createIssueWithTitle("Mobile Notification: " + notification);
        steps.shouldSeeIssueWithTitle("Mobile Notification: " + notification);
    }
    
    private String getNotificationType(String notification) {
        switch (notification) {
            case "Push Notifications": return "Real-time";
            case "SMS": return "Text";
            case "Email": return "Email";
            case "In-app": return "Internal";
            case "Banner": return "Visual";
            default: return "Unknown";
        }
    }
    
    private String getNotificationPriority(String notification) {
        switch (notification) {
            case "Push Notifications": return "High";
            case "SMS": return "Critical";
            case "Email": return "Medium";
            case "In-app": return "Low";
            case "Banner": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getNotificationDelivery(String notification) {
        switch (notification) {
            case "Push Notifications": return "Instant";
            case "SMS": return "Instant";
            case "Email": return "Delayed";
            case "In-app": return "Immediate";
            case "Banner": return "Immediate";
            default: return "Unknown";
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Offline Mode", "Background Sync", "Data Saver", "Battery Optimization", "Storage Management"})
    @DisplayName("Test different mobile app states")
    @Story("App states")
    public void shouldTestDifferentMobileAppStates(String state) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // App State Configuration (JSON)
        String stateConfig = "{\n" +
                "  \"state\": \"" + state + "\",\n" +
                "  \"type\": \"" + getStateType(state) + "\",\n" +
                "  \"impact\": \"" + getStateImpact(state) + "\",\n" +
                "  \"battery\": \"" + getStateBattery(state) + "\",\n" +
                "  \"timestamp\": \"" + timestamp + "\",\n" +
                "  \"status\": \"testing\"\n" +
                "}";
        
        // App State Report (HTML)
        String stateReport = "<!DOCTYPE html>\n" +
                "<html><head><title>Mobile App State Report</title></head>\n" +
                "<body><h1>Mobile App State Testing Report</h1>\n" +
                "<p>Generated: " + timestamp + "</p>\n" +
                "<h2>App State Details</h2>\n" +
                "<ul>\n" +
                "<li>State: " + state + "</li>\n" +
                "<li>Type: " + getStateType(state) + "</li>\n" +
                "<li>Impact: " + getStateImpact(state) + "</li>\n" +
                "<li>Battery: " + getStateBattery(state) + "</li>\n" +
                "</ul>\n" +
                "</body></html>";
        
        // App State Data (CSV)
        String stateData = "state,type,impact,battery,status,timestamp\n" +
                state + "," + getStateType(state) + "," + getStateImpact(state) + "," + getStateBattery(state) + ",testing," + timestamp;
        
        // App State Log (Plain Text)
        String stateLog = "MOBILE APP STATE TESTING LOG\n" +
                "==============================\n\n" +
                "State: " + state + "\n" +
                "Type: " + getStateType(state) + "\n" +
                "Impact: " + getStateImpact(state) + "\n" +
                "Battery: " + getStateBattery(state) + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Status: Testing\n" +
                "Action: Mobile app state validation completed";
        
        Allure.attachment("App State Configuration (JSON)", stateConfig);
        Allure.attachment("App State Report (HTML)", stateReport);
        Allure.attachment("App State Data (CSV)", stateData);
        Allure.attachment("App State Log (Text)", stateLog);
        
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
        steps.createIssueWithTitle("Mobile App State: " + state);
        steps.shouldSeeIssueWithTitle("Mobile App State: " + state);
    }
    
    private String getStateType(String state) {
        switch (state) {
            case "Offline Mode": return "Connectivity";
            case "Background Sync": return "Performance";
            case "Data Saver": return "Resource";
            case "Battery Optimization": return "Power";
            case "Storage Management": return "Resource";
            default: return "Unknown";
        }
    }
    
    private String getStateImpact(String state) {
        switch (state) {
            case "Offline Mode": return "High";
            case "Background Sync": return "Medium";
            case "Data Saver": return "Low";
            case "Battery Optimization": return "Medium";
            case "Storage Management": return "Low";
            default: return "Unknown";
        }
    }
    
    private String getStateBattery(String state) {
        switch (state) {
            case "Offline Mode": return "Saves";
            case "Background Sync": return "Uses";
            case "Data Saver": return "Saves";
            case "Battery Optimization": return "Saves";
            case "Storage Management": return "Neutral";
            default: return "Unknown";
        }
    }
}
