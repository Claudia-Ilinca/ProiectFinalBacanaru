package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying all products have the same image on inventory with problem_user.
 */
public class TestAllProductsHaveSameImageOnInventory extends BaseTest {

    @Test
    public void testAllProductsHaveSameImageOnInventory() {
        LoggerUtility.startTest("testAllProductsHaveSameImageOnInventory");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> imageSources = inventoryPage.getProductImageSources();
        LoggerUtility.info("Product image sources: " + imageSources);
        Assert.assertFalse(imageSources.isEmpty(), "No valid product images found");
        String expectedImage = "sl-404";
        for (String src : imageSources) {
            Assert.assertTrue(src.contains(expectedImage), "Product image should contain sl-404, but found: " + src);
        }
        LoggerUtility.info("All product images contain sl-404");

        LoggerUtility.endTest("testAllProductsHaveSameImageOnInventory");
    }
}