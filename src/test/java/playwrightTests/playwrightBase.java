package playwrightTests;
import com.microsoft.playwright.options.*;
import com.microsoft.playwright.*;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.*;
import java.nio.file.Paths;

public class PlaywrightBase {
    // 1. Declare variables at the CLASS LEVEL so they are accessible everywhere
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected DeviceDescriptor device;

    @BeforeClass
    @Parameters("Browser")
    public void launchBrowser(@Optional("chromium") String browserName) {
        playwright = Playwright.create();

        // 2. ASSIGN to the variables (don't re-declare them with 'Browser browser = ...')
        if (browserName.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
    }

    @BeforeMethod
    @Parameters("Device")
    public void createContext(@Optional("Desktop") String deviceName) {
        Browser.NewContextOptions options;

        if (!deviceName.equalsIgnoreCase("Desktop")) {
            // In Java, playwright.devices().get() returns NewContextOptions directly
            options = playwright.devices().get(deviceName);

            if (options == null) {
                throw new RuntimeException("Device '" + deviceName + "' not found in Playwright registry!");
            }
        } else {
            options = new Browser.NewContextOptions();
        }

        // Now simply pass those options to create the context
        context = browser.newContext(options);
        page = context.newPage();
    }

}