package Pages;

import Helpers.WebActions;
import Helpers.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CartPage {

    private final WebActions webActions;
    private final WebDriver driver;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".cart_item_label img")
    private WebElement itemImage;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = ".cart_item .btn_inventory")
    private WebElement removeButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public int getItemCount() {
        return itemNames.size();
    }

    public String getFirstItemName() {
        return webActions.getText(itemNames.get(0));
    }

    public boolean isProductInCart(String productName) {
        for (WebElement item : itemNames) {
            if (webActions.getText(item).equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public String getFirstItemImageSrc() {
        try {
            return webActions.getAttribute(itemImage, "src");
        } catch (Exception e) {
            return "";
        }
    }

    public void goToCheckout() {
        webActions.click(checkoutButton);
    }

    public void goBackToInventory() {
        webActions.click(continueShoppingButton);
    }

    public void removeFirstItem() {
        webActions.click(removeButton);
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart.html");
    }
}