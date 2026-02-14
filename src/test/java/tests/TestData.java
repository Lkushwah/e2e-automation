package tests;

import org.openqa.selenium.By;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestData {
    public static Map<String, By> getProducts() {
        Map<String, By> products = new LinkedHashMap<>();

        products.put("Sauce Labs Backpack", By.id("add-to-cart-sauce-labs-backpack"));
        products.put("Test.allTheThings() T-Shirt (Red)",By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));

        return products;
    }
    // Inside utils.TestData
    public static final By CART_ICON = By.className("shopping_cart_badge");
    public static final By CHECKOUT_BTN = By.id("checkout");
    public static final By CART_ITEM_NAME = By.className("inventory_item_name");
}
