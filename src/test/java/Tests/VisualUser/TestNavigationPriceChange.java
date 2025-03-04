package Tests.VisualUser;

import Logger.LoggerUtility;
import ObjectData.VisualUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying price changes after navigation with visual_user.
 */
public class TestNavigationPriceChange extends BaseTest {

    private final VisualUserData userData = new VisualUserData();

    @Test
    public void testPricesChangeAfterNavigation() {
        LoggerUtility.startTest("testPricesChangeAfterNavigation");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with visual_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        List<String> initialPrices = inventoryPage.getProductPrices();
        LoggerUtility.info("Initial prices on inventory page: " + initialPrices);

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        LoggerUtility.info("Navigated to product page");

        productPage.goBackToInventory();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
        List<String> pricesAfterProductPage = inventoryPage.getProductPrices();
        LoggerUtility.info("Prices after returning from product page: " + pricesAfterProductPage);

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        LoggerUtility.info("Navigated to cart page");

        cartPage.goBackToInventory();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
        List<String> pricesAfterCartPage = inventoryPage.getProductPrices();
        LoggerUtility.info("Prices after returning from cart page: " + pricesAfterCartPage);

        Assert.assertFalse(initialPrices.equals(pricesAfterProductPage), "Prices should change after navigating to product page and back");
        Assert.assertFalse(pricesAfterProductPage.equals(pricesAfterCartPage), "Prices should change after navigating to cart page and back");
        LoggerUtility.info("Verified prices change after navigation");

        LoggerUtility.endTest("testPricesChangeAfterNavigation");
    }
}