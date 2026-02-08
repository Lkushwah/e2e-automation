package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class app {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("This project will cover E2E shopping Automation");
                      
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        Properties props = new Properties();
        FileInputStream file = new FileInputStream("config.properties");
        props.load(file);

        String user = props.getProperty("user");
        String pass = props.getProperty("pass");

        driver.findElement(By.id("ap_email")).sendKeys(user);
        driver.findElement(By.id("ap_password")).sendKeys(pass);

     String url =  "https://amazon.in";
     driver.get(url);

        Thread.sleep(3000);

        driver.findElement(By.xpath("/html/body/div[1]/header/div/div[1]/div[3]/div/div[2]/a/span")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div/div/span/form/div[1]/input")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div/div/span/form/div[1]/input")).sendKeys("");    }
}
