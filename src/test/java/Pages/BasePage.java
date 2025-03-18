package Pages;

import Helpers.WebActions;
import Helpers.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebActions webActions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    // Metodă abstractă pentru polimorfism
    public abstract boolean isOnPage();

    // Metodă comună, poate fi suprascrisă
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Metodă încapsulată pentru navigare
    protected void navigateTo(String url) {
        driver.get(url);
    }
}