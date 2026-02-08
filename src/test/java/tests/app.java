package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.Driver;

public class app {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("This project will cover E2E shopping Automation of Amazon");

       ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.amazon.in/");

        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div[1]/header/div/div[1]/div[3]/div/div[2]/a/span")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div/div/span/form/div[1]/input")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div/div/span/form/div[1]/input")).sendKeys("");    }
}
