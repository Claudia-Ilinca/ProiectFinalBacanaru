package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import Pages.LoginPage;
import Tests.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LockedOutLoginTest extends BaseTest {

    @Test(description = "Verifică login-ul cu un utilizator blocat")
    public void testLockedOutUserLogin() {
        LoggerUtility.startTest("testLockedOutUserLogin");
        extentTest.log(Status.INFO, "Testul a început");

        LoginPage loginPage = new LoginPage(driver);
        extentTest.log(Status.INFO, "Pagina de login a fost inițializată");

        loginPage.login("locked_out_user", "secret_sauce");
        LoggerUtility.info("User attempted login with locked_out_user");
        extentTest.log(Status.INFO, "Utilizatorul a introdus credențiale pentru locked_out_user");

        String error = loginPage.getErrorMessage();
        LoggerUtility.info("Login failed as expected - user is locked out");
        extentTest.log(Status.INFO, "Mesajul de eroare a fost afișat: " + error);

        Assert.assertTrue(error.contains("Sorry, this user has been locked out"),
                "Expected locked out error message not displayed");
        extentTest.log(Status.PASS, "Verificare trecută: Mesajul de eroare pentru utilizator blocat este corect");

        LoggerUtility.endTest("testLockedOutUserLogin");
    }
}