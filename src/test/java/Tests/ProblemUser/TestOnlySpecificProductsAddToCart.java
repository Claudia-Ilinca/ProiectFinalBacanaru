package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying specific products addition to cart with problem_user.
 */
public class TestOnlySpecificProductsAddToCart extends BaseTest {

    @Test
    public void testOnlySpecificProductsAddToCart() {
        LoggerUtility.startTest("testOnlySpecificProductsAddToCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addAllProductsToCart();
        LoggerUtility.info("Attempted to add all products to cart");

        CartPage cartPage = new CartPage(driver);
        inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        LoggerUtility.info("Navigated to cart page");

        int itemCount = cartPage.getItemCount();
        Assert.assertEquals(itemCount, 3, "Expected only 3 items (1, 2, 5) in cart, but found: " + itemCount);
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "Backpack should be in cart");
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"), "Bike Light should be in cart");
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Onesie"), "Onesie should be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Bolt T-Shirt"), "Bolt T-Shirt should not be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Fleece Jacket"), "Fleece Jacket should not be in cart");
        Assert.assertFalse(cartPage.isProductInCart("Test.allTheThings() T-Shirt (Red)"), "Red T-Shirt should not be in cart");
        LoggerUtility.info("Verified only products 1, 2, and 5 are in cart");

        LoggerUtility.endTest("testOnlySpecificProductsAddToCart");
    }
}