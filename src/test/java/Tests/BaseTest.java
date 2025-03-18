package Tests;

import Helpers.BrowserFactory;
import Logger.LoggerUtility;
import Helpers.ScreenshotUtility;
import Sql.Queries.BookTable;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BaseTest {

    protected WebDriver driver;
    protected BookTable bookTable;
    private static ExtentReports extent;
    protected ExtentTest extentTest;

    @BeforeSuite
    public void setUpSuite() {
        // Inițializare Extent Reports
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-reports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setReportName("Saucedemo E2E Test Report");
        spark.config().setDocumentTitle("Test Results - Final Project");
        LoggerUtility.info("Extent Reports initialized");
    }
// LOCAL
//    @BeforeMethod
//    public void setUp(ITestResult result) {
//        try {
//            driver = BrowserFactory.getBrowserDriver();
//            driver.manage().window().maximize();
//            driver.get("https://www.saucedemo.com/");
//            bookTable = new BookTable();
//            extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
//            LoggerUtility.startTest(result.getMethod().getMethodName());
//            LoggerUtility.info("Browser initialized and navigated to saucedemo.com, SQL connection established");
//            extentTest.log(Status.INFO, "Browser initialized and navigated to saucedemo.com");
//        } catch (SQLException e) {
//            LoggerUtility.error("Failed to initialize SQL connection: " + e.getMessage());
//            if (extentTest != null) {
//                extentTest.log(Status.FAIL, "Failed to initialize SQL connection: " + e.getMessage());
//            }
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            LoggerUtility.error("Failed to initialize browser: " + e.getMessage());
//            if (extentTest != null) {
//                extentTest.log(Status.FAIL, "Failed to initialize browser: " + e.getMessage());
//            }
//            throw e;
//        }
//    }
// CI CD
    @BeforeMethod
    public void setUp(ITestResult result) {
        try {
            driver = BrowserFactory.getBrowserDriver();
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");
            // bookTable = new BookTable();
            extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
            LoggerUtility.startTest(result.getMethod().getMethodName());
            LoggerUtility.info("Browser initialized and navigated to saucedemo.com");
            extentTest.log(Status.INFO, "Browser initialized and navigated to saucedemo.com");
        } catch (Exception e) {
            LoggerUtility.error("Failed to initialize browser: " + e.getMessage());
            if (extentTest != null) {
                extentTest.log(Status.FAIL, "Failed to initialize browser: " + e.getMessage());
            }
            throw e;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            try {
                File screenshot = ScreenshotUtility.captureScreenshot(driver, result.getName());
                String screenshotPath = screenshot.getAbsolutePath();
                LoggerUtility.error("Test failed, screenshot captured: " + screenshotPath);
                extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
                extentTest.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            } catch (WebDriverException e) {
                LoggerUtility.error("Driver unavailable for screenshot: " + e.getMessage());
                extentTest.log(Status.WARNING, "Driver unavailable for screenshot: " + e.getMessage());
            } catch (IOException e) {
                LoggerUtility.error("Failed to capture screenshot: " + e.getMessage());
                extentTest.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "Test passed successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test skipped");
        }

        if (driver != null) {
            try {
                driver.quit();
                LoggerUtility.info("Browser closed via WebDriver.quit()");
                extentTest.log(Status.INFO, "Browser closed");
            } catch (WebDriverException e) {
                LoggerUtility.error("Failed to quit WebDriver: " + e.getMessage());
                extentTest.log(Status.WARNING, "Failed to quit WebDriver: " + e.getMessage());
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
        extent.flush(); // Salvează raportul
        LoggerUtility.info("Extent Reports flushed and saved to target/extent-reports/ExtentReport.html");
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