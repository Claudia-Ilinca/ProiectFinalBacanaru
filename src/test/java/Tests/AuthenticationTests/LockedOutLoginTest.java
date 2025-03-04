package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying login behavior with a locked-out user.
 */
public class LockedOutLoginTest extends BaseTest {

    @Test
    public void testLockedOutUserLogin() {
        LoggerUtility.startTest("testLockedOutUserLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        LoggerUtility.info("User attempted login with locked_out_user");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out"),
                "Expected locked out error message not displayed");
        LoggerUtility.info("Login failed as expected - user is locked out");

        LoggerUtility.endTest("testLockedOutUserLogin");
    }
}