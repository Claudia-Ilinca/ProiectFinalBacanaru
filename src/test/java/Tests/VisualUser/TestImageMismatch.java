package Tests.VisualUser;

import Logger.LoggerUtility;
import ObjectData.VisualUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying image mismatch with visual_user.
 */
public class TestImageMismatch extends BaseTest {

    private final VisualUserData userData = new VisualUserData();

    @Test
    public void testFirstProductImageMismatch() {
        LoggerUtility.startTest("testFirstProductImageMismatch");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with visual_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        String inventoryImage = inventoryPage.getProductImageSources().get(0);
        LoggerUtility.info("First product image on inventory page: " + inventoryImage);
        Assert.assertTrue(inventoryImage.contains("sl-404"), "First product image on inventory should be incorrect (dog image), but found: " + inventoryImage);

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        String productPageImage = productPage.getProductImageSrc();
        LoggerUtility.info("Product image on individual page: " + productPageImage);
        Assert.assertFalse(productPageImage.contains("sl-404"), "Product image on individual page should be correct, but found: " + productPageImage);

        LoggerUtility.endTest("testFirstProductImageMismatch");
    }
}