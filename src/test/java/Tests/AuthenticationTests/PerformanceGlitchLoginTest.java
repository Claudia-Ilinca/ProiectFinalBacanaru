package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import ObjectData.PerformanceGlitchUserData;
import Pages.LoginPage;
import Tests.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PerformanceGlitchLoginTest extends BaseTest {

    private final PerformanceGlitchUserData userData = new PerformanceGlitchUserData();

    @Test(description = "Verifică login-ul cu întârziere pentru performance_glitch_user")
    public void testPerformanceGlitchUserLogin() {
        LoggerUtility.startTest("testPerformanceGlitchUserLogin");
        extentTest.log(Status.INFO, "Testul a început");

        LoginPage loginPage = new LoginPage(driver);
        extentTest.log(Status.INFO, "Pagina de login a fost inițializată");

        long startTime = System.currentTimeMillis();
        loginPage.login(userData.getUsername(), userData.getPassword());
        long endTime = System.currentTimeMillis();
        long loginDuration = (endTime - startTime) / 1000;
        LoggerUtility.info("User attempted login with performance_glitch_user. Login took " + loginDuration + " seconds");
        extentTest.log(Status.INFO, "Utilizatorul a introdus credențiale pentru performance_glitch_user. Login-ul a durat " + loginDuration + " secunde");

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed for performance_glitch_user");
        LoggerUtility.info("Login successful despite performance glitch");
        extentTest.log(Status.INFO, "Login-ul a fost realizat cu succes în ciuda întârzierii");

        Assert.assertTrue(loginDuration > 2, "Login was not delayed as expected. Duration: " + loginDuration + " seconds");
        extentTest.log(Status.PASS, "Verificare trecută: Login-ul a fost întârziat (> 2 secunde), durata: " + loginDuration + " secunde");

        LoggerUtility.endTest("testPerformanceGlitchUserLogin");
    }
}