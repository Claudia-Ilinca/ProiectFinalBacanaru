package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibraryPage {
    private WebDriver driver;

    @FindBy(id = "title")
    private WebElement titleField;

    @FindBy(id = "author")
    private WebElement authorField;

    @FindBy(id = "isbn")
    private WebElement isbnField;

    @FindBy(id = "addBookButton")
    private WebElement addBookButton;

    public LibraryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addBook(String title, String author, String isbn) {
        titleField.sendKeys(title);
        authorField.sendKeys(author);
        isbnField.sendKeys(isbn);
        addBookButton.click();
    }
}