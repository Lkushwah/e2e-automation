package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class LoginTest extends BrowserDriversSetup{

    @Test
    public void loginTest() throws InterruptedException {
        var driver = getDriver();

        driver.get(props.getProperty("url"));
        Thread.sleep(5000);
        String Upath = "//input[@placeholder='Username']";
        driver.findElement(By.xpath(Upath)).click();
        driver.findElement(By.xpath(Upath)).sendKeys(props.getProperty("user"));
        String Ppath = "//input[@placeholder='Password']";
        driver.findElement(By.xpath(Ppath)).click();
        driver.findElement(By.xpath(Ppath)).sendKeys(props.getProperty("pass"));
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        String title = driver.getTitle();
        System.out.println("Title: " + title);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        String Heading = titleElement.getText();
        Assert.assertEquals(Heading, "Swag Labs", "Login Failed header 'Swag Labs' not found");

    }


}
