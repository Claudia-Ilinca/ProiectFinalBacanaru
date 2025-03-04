package Tests.PerformanceGlitchUser;

import Logger.LoggerUtility;
import ObjectData.PerformanceGlitchUserData;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying checkout completion delay with performance_glitch_user.
 */
public class TestCheckoutCompletionDelay extends BaseTest {

    private final PerformanceGlitchUserData userData = new PerformanceGlitchUserData();

    @Test
    public void testCheckoutCompletionDelay() {
        LoggerUtility.startTest("testCheckoutCompletionDelay");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with performance_glitch_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        cartPage.goToCheckout();
        LoggerUtility.info("Navigated to checkout page");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Not on checkout page");
        checkoutPage.fillCheckoutForm(userData.getFirstName(), userData.getLastName(), userData.getPostalCode());
        LoggerUtility.info("Filled checkout form and clicked Continue");

        long startTime = System.currentTimeMillis();
        checkoutPage.completeCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutComplete(), "Checkout not completed");
        LoggerUtility.info("Checkout completed");

        checkoutPage.goBackHome();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        LoggerUtility.info("Time to navigate back to inventory page after checkout: " + duration + " ms");

        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page after checkout");
        Assert.assertTrue(duration > 2000,
                "Navigation back to inventory page should take significant time (>2000ms) for performance_glitch_user, but took: " + duration + " ms");
        LoggerUtility.info("Verified significant delay in navigating back to inventory page after checkout");

        LoggerUtility.endTest("testCheckoutCompletionDelay");
    }
}