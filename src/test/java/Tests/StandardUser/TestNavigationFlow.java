package Tests.StandardUser;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.AboutPage;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying navigation flow with standard_user.
 */
public class TestNavigationFlow extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testNavigateToAboutPage() {
        LoggerUtility.startTest("testNavigateToAboutPage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        inventoryPage.goToAboutPage();
        LoggerUtility.info("Navigated to about page");

        AboutPage aboutPage = new AboutPage(driver);
        Assert.assertTrue(aboutPage.isOnPage(), "Not on about page");
        Assert.assertFalse(aboutPage.is404Displayed(), "Expected no 404 error for standard_user");
        LoggerUtility.info("Verified about page is accessible");

        LoggerUtility.endTest("testNavigateToAboutPage");
    }

    @Test
    public void testNavigateBackFromCart() {
        LoggerUtility.startTest("testNavigateBackFromCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        LoggerUtility.info("Navigated to cart page");

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnPage(), "Not on cart page");
        cartPage.goBackToInventory();
        Assert.assertTrue(inventoryPage.isOnPage(), "Not navigated back to inventory page");
        LoggerUtility.info("Navigated back to inventory page");

        LoggerUtility.endTest("testNavigateBackFromCart");
    }
}