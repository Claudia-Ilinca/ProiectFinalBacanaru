package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying remove button behavior with problem_user.
 */
public class TestRemoveButtonDoesNotWork extends BaseTest {

    @Test
    public void testRemoveButtonDoesNotWork() {
        LoggerUtility.startTest("testRemoveButtonDoesNotWork");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addBackpackToCart();
        String initialCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(initialCount, "1", "Cart should have 1 item after adding");
        LoggerUtility.info("Added Backpack to cart, count: " + initialCount);

        inventoryPage.removeBackpackFromCart();
        String afterRemoveCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(afterRemoveCount, "1", "Cart count should not change after remove attempt");
        LoggerUtility.info("Remove button did not work, count remains: " + afterRemoveCount);

        LoggerUtility.endTest("testRemoveButtonDoesNotWork");
    }
}