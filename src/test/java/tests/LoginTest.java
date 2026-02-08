package tests;

import org.openqa.selenium.By;
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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginTest {
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    Properties props;
    @Parameters("Browser")
    @BeforeMethod

    public void setup(String browser) throws IOException {
        String command = "taskkill /F /IM chromedriver.exe /IM geckodriver.exe /IM msedgedriver.exe /IM chrome.exe /IM firefox.exe /IM msedge.exe /T";
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
    @Test
    public void loginTest() throws InterruptedException {
        WebDriver driver = threadDriver.get();

        driver.get("https://www.saucedemo.com/");
        Thread.sleep(5000);
        String Upath = "//input[@placeholder='Username']";
        driver.findElement(By.xpath(Upath)).click();
        driver.findElement(By.xpath(Upath)).sendKeys(props.getProperty("user"));
        String Ppath = "//input[@placeholder='Password']";
        driver.findElement(By.xpath(Ppath)).click();
        driver.findElement(By.xpath(Ppath)).sendKeys(props.getProperty("pass"));
        driver.findElement(By.xpath("//input[@placeholder='Login']")).click();

    }

    @AfterMethod
    public void tearDown() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }
}
