package Pages;

import Helpers.WebActions;
import Helpers.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    private final WebActions webActions;
    private final WebDriver driver;

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_img")
    private WebElement productImage;

    @FindBy(css = ".btn_inventory")
    private WebElement addToCartButton;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(css = ".btn_inventory.btn_secondary")
    private WebElement removeButton;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public String getProductName() {
        return webActions.getText(productName);
    }

    public String getProductImageSrc() {
        return webActions.getAttribute(productImage, "src");
    }

    public String getProductPrice() {
        return webActions.getText(productPrice);
    }

    public void addToCart() {
        webActions.click(addToCartButton);
    }

    public void goToCart() {
        webActions.click(cartLink);
    }

    public void goBackToInventory() {
        webActions.click(backToProductsButton);
    }

    public void removeFromCart() {
        webActions.click(removeButton);
    }

    public boolean isOnProductPage() {
        return driver.getCurrentUrl().contains("inventory-item.html");
    }
}