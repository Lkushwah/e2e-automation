package playwrightTests;
import com.microsoft.playwright.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class PlaywrightLoginTest extends playwrightBase{

    @Test(description = "E2E Login and Product Selection in Playwright")
    public void verifyMultipleProductsInPlaywright() {

        page.navigate("https://www.saucedemo.com/");
        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");
        page.locator("#login-button").click();

        page.waitForURL("**/inventory.html");

        Map<String, By> productsFromSelenium = tests.TestData.getProducts();

        for (Map.Entry<String, By> entry : productsFromSelenium.entrySet()) {
            String rawLocator = entry.getValue().toString();
            String playwrightSelector = rawLocator.substring(rawLocator.indexOf(":") + 2).trim();

            page.click(playwrightSelector);

            String currentText = page.locator(playwrightSelector).innerText();
            Assert.assertEquals(currentText, "Remove", "Button did not update!");
        }
            page.click(".shopping_cart_link");

            int expectedCount = productsFromSelenium.size();
            int actualCount = page.locator(".cart_item").count();
            Assert.assertEquals(actualCount, expectedCount, "Cart item count mismatch!");

            List<String> actualNames = page.locator(".inventory_item_name").allInnerTexts();
            for (String expectedName : productsFromSelenium.keySet()) {
                Assert.assertTrue(actualNames.contains(expectedName),
                        "Product '" + expectedName + "' was not found in the cart!");
            }
            page.locator("#checkout").click();
//            page.locator("btn.btn_action.btn_medium.checkout_button").click();

            page.locator("#first-name").fill("standard_user");
            page.locator("#last-name").fill("secret_sauce");
            page.locator("#postal-code").fill("12345");

            page.click("#continue");
            page.waitForURL("**/checkout-step-two.html");

            Assert.assertEquals(page.locator(".title").innerText(),"Checkout: Overview", "On Checkout Page!");

            page.locator("#finish").click();

            Assert.assertEquals(page.locator(".title").innerText(),"Checkout: Complete!");
            System.out.println("Shopping done");
    }
}
