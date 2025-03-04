package Tests;

import Helpers.BrowserFactory;
import Logger.LoggerUtility;
import Helpers.ScreenshotUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(ITestResult result) {
        try {
            driver = BrowserFactory.getBrowserDriver();
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");
            LoggerUtility.startTest(result.getMethod().getMethodName());
            LoggerUtility.info("Browser initialized and navigated to saucedemo.com");
        } catch (Exception e) {
            LoggerUtility.error("Failed to initialize browser: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            try {
                ScreenshotUtility.captureScreenshot(driver, result.getName());
            } catch (WebDriverException e) {
                LoggerUtility.error("Driver unavailable for screenshot: " + e.getMessage());
            } catch (IOException e) {
                LoggerUtility.error("Failed to capture screenshot: " + e.getMessage());
            }
        }
        if (driver != null) {
            try {
                driver.quit();
                LoggerUtility.info("Browser closed via WebDriver.quit()");
            } catch (WebDriverException e) {
                LoggerUtility.error("Failed to quit WebDriver: " + e.getMessage());
                forceCleanup();
            } finally {
                driver = null;
            }
        }
        LoggerUtility.endTest(result.getMethod().getMethodName());
    }

    @AfterSuite
    public void finishSuite() {
        forceCleanup();
        LoggerUtility.mergeLogsIntoOne();
    }

    private void forceCleanup() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            LoggerUtility.info("Forcefully terminated ChromeDriver processes");
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            LoggerUtility.error("Failed to force cleanup of ChromeDriver: " + e.getMessage());
        }
    }
}