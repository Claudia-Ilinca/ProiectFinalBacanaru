package Tests.StandardUser;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying checkout flow with standard_user.
 */
public class TestCheckoutFlow extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testCompleteCheckout() {
        LoggerUtility.startTest("testCompleteCheckout");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnPage(), "Not on cart page");
        cartPage.goToCheckout();
        LoggerUtility.info("Navigated to checkout page");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isOnPage(), "Not on checkout page");
        checkoutPage.fillCheckoutForm(userData.getFirstName(), userData.getLastName(), userData.getPostalCode());
        LoggerUtility.info("Filled checkout form with valid data");

        checkoutPage.completeCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutComplete(), "Checkout not completed");
        LoggerUtility.info("Checkout completed successfully");

        LoggerUtility.endTest("testCompleteCheckout");
    }

    @Test
    public void testIncompleteCheckoutFields() {
        LoggerUtility.startTest("testIncompleteCheckoutFields");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnPage(), "Not on cart page");
        cartPage.goToCheckout();
        LoggerUtility.info("Navigated to checkout page");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isOnPage(), "Not on checkout page");
        checkoutPage.fillCheckoutForm("", userData.getLastName(), userData.getPostalCode());
        LoggerUtility.info("Filled checkout form with missing first name");

        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "Expected error for incomplete fields");
        LoggerUtility.info("Error displayed for missing first name");

        LoggerUtility.endTest("testIncompleteCheckoutFields");
    }
}