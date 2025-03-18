package Tests.AuthenticationTests;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test(description = "Verifică funcționalitatea de logout după un login reușit")
    public void testLogout() {
        LoggerUtility.startTest("testLogout");
        extentTest.log(Status.INFO, "Testul a început");

        LoginPage loginPage = new LoginPage(driver);
        extentTest.log(Status.INFO, "Pagina de login a fost inițializată");

        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");
        extentTest.log(Status.INFO, "Utilizatorul s-a logat cu succes folosind standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page after login");
        LoggerUtility.info("User is on inventory page before logout, URL: " + driver.getCurrentUrl());
        extentTest.log(Status.INFO, "Utilizatorul este pe pagina de inventar înainte de logout, URL: " + driver.getCurrentUrl());

        inventoryPage.logout();
        LoggerUtility.info("Performed logout action, current URL: " + driver.getCurrentUrl());
        extentTest.log(Status.INFO, "Acțiunea de logout a fost realizată, URL curent: " + driver.getCurrentUrl());

        Assert.assertTrue(loginPage.isOnPage(), "Not on login page after logout");
        LoggerUtility.info("Logged out successfully, final URL: " + driver.getCurrentUrl());
        extentTest.log(Status.PASS, "Verificare trecută: Utilizatorul a fost redirecționat la pagina de login după logout");

        LoggerUtility.endTest("testLogout");
    }
}