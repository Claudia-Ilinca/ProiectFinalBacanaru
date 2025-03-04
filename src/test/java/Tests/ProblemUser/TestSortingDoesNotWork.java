package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying sorting behavior with problem_user.
 */
public class TestSortingDoesNotWork extends BaseTest {

    @Test
    public void testSortingDoesNotWork() {
        LoggerUtility.startTest("testSortingDoesNotWork");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> initialNames = inventoryPage.getProductNames();
        LoggerUtility.info("Initial product names: " + initialNames);

        inventoryPage.sortByNameAZ();
        List<String> sortedNames = inventoryPage.getProductNames();
        Assert.assertEquals(sortedNames, initialNames, "Sorting by name should not work");
        LoggerUtility.info("Names after sort by A-Z: " + sortedNames);

        List<String> initialPrices = inventoryPage.getProductPrices();
        LoggerUtility.info("Initial product prices: " + initialPrices);

        inventoryPage.sortByPriceLowToHigh();
        List<String> sortedPrices = inventoryPage.getProductPrices();
        Assert.assertEquals(sortedPrices, initialPrices, "Sorting by price should not work");
        LoggerUtility.info("Prices after sort by low-to-high: " + sortedPrices);

        LoggerUtility.endTest("testSortingDoesNotWork");
    }
}