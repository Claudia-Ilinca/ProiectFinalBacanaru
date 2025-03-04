package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying logout functionality.
 */
public class LogoutTest extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testLogout() {
        LoggerUtility.startTest("testLogout");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page after login");
        LoggerUtility.info("User is on inventory page before logout, URL: " + driver.getCurrentUrl());

        inventoryPage.logout();
        LoggerUtility.info("Performed logout action, current URL: " + driver.getCurrentUrl());

        Assert.assertTrue(loginPage.isOnLoginPage(), "Not on login page after logout");
        LoggerUtility.info("Logged out successfully, final URL: " + driver.getCurrentUrl());

        LoggerUtility.endTest("testLogout");
    }
}