package Tests.ProblemUser;

import Logger.LoggerUtility;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for verifying checkout field interference with problem_user.
 */
public class TestCheckoutFieldInterference extends BaseTest {

    @Test
    public void testCheckoutFieldInterference() {
        LoggerUtility.startTest("testCheckoutFieldInterference");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        LoggerUtility.info("Logged in with problem_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        LoggerUtility.info("Navigated to cart page");

        cartPage.goToCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Not on checkout page");
        LoggerUtility.info("Navigated to checkout page");

        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        String firstNameAfter = checkoutPage.getFirstNameValue();
        Assert.assertNotEquals(firstNameAfter, "John", "First name should be altered after filling second field");
        LoggerUtility.info("First name altered to: " + firstNameAfter);

        String postalCodeAfter = checkoutPage.getPostalCodeValue();
        Assert.assertEquals(postalCodeAfter, "12345", "Postal code should remain correct");
        LoggerUtility.info("Postal code remains: " + postalCodeAfter);

        LoggerUtility.endTest("testCheckoutFieldInterference");
    }
}