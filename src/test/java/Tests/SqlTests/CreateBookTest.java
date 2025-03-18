package Tests.SqlTests;

import Logger.LoggerUtility;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateBookTest extends BaseTest {

    @Test(description = "Verifică adăugarea unei cărți noi în baza de date")
    public void testCreateNewBook() throws SQLException {
        String title = "Dune";
        String author = "Frank Herbert";
        String isbn = "978-0-441-17271-9";

        // Adăugare carte (simulăm prin SQL; adaugă Selenium dacă ai pagină)
        LoggerUtility.info("Adăugăm cartea: " + title);
        bookTable.insertBook(title, author, isbn);

        // Verificare
        LoggerUtility.info("Verificăm prezența cărții în baza de date");
        ResultSet rs = bookTable.getBooks();
        boolean bookFound = false;
        while (rs.next()) {
            if (rs.getString("title").equals(title)) {
                bookFound = true;
                Assert.assertEquals(rs.getString("author"), author, "Autorul nu corespunde!");
                Assert.assertEquals(rs.getString("isbn"), isbn, "ISBN-ul nu corespunde!");
                break;
            }
        }
        Assert.assertTrue(bookFound, "Cartea '" + title + "' nu a fost găsită!");
        LoggerUtility.info("Cartea '" + title + "' a fost adăugată și verificată cu succes");
    }
}