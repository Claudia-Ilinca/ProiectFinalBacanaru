package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import Pages.LoginPage;
import Tests.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for verifying successful and failed login scenarios.
 */
public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        LoggerUtility.startTest("testSuccessfulLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        LoggerUtility.info("User attempted login with valid credentials");

        // Așteptăm explicit pagina de inventar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> loginPage.isLoginSuccessful());
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed - not redirected to inventory page");
        LoggerUtility.info("Login successful - redirected to inventory page");

        LoggerUtility.endTest("testSuccessfulLogin");
    }

    @Test
    public void testFailedLogin() {
        LoggerUtility.startTest("testFailedLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_password");
        LoggerUtility.info("User attempted login with invalid credentials");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"), "Error message not displayed");
        LoggerUtility.info("Login failed as expected - error message displayed");

        LoggerUtility.endTest("testFailedLogin");
    }
}