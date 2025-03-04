package Helpers;

import Logger.LoggerUtility;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
public class ScreenshotUtility {

    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/target/screenshots/";

    public static File captureScreenshot(WebDriver driver, String testName) throws IOException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filePath = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(filePath);
        FileUtils.copyFile(screenshot, destination);

        LoggerUtility.info("Screenshot captured and saved at: " + filePath);
        return destination;
    }
}