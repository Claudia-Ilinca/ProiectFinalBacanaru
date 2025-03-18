package Pages;

import Helpers.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

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
        super(driver);
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

    @Override
    public boolean isOnPage() {
        return driver.getCurrentUrl().contains("inventory-item.html");
    }

    @Override
    public String getPageTitle() {
        return "Product: " + getProductName(); // Polimorfism prin suprascriere
    }
}