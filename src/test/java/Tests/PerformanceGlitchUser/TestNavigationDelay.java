package Tests.PerformanceGlitchUser;

import Logger.LoggerUtility;
import ObjectData.PerformanceGlitchUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying navigation delay with performance_glitch_user.
 */
public class TestNavigationDelay extends BaseTest {

    private final PerformanceGlitchUserData userData = new PerformanceGlitchUserData();

    @Test
    public void testNavigationDelay() {
        LoggerUtility.startTest("testNavigationDelay");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with performance_glitch_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");

        long startTime = System.currentTimeMillis();
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        long endTime = System.currentTimeMillis();
        long durationToCart = endTime - startTime;
        LoggerUtility.info("Navigation to cart page completed, time taken: " + durationToCart + " ms");

        startTime = System.currentTimeMillis();
        cartPage.goBackToInventory();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
        endTime = System.currentTimeMillis();
        long durationBack = endTime - startTime;
        LoggerUtility.info("Navigation back to inventory page completed, time taken: " + durationBack + " ms");

        Assert.assertTrue(durationToCart > 2000 || durationBack > 2000,
                "Navigation should take significant time (>2000ms) for performance_glitch_user, but took: " +
                        durationToCart + " ms to cart and " + durationBack + " ms back");
        LoggerUtility.info("Verified significant delay in navigation");

        LoggerUtility.endTest("testNavigationDelay");
    }
}