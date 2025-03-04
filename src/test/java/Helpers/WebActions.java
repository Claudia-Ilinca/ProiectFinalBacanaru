package Helpers;

import org.openqa.selenium.WebElement;

public interface WebActions {
    void click(WebElement element);
    void fill(WebElement element, String text);
    String getText(WebElement element);
    String getAttribute(WebElement element, String attribute);
    boolean isDisplayed(WebElement element);
}