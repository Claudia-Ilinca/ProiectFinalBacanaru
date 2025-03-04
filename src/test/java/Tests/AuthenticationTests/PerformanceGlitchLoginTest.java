package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import ObjectData.PerformanceGlitchUserData;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying login delay with performance_glitch_user.
 */
public class PerformanceGlitchLoginTest extends BaseTest {

    private final PerformanceGlitchUserData userData = new PerformanceGlitchUserData();

    @Test
    public void testPerformanceGlitchUserLogin() {
        LoggerUtility.startTest("testPerformanceGlitchUserLogin");

        LoginPage loginPage = new LoginPage(driver);

        long startTime = System.currentTimeMillis();
        loginPage.login(userData.getUsername(), userData.getPassword());
        long endTime = System.currentTimeMillis();
        long loginDuration = (endTime - startTime) / 1000;
        LoggerUtility.info("User attempted login with performance_glitch_user. Login took " + loginDuration + " seconds");

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed for performance_glitch_user");
        LoggerUtility.info("Login successful despite performance glitch");

        Assert.assertTrue(loginDuration > 2, "Login was not delayed as expected. Duration: " + loginDuration + " seconds");
        LoggerUtility.info("Login delay verified");

        LoggerUtility.endTest("testPerformanceGlitchUserLogin");
    }
}