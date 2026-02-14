package tests;

import org.testng.annotations.Test;

public class sProduct extends BrowserDriversSetup{
    @Test(description = "Selecting Product")
    public void selectProductTest() throws InterruptedException {

        var driver = getDriver();
        driver.get(props.getProperty("url"));

    }

}
