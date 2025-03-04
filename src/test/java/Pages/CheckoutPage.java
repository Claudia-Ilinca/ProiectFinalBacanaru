package Pages;

import Helpers.WebActions;
import Helpers.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    private final WebActions webActions;
    private final WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(css = ".complete-header")
    private WebElement completeHeader;

    @FindBy(css = ".error-message-container.error")
    private WebElement errorMessage;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        webActions.fill(firstNameField, firstName);
        webActions.fill(lastNameField, lastName);
        webActions.fill(postalCodeField, postalCode);
        webActions.click(continueButton);
    }

    public void fillFieldsOnly(String firstName, String lastName, String postalCode) {
        webActions.fill(firstNameField, firstName);
        webActions.fill(lastNameField, lastName);
        webActions.fill(postalCodeField, postalCode);
    }

    public void completeCheckout() {
        webActions.click(finishButton);
    }

    public void goBackHome() {
        webActions.click(backHomeButton);
    }

    public String getFirstNameValueBeforeContinue() {
        return webActions.getAttribute(firstNameField, "value");
    }

    public String getLastNameValueBeforeContinue() {
        return webActions.getAttribute(lastNameField, "value");
    }

    public String getPostalCodeValueBeforeContinue() {
        return webActions.getAttribute(postalCodeField, "value");
    }

    public String getFirstNameValue() {
        return webActions.getAttribute(firstNameField, "value");
    }

    public String getLastNameValue() {
        return webActions.getAttribute(lastNameField, "value");
    }

    public String getPostalCodeValue() {
        return webActions.getAttribute(postalCodeField, "value");
    }

    public boolean isErrorDisplayed() {
        try {
            return webActions.isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout-step-one.html");
    }

    public boolean isOnCheckoutStepTwo() {
        return driver.getCurrentUrl().contains("checkout-step-two.html");
    }

    public boolean isCheckoutComplete() {
        return driver.getCurrentUrl().contains("checkout-complete.html") &&
                webActions.isDisplayed(completeHeader);
    }
}