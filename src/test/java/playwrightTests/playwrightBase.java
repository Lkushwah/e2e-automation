package playwrightTests;

import com.microsoft.playwright.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class playwrightBase {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected AndroidDriver androidDriver;

    @BeforeClass
    @Parameters("Browser")
    public void launchBrowser(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);

        if (browserName.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(launchOptions);
        } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(launchOptions);
        } else {
            browser = playwright.chromium().launch(launchOptions);
        }
    }

    @BeforeMethod
    @Parameters({"Device", "Platform"}) // Added Platform parameter
    public void setupTest(@Optional("Desktop") String deviceName, @Optional("Web") String platform) throws Exception {

        if (platform.equalsIgnoreCase("Mobile_Native")) {
            // --- APPIUM REAL DEVICE LOGIC ---
            UiAutomator2Options appiumOptions = new UiAutomator2Options()
                    .setDeviceName("Android Emulator")
                    .setApp(System.getProperty("user.dir") + "/src/test/resources/SauceLabs.apk")
                    .setAutomationName("UiAutomator2");

            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), appiumOptions);
        } else {
            // --- YOUR EXISTING PLAYWRIGHT EMULATION LOGIC ---
            Browser.NewContextOptions options = new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("target/videos/"));

            if (deviceName.equalsIgnoreCase("iPhone 13")) {
                options.setViewportSize(390, 844)
                        .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                        .setHasTouch(true).setIsMobile(true);
            } else if (deviceName.equalsIgnoreCase("Pixel 5")) {
                options.setViewportSize(393, 851)
                        .setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.91 Mobile Safari/537.36")
                        .setHasTouch(true).setIsMobile(true);
            }

            context = browser.newContext(options);

            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

            page = context.newPage();
        }
    }
    @AfterMethod
    public void teardownTest(ITestResult result) {
        String methodName = result.getMethod().getMethodName();

        // 1. HANDLE PLAYWRIGHT TEARDOWN (Web/Emulation)
        if (context != null) {
            String tracePath = "target/traces/" + methodName + "_" + System.currentTimeMillis() + ".zip";
            try {
                context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));

                Allure.addAttachment("Trace: " + methodName, "application/zip",
                        new ByteArrayInputStream(Files.readAllBytes(Paths.get(tracePath))), ".zip");

                if (page.video() != null) {
                    Allure.addAttachment("Video: " + methodName, "video/webm",
                            new ByteArrayInputStream(Files.readAllBytes(page.video().path())), ".webm");
                }
            } catch (IOException e) {
                System.out.println("Failed to attach Playwright artifacts: " + e.getMessage());
            } finally {
                context.close();
                context = null; // Clear to prevent reuse errors
            }
        }

        // 2. HANDLE APPIUM TEARDOWN (Native Mobile)
        if (androidDriver != null) {
            // Take a final screenshot for Allure if the test failed
            if (result.getStatus() == ITestResult.FAILURE) {
                byte[] screenshot = androidDriver.getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                Allure.addAttachment("Appium Failure Screenshot", new ByteArrayInputStream(screenshot));
            }
            androidDriver.quit(); // Properly kills the session on the emulator
            androidDriver = null;
        }

    }

    @AfterClass
    public void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}