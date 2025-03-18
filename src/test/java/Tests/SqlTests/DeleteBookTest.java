package Tests.SqlTests;

import Logger.LoggerUtility;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteBookTest extends BaseTest {

    @Test(description = "Verifică ștergerea unei cărți")
    public void testDeleteBook() throws SQLException {
        String title = "Temp Book";
        String author = "Temp Author";
        String isbn = "999-999-999";

        // Adăugăm carte temporară
        LoggerUtility.info("Adăugăm o carte temporară: " + title);
        bookTable.insertBook(title, author, isbn);

        // Verificăm adăugarea
        ResultSet rs = bookTable.getBooks();
        boolean bookFoundBefore = false;
        while (rs.next()) {
            if (rs.getString("title").equals(title)) {
                bookFoundBefore = true;
                break;
            }
        }
        Assert.assertTrue(bookFoundBefore, "Cartea temporară nu a fost adăugată!");

        // Ștergem cartea
        LoggerUtility.info("Ștergem cartea: " + title);
        bookTable.deleteBook(title);

        // Verificăm ștergerea
        rs = bookTable.getBooks();
        boolean bookFoundAfter = false;
        while (rs.next()) {
            if (rs.getString("title").equals(title)) {
                bookFoundAfter = true;
                break;
            }
        }
        Assert.assertFalse(bookFoundAfter, "Cartea '" + title + "' nu a fost ștearsă!");
        LoggerUtility.info("Cartea '" + title + "' a fost ștearsă cu succes");
    }
}