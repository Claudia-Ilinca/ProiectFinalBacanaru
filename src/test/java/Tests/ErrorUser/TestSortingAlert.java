package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for verifying sorting alert behavior with error_user.
 */
public class TestSortingAlert extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testSortingTriggersAlert() {
        LoggerUtility.startTest("testSortingTriggersAlert");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.sortByNameZA();
        LoggerUtility.info("Attempted to sort by Name Z-A");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        LoggerUtility.info("Alert text: " + alertText);
        alert.accept();
        LoggerUtility.info("Accepted alert");
        Assert.assertEquals(alertText, "Sorting is broken! This error has been reported to Backtrace.", "Unexpected alert text");

        LoggerUtility.endTest("testSortingTriggersAlert");
    }
}