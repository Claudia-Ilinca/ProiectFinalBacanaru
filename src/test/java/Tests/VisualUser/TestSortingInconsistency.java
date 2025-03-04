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
 * Test class for verifying sorting inconsistency with visual_user.
 */
public class TestSortingInconsistency extends BaseTest {

    private final VisualUserData userData = new VisualUserData();

    @Test
    public void testSortingInconsistency() {
        LoggerUtility.startTest("testSortingInconsistency");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with visual_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<List<String>> zaSortPrices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inventoryPage.sortByNameZA();
            List<String> prices = inventoryPage.getProductPrices();
            zaSortPrices.add(prices);
            LoggerUtility.info("Prices after sort Z-A attempt " + (i + 1) + ": " + prices);

            if (i < 2) {
                inventoryPage.goToCart();
                CartPage cartPage = new CartPage(driver);
                Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
                cartPage.goBackToInventory();
                Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
            }
        }

        boolean zaPricesChanged = false;
        for (int i = 0; i < zaSortPrices.size() - 1; i++) {
            if (!zaSortPrices.get(i).equals(zaSortPrices.get(i + 1))) {
                zaPricesChanged = true;
                break;
            }
        }
        Assert.assertTrue(zaPricesChanged, "Prices should change at least once between Z-A sorts with navigation for visual_user");

        List<List<String>> priceSortPrices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inventoryPage.sortByPriceHighToLow();
            List<String> prices = inventoryPage.getProductPrices();
            priceSortPrices.add(prices);
            LoggerUtility.info("Prices after sort Price High to Low attempt " + (i + 1) + ": " + prices);

            if (i < 2) {
                inventoryPage.goToCart();
                CartPage cartPage = new CartPage(driver);
                Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
                cartPage.goBackToInventory();
                Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not back on inventory page");
            }
        }

        boolean pricePricesChanged = false;
        for (int i = 0; i < priceSortPrices.size() - 1; i++) {
            if (!priceSortPrices.get(i).equals(priceSortPrices.get(i + 1))) {
                pricePricesChanged = true;
                break;
            }
        }
        Assert.assertTrue(pricePricesChanged, "Prices should change at least once between Price High to Low sorts with navigation for visual_user");

        LoggerUtility.info("Verified sorting inconsistency: prices change unpredictably after multiple sort attempts with navigation");
        LoggerUtility.endTest("testSortingInconsistency");
    }
}