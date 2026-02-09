package tests;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BrowserDriversSetup {
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    Properties props;

    @BeforeSuite
    public void beforeSuite(){
//       try{
//           ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
//           "taskkill /F /IM geckodriver.exe /IM chromedriver.exe /IM msedgedriver.exe /IM firefox.exe /IM chrome.exe /IM msedge.exe /T");
//        pb.start().waitFor();
//       }
//       catch(Exception e){}
    }

    @Parameters("Browser")
    @BeforeMethod

    public void setup(String browser) throws IOException {

        props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        WebDriver driver = null;
        if(browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        }
        else if(browser.equalsIgnoreCase("Edge")){
            EdgeOptions options = new EdgeOptions();
//            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.addArguments("--remote-allow-origins=*");

            // Optional: Use these for extra stability on Windows 11
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new EdgeDriver(options);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(browser.equalsIgnoreCase("firefox")){
            FirefoxOptions options = new FirefoxOptions();

            // 1. Force Selenium to wait for the browser window to be fully 'active'
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            // 2. Disable features that slow down startup on Windows 11
            options.addPreference("browser.tabs.remote.autostart", false);
            options.addPreference("toolkit.telemetry.enabled", false);

            driver = new FirefoxDriver(options);

            // 3. CRITICAL: Give Windows 11 time to stabilize the profile folder
            try { Thread.sleep(5000); } catch (InterruptedException e) {}

            driver.manage().window().maximize();
        }

        threadDriver.set(driver);
    }
    public WebDriver getDriver() {
        return threadDriver.get();
    }

    @AfterMethod
    public void tearDown() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }

}
