package Tests.StandardUser;

import Logger.LoggerUtility;
import ObjectData.StandardUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

/**
 * Test class for verifying inventory validation with standard_user.
 */
public class TestInventoryValidation extends BaseTest {

    private final StandardUserData userData = new StandardUserData();

    @Test
    public void testUniqueImagesAndTitles() {
        LoggerUtility.startTest("testUniqueImagesAndTitles");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> imageSources = inventoryPage.getProductImageSources();
        List<String> titles = inventoryPage.getProductNames();
        LoggerUtility.info("Product image sources: " + imageSources);
        LoggerUtility.info("Product titles: " + titles);

        Assert.assertEquals(imageSources.stream().distinct().count(), imageSources.size(), "Product images should be unique");
        Assert.assertEquals(titles.stream().distinct().count(), titles.size(), "Product titles should be unique");
        LoggerUtility.info("Validated all images and titles are unique");

        LoggerUtility.endTest("testUniqueImagesAndTitles");
    }

    @Test
    public void testFilterValidation() {
        LoggerUtility.startTest("testFilterValidation");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        inventoryPage.sortByNameAZ();
        List<String> namesAZ = inventoryPage.getProductNames();
        List<String> sortedNamesAZ = namesAZ.stream().sorted().toList();
        Assert.assertEquals(namesAZ, sortedNamesAZ, "Products should be sorted A to Z");
        LoggerUtility.info("Validated sort by Name (A to Z): " + namesAZ);

        inventoryPage.sortByNameZA();
        List<String> namesZA = inventoryPage.getProductNames();
        List<String> sortedNamesZA = namesAZ.stream().sorted(Comparator.reverseOrder()).toList();
        Assert.assertEquals(namesZA, sortedNamesZA, "Products should be sorted Z to A");
        LoggerUtility.info("Validated sort by Name (Z to A): " + namesZA);

        inventoryPage.sortByPriceLowToHigh();
        List<String> pricesLowToHigh = inventoryPage.getProductPrices();
        List<String> sortedPricesLowToHigh = pricesLowToHigh.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .sorted()
                .map(price -> "$" + price)
                .toList();
        Assert.assertEquals(pricesLowToHigh, sortedPricesLowToHigh, "Products should be sorted by price low to high");
        LoggerUtility.info("Validated sort by Price (Low to High): " + pricesLowToHigh);

        inventoryPage.sortByPriceHighToLow();
        List<String> pricesHighToLow = inventoryPage.getProductPrices();
        List<String> sortedPricesHighToLow = pricesHighToLow.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .sorted(Comparator.reverseOrder())
                .map(price -> "$" + price)
                .toList();
        Assert.assertEquals(pricesHighToLow, sortedPricesHighToLow, "Products should be sorted by price high to low");
        LoggerUtility.info("Validated sort by Price (High to Low): " + pricesHighToLow);

        LoggerUtility.endTest("testFilterValidation");
    }
}