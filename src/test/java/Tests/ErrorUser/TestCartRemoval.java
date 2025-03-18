package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying cart removal behavior with error_user.
 */
public class TestCartRemoval extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testRemoveFromInventoryDoesNotWork() {
        LoggerUtility.startTest("testRemoveFromInventoryDoesNotWork");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addBackpackToCart();
        String initialCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(initialCount, "1", "Cart should have 1 item after adding");
        LoggerUtility.info("Added Backpack to cart, count: " + initialCount);

        inventoryPage.removeBackpackFromCart();
        String afterRemoveCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(afterRemoveCount, "1", "Cart count should not change after remove attempt");
        LoggerUtility.info("Remove button did not work, count remains: " + afterRemoveCount);

        LoggerUtility.endTest("testRemoveFromInventoryDoesNotWork");
    }
}