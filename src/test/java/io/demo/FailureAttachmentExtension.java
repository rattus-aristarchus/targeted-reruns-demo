package io.demo;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.InputStream;

/**
 * Attaches the large demo fixtures (big.json, file_example.mp4) to the Allure report
 * only when a test fails, instead of on every run, to keep report size manageable.
 */
public class FailureAttachmentExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        attachIfPresent("big.json", "Big JSON Data");
        attachIfPresent("file_example.mp4", "Test Demo Video");
    }

    private void attachIfPresent(String resource, String attachmentName) {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(resource);
            if (stream != null) {
                Allure.attachment(attachmentName, stream);
            }
        } catch (Exception e) {
            // If the resource file is not found, continue without the attachment
        }
    }
}
