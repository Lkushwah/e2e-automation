package tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;


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

    }


}
