package Tests.StandardUser;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying cart operations with standard_user.
 */
public class TestCartOperations extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testAddSingleProductToCart() {
        LoggerUtility.startTest("testAddSingleProductToCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");

        String cartCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(cartCount, "1", "Cart should have 1 item");
        LoggerUtility.info("Cart shows 1 item");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        Assert.assertEquals(cartPage.getFirstItemName(), "Sauce Labs Backpack", "Incorrect product in cart");
        LoggerUtility.info("Verified Sauce Labs Backpack in cart");

        LoggerUtility.endTest("testAddSingleProductToCart");
    }

    @Test
    public void testAddMultipleProductsToCart() {
        LoggerUtility.startTest("testAddMultipleProductsToCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");
        inventoryPage.addBikeLightToCart();
        LoggerUtility.info("Added Sauce Labs Bike Light to cart");
        inventoryPage.addBoltTShirtToCart();
        LoggerUtility.info("Added Sauce Labs Bolt T-Shirt to cart");

        String cartCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(cartCount, "3", "Cart should have 3 items");
        LoggerUtility.info("Cart shows 3 items");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        Assert.assertEquals(cartPage.getItemCount(), 3, "Cart should contain 3 items");
        LoggerUtility.info("Verified 3 items in cart");

        LoggerUtility.endTest("testAddMultipleProductsToCart");
    }

    @Test
    public void testRemoveProductFromCart() {
        LoggerUtility.startTest("testRemoveProductFromCart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        LoggerUtility.info("Added Sauce Labs Backpack to cart");
        Assert.assertEquals(inventoryPage.getCartItemCount(), "1", "Cart should have 1 item");

        inventoryPage.removeBackpackFromCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), "0", "Cart should be empty after removal");
        LoggerUtility.info("Removed Sauce Labs Backpack from inventory page");

        LoggerUtility.endTest("testRemoveProductFromCart");
    }
}