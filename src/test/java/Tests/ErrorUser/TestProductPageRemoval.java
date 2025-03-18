package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.ProductPage;
import Tests.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for verifying product removal behavior from product page with error_user.
 */
public class TestProductPageRemoval extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testRemoveFromProductPageAfterInventoryAdd() {
        LoggerUtility.startTest("testRemoveFromProductPageAfterInventoryAdd");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isInventoryLoaded = wait.until(d -> inventoryPage.isOnPage());
        Assert.assertTrue(isInventoryLoaded, "Not on inventory page after login");
        inventoryPage.addBackpackToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), "1", "Cart should have 1 item");
        LoggerUtility.info("Added Backpack to cart from inventory");

        inventoryPage.goToBackpackProductPage();
        ProductPage productPage = new ProductPage(driver);
        boolean isProductLoaded = wait.until(d -> productPage.isOnPage());
        Assert.assertTrue(isProductLoaded, "Not on product page");
        productPage.removeFromCart();
        LoggerUtility.info("Attempted to remove Backpack from product page");

        Assert.assertEquals(inventoryPage.getCartItemCount(), "1", "Cart count should not change after remove attempt");
        LoggerUtility.info("Remove button did not work from product page");

        LoggerUtility.endTest("testRemoveFromProductPageAfterInventoryAdd");
    }
}