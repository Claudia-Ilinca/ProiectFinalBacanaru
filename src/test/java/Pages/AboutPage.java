package Pages;

import Helpers.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutPage extends BasePage {

    @FindBy(css = ".error-message-container.error")
    private WebElement errorMessage;

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public boolean is404Displayed() {
        try {
            return webActions.isDisplayed(errorMessage) &&
                    webActions.getText(errorMessage).contains("404");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("404");
        }
    }

    @Override
    public boolean isOnPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("saucelabs.com") || currentUrl.contains("about") || currentUrl.contains("404");
    }
}