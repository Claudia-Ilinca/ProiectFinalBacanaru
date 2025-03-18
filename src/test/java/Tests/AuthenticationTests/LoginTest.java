package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import Pages.LoginPage;
import Tests.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test(description = "Verifică login-ul cu succes folosind credențiale valide")
    public void testSuccessfulLogin() {
        LoggerUtility.startTest("testSuccessfulLogin");
        extentTest.log(Status.INFO, "Testul a început");

        LoginPage loginPage = new LoginPage(driver);
        extentTest.log(Status.INFO, "Pagina de login a fost inițializată");

        loginPage.login("standard_user", "secret_sauce");
        LoggerUtility.info("User attempted login with valid credentials");
        extentTest.log(Status.INFO, "Utilizatorul a introdus credențialele valide: standard_user / secret_sauce");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> loginPage.isLoginSuccessful());
        LoggerUtility.info("Login successful - redirected to inventory page");
        extentTest.log(Status.INFO, "Login-ul a fost realizat cu succes, utilizatorul a fost redirecționat la pagina de inventar");

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed - not redirected to inventory page");
        extentTest.log(Status.PASS, "Verificare trecută: Utilizatorul este pe pagina de inventar");

        LoggerUtility.endTest("testSuccessfulLogin");
    }

    @Test(description = "Verifică login-ul eșuat cu credențiale invalide")
    public void testFailedLogin() {
        LoggerUtility.startTest("testFailedLogin");
        extentTest.log(Status.INFO, "Testul a început");

        LoginPage loginPage = new LoginPage(driver);
        extentTest.log(Status.INFO, "Pagina de login a fost inițializată");

        loginPage.login("invalid_user", "wrong_password");
        LoggerUtility.info("User attempted login with invalid credentials");
        extentTest.log(Status.INFO, "Utilizatorul a introdus credențiale invalide: invalid_user / wrong_password");

        String error = loginPage.getErrorMessage();
        LoggerUtility.info("Login failed as expected - error message displayed");
        extentTest.log(Status.INFO, "Mesajul de eroare a fost afișat: " + error);

        Assert.assertTrue(error.contains("Username and password do not match"), "Error message not displayed");
        extentTest.log(Status.PASS, "Verificare trecută: Mesajul de eroare este corect");

        LoggerUtility.endTest("testFailedLogin");
    }
}