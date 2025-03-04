package Pages;

import Helpers.WebActions;
import Helpers.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AboutPage {

    private final WebActions webActions;
    private final WebDriver driver;

    @FindBy(css = ".error-message-container.error")
    private WebElement errorMessage;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean is404Displayed() {
        try {
            return webActions.isDisplayed(errorMessage) &&
                    webActions.getText(errorMessage).contains("404");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("404");
        }
    }

    public boolean isOnAboutPage() {
        return driver.getCurrentUrl().contains("saucelabs.com") || driver.getCurrentUrl().contains("about") ||
                driver.getCurrentUrl().contains("404");
    }
}