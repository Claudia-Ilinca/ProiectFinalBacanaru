package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.AboutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying 404 error on about page with problem_user.
 */
public class TestAboutPage404 extends BaseTest {

    @Test
    public void testAboutPage404() {
        LoggerUtility.startTest("testAboutPage404");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.goToAboutPage();
        AboutPage aboutPage = new AboutPage(driver);
        Assert.assertTrue(aboutPage.isOnAboutPage(), "Not on about page");
        LoggerUtility.info("Navigated to about page");

        Assert.assertTrue(aboutPage.is404Displayed(), "Expected 404 error on about page");
        LoggerUtility.info("404 error displayed as expected");

        LoggerUtility.endTest("testAboutPage404");
    }
}