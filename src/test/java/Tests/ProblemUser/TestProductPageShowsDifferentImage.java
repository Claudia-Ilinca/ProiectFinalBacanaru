package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying different product image on product page with problem_user.
 */
public class TestProductPageShowsDifferentImage extends BaseTest {

    @Test
    public void testProductPageShowsDifferentImage() {
        LoggerUtility.startTest("testProductPageShowsDifferentImage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> inventoryImages = inventoryPage.getProductImageSources();
        String inventoryImage = inventoryImages.get(0);
        LoggerUtility.info("Inventory image: " + inventoryImage);
        Assert.assertTrue(inventoryImage.contains("sl-404"), "Inventory image should contain sl-404, but found: " + inventoryImage);

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        LoggerUtility.info("Navigated to product page");

        String productPageImage = productPage.getProductImageSrc();
        Assert.assertNotEquals(productPageImage, inventoryImage, "Product page image should differ from inventory image");
        LoggerUtility.info("Product page image: " + productPageImage);

        LoggerUtility.endTest("testProductPageShowsDifferentImage");
    }
}