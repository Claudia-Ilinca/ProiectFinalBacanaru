package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying cart addition behavior with error_user.
 */
public class TestCartAdditions extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testOnlySpecificProductsAddToCart() {
        LoggerUtility.startTest("testOnlySpecificProductsAddToCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addAllProductsToCart();
        LoggerUtility.info("Attempted to add all products to cart");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        LoggerUtility.info("Navigated to cart page");

        int itemCount = cartPage.getItemCount();
        Assert.assertEquals(itemCount, 3, "Expected only 3 items in cart, but found: " + itemCount);
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "Backpack should be in cart");
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"), "Bike Light should be in cart");
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Onesie"), "Onesie should be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Bolt T-Shirt"), "Bolt T-Shirt should not be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Fleece Jacket"), "Fleece Jacket should not be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Test.allTheThings() T-Shirt (Red)"), "Red T-Shirt should not be in cart");
        LoggerUtility.info("Verified only 3 items are in cart");

        LoggerUtility.endTest("testOnlySpecificProductsAddToCart");
    }
}