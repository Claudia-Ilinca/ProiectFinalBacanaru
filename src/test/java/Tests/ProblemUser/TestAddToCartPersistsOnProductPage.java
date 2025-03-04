package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying add-to-cart persistence on product page with problem_user.
 */
public class TestAddToCartPersistsOnProductPage extends BaseTest {

    @Test
    public void testAddToCartPersistsOnProductPage() {
        LoggerUtility.startTest("testAddToCartPersistsOnProductPage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addBackpackToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), "1", "Cart should have 1 item");
        LoggerUtility.info("Added Backpack to cart from inventory");

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        LoggerUtility.info("Navigated to product page");

        productPage.addToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), "1", "Cart count should not increase due to defect");
        LoggerUtility.info("Add to cart button persists and does not increase count");

        LoggerUtility.endTest("testAddToCartPersistsOnProductPage");
    }
}