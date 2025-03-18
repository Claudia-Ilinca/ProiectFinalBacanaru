package Helpers;

import Logger.LoggerUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserFactory {

    public static WebDriver getBrowserDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        LoggerUtility.info("Initializing browser: " + browser);

        switch (browser) {
            case "chrome":
                return initializeChromeDriver();
            case "edge":
                return initializeEdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browser);
        }
    }

    private static WebDriver initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        boolean isCI = "true".equalsIgnoreCase(System.getenv("CI"));
        if (isCI) {
            options.addArguments("--headless"); // Rulează headless doar în CI
            LoggerUtility.info("Running in CI environment, enabling headless mode");
        } else {
            LoggerUtility.info("Running locally, using non-headless mode");
        }

        options.addArguments("--disable-gpu", "--no-sandbox", "--window-size=1920,1080");
        options.addArguments("--disable-extensions", "--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        try {
            return new ChromeDriver(options);
        } catch (Exception e) {
            LoggerUtility.error("Failed to initialize ChromeDriver: " + e.getMessage());
            throw e;
        }
    }

    private static WebDriver initializeEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-gpu", "--no-sandbox", "--window-size=1920,1080");
        return new EdgeDriver(options);
    }
}