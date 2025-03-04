package Tests.VisualUser;

import Logger.LoggerUtility;
import ObjectData.VisualUserData;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for verifying description inconsistency with visual_user.
 */
public class TestDescriptionInconsistency extends BaseTest {

    private final VisualUserData userData = new VisualUserData();

    @Test
    public void testDescriptionInconsistency() {
        LoggerUtility.startTest("testDescriptionInconsistency");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with visual_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<List<String>> descriptions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> currentDescriptions = inventoryPage.getProductDescriptions();
            descriptions.add(currentDescriptions);
            LoggerUtility.info("Descriptions after attempt " + (i + 1) + ": " + currentDescriptions);

            if (i % 2 == 0 && i < 4) {
                inventoryPage.goToCart();
                CartPage cartPage = new CartPage(driver);
                Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
                cartPage.goBackToInventory();
                Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
            } else if (i < 4) {
                inventoryPage.sortByNameZA();
            }
        }

        boolean descriptionsChanged = false;
        for (int i = 0; i < descriptions.size() - 1; i++) {
            if (!descriptions.get(i).equals(descriptions.get(i + 1))) {
                descriptionsChanged = true;
                break;
            }
        }
        Assert.assertTrue(descriptionsChanged, "Descriptions should change at least once between navigations and sorts for visual_user");
        LoggerUtility.info("Verified description inconsistency: descriptions change unpredictably after multiple attempts");

        LoggerUtility.endTest("testDescriptionInconsistency");
    }
}