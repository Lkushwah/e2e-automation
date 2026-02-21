package mobileTests;

import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;
import playwrightTests.playwrightBase; // Import your base class

public class NativeAppTest extends playwrightBase {

    @Test(description = "Verify Login on Native Android App")
    public void testNativeLogin() {
        // 1. Appium uses AppiumBy instead of page.locator
        // These IDs come from the 'Sauce Labs' sample app
        androidDriver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        androidDriver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");

        // 2. Click the login button
        androidDriver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        // 3. Assertion: Verify the products header is displayed
        boolean isHeaderDisplayed = androidDriver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']")).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed, "Login failed on Native App!");
    }
}