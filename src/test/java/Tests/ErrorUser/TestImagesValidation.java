package Tests.ErrorUser;

import Logger.LoggerUtility;
import ObjectData.ErrorUserData;
import Pages.InventoryPage;
import Pages.LoginPage;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for verifying product image consistency with error_user.
 */
public class TestImagesValidation extends BaseTest {

    private final ErrorUserData userData = new ErrorUserData();

    @Test
    public void testImagesAreCorrect() {
        LoggerUtility.startTest("testImagesAreCorrect");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userData.getUsername(), userData.getPassword());
        LoggerUtility.info("Logged in with error_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Not on inventory page");
        LoggerUtility.info("User is on inventory page");

        List<String> imageSources = inventoryPage.getProductImageSources();
        LoggerUtility.info("Product image sources: " + imageSources);
        Assert.assertEquals(imageSources.size(), 6, "Expected 6 product images");
        for (String src : imageSources) {
            Assert.assertFalse(src.contains("sl-404"), "Image should not be a placeholder: " + src);
        }
        LoggerUtility.info("Verified all images are correct");

        LoggerUtility.endTest("testImagesAreCorrect");
    }
}