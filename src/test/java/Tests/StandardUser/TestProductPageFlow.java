package Tests.StandardUser;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying product page flow with standard_user.
 */
public class TestProductPageFlow extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testAddFromProductPageAndNavigateBack() {
        LoggerUtility.startTest("testAddFromProductPageAndNavigateBack");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.goToBackpackProductPage();
        LoggerUtility.info("Navigated to product page");

        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        productPage.addToCart();
        LoggerUtility.info("Added Sauce Labs Backpack from product page");

        productPage.goBackToInventory();
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not navigated back to inventory page");
        LoggerUtility.info("Navigated back to inventory page");

        inventoryPage.addBikeLightToCart();
        LoggerUtility.info("Added Sauce Labs Bike Light from inventory page");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getItemCount(), 2, "Cart should have 2 items");
        LoggerUtility.info("Verified 2 items in cart");

        LoggerUtility.endTest("testAddFromProductPageAndNavigateBack");
    }

    @Test
    public void testRemoveFromProductPage() {
        LoggerUtility.startTest("testRemoveFromProductPage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToBackpackProductPage();
        LoggerUtility.info("Navigated to product page");

        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        productPage.removeFromCart();
        LoggerUtility.info("Removed Sauce Labs Backpack from product page");

        Assert.assertEquals(inventoryPage.getCartItemCount(), "0", "Cart should be empty");
        LoggerUtility.info("Verified cart is empty");

        LoggerUtility.endTest("testRemoveFromProductPage");
    }
}