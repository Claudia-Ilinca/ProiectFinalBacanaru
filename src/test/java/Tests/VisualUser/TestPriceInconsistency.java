package Tests.VisualUser;

import Logger.LoggerUtility;
import ObjectData.VisualUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying price inconsistency with visual_user.
 */
public class TestPriceInconsistency extends BaseTest {

    private final VisualUserData userData = new VisualUserData();

    @Test
    public void testPriceInconsistency() {
        LoggerUtility.startTest("testPriceInconsistency");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with visual_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> inventoryPrices = inventoryPage.getProductPrices();
        LoggerUtility.info("Prices on inventory page: " + inventoryPrices);

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isOnProductPage(), "Not on product page");
        String correctPrice = productPage.getProductPrice();
        LoggerUtility.info("Correct price on individual page: " + correctPrice);

        Assert.assertFalse(inventoryPrices.get(0).equals(correctPrice), "First product price on inventory should differ from correct price, but matched: " + inventoryPrices.get(0));
        LoggerUtility.info("Verified inventory price differs from correct price");

        LoggerUtility.endTest("testPriceInconsistency");
    }
}