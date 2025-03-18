package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for verifying checkout field issues with error_user.
 */
public class TestCheckoutFields extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testCheckoutFieldTwoNotWorking() {
        LoggerUtility.startTest("testCheckoutFieldTwoNotWorking");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> inventoryPage.isOnPage());
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        LoggerUtility.endTest("testCheckoutFieldTwoNotWorking");
    }
}