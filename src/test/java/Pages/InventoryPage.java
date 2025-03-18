package Pages;

import Helpers.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackButton;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLightButton;

    @FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
    private WebElement addBoltTShirtButton;

    @FindBy(id = "add-to-cart-sauce-labs-fleece-jacket")
    private WebElement addFleeceJacketButton;

    @FindBy(id = "add-to-cart-sauce-labs-onesie")
    private WebElement addOnesieButton;

    @FindBy(id = "add-to-cart-test.allthethings()-t-shirt-(red)")
    private WebElement addRedTShirtButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeBackpackButton;

    @FindBy(css = ".inventory_item_img[src]")
    private List<WebElement> productImages;

    @FindBy(id = "item_4_title_link")
    private WebElement backpackLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".inventory_item_description .inventory_item_desc")
    private List<WebElement> productDescriptions;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) { // Corectat constructorul
        super(driver);
    }

    public void addBackpackToCart() {
        webActions.click(addBackpackButton);
    }

    public void addBikeLightToCart() {
        webActions.click(addBikeLightButton);
    }

    public void addBoltTShirtToCart() {
        webActions.click(addBoltTShirtButton);
    }

    public void addFleeceJacketToCart() {
        webActions.click(addFleeceJacketButton);
    }

    public void addOnesieToCart() {
        webActions.click(addOnesieButton);
    }

    public void addRedTShirtToCart() {
        webActions.click(addRedTShirtButton);
    }

    public void addAllProductsToCart() {
        addBackpackToCart();
        addBikeLightToCart();
        addBoltTShirtToCart();
        addFleeceJacketToCart();
        addOnesieToCart();
        addRedTShirtToCart();
    }

    public void removeBackpackFromCart() {
        webActions.click(removeBackpackButton);
    }

    public void goToBackpackProductPage() {
        webActions.click(backpackLink);
    }

    public void goToCart() {
        webActions.click(cartLink);
    }

    public List<String> getProductImageSources() {
        return productImages.stream()
                .map(img -> webActions.getAttribute(img, "src"))
                .filter(src -> src != null && !src.isEmpty())
                .toList();
    }

    public String getProductImageSrc() {
        return !productImages.isEmpty() && webActions.isDisplayed(productImages.get(0)) ?
                webActions.getAttribute(productImages.get(0), "src") : "";
    }

    public String getCartItemCount() {
        try {
            return webActions.getText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    public void sortByNameAZ() {
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByValue("az");
    }

    public void sortByNameZA() {
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByValue("za");
    }

    public void sortByPriceLowToHigh() {
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByValue("lohi");
    }

    public void sortByPriceHighToLow() {
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByValue("hilo");
    }

    public List<String> getProductNames() {
        return productNames.stream()
                .map(webActions::getText)
                .toList();
    }

    public List<String> getProductPrices() {
        return productPrices.stream()
                .map(webActions::getText)
                .toList();
    }

    public List<String> getProductDescriptions() {
        return productDescriptions.stream()
                .map(webActions::getText)
                .toList();
    }

    public void openMenu() {
        webActions.click(menuButton);
    }

    public void goToAboutPage() {
        openMenu();
        try {
            webActions.click(aboutLink);
        } catch (Exception e) {
            driver.get("https://saucelabs.com/");
        }
    }

    public void logout() {
        openMenu();
        webActions.click(logoutLink);
    }

    @Override
    public boolean isOnPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl != null && currentUrl.contains("inventory.html");
        } catch (WebDriverException e) {
            System.err.println("Driver unavailable in isOnInventoryPage: " + e.getMessage());
            return false;
        }
    }
}