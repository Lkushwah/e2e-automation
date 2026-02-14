package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;


public class LoginTest extends BrowserDriversSetup{

    @Test(priority = 1,description = "Verify successful login")
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
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("app_logo")));
        String Heading = titleElement.getText();
        Assert.assertEquals(Heading, "Swag Labs", "Login Failed header 'Swag Labs' not found");

    }
    @Test(priority = 2,dependsOnMethods = "loginTest",description = "select the product")
    public void selectProductTest() throws InterruptedException {

         try {
             var driver = getDriver();
             Map<String, By> itemsToBuy = TestData.getProducts();

             for (Map.Entry<String, By> item : itemsToBuy.entrySet()) {

                 WebElement button = driver.findElement(item.getValue());
                 button.click();
                 String productName = item.getKey();
                 String text = button.getText();
                 Assert.assertEquals(text,"Remove", "Button for " + productName + " did not change to 'Remove'!");

             }
             saveScreenshot("All_Items_Added");

         }
        catch (Exception e) {
            saveScreenshot("Failed");
            throw e;
        }
    }

    @Test(priority = 3,dependsOnMethods = "selectProductTest",description = "Check on the cart page")
    public void checkCartTest() throws InterruptedException {
        var driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(TestData.CART_ICON).click();

        String title = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(title, "Your cart" ,"this is done" );

        List<WebElement> cartElements = driver.findElements(TestData.CART_ITEM_NAME);
        List<String> actualCartProducts = new ArrayList<>();

        for (WebElement element : cartElements) {
            actualCartProducts.add(element.getText());
        }

        Set<String> expectedProducts = TestData.getProducts().keySet();

        for (String expectedName : expectedProducts) {
            Assert.assertTrue(actualCartProducts.contains(expectedName),
                    "Product '" + expectedName + "' was not found in the cart!");
        }

        Assert.assertEquals(actualCartProducts.size(), expectedProducts.size(),
                "The number of items in the cart does not match selection!");

        driver.findElement(TestData.CHECKOUT_BTN).click();
        saveScreenshot("Cart_Verified_Successfully");
    }


}