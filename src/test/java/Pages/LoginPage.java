package Pages;

import Helpers.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        webActions.fill(usernameField, username);
        webActions.fill(passwordField, password);
        webActions.click(loginButton);
    }

    public String getErrorMessage() {
        return webActions.getText(errorMessage);
    }

    @Override
    public boolean isOnPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.equals("https://www.saucedemo.com/") || currentUrl.contains("index.html");
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    @Override
    public String getPageTitle() {
        return "Login - " + super.getPageTitle(); // Polimorfism prin suprascriere
    }
}