package Tests.SqlTests;

import Logger.LoggerUtility;
import Tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GetBooksTest extends BaseTest {

    @Test(description = "Verifică obținerea cărților default")
    public void testGetDefaultBooks() throws SQLException {
        Set<String> expectedTitles = new HashSet<>();
        expectedTitles.add("The Hobbit");
        expectedTitles.add("1984");
        expectedTitles.add("To Kill a Mockingbird");

        LoggerUtility.info("Obținem toate cărțile din baza de date");
        ResultSet rs = bookTable.getBooks();
        Set<String> actualTitles = new HashSet<>();
        while (rs.next()) {
            actualTitles.add(rs.getString("title"));
        }

        Assert.assertTrue(actualTitles.containsAll(expectedTitles),
                "Nu toate cărțile default au fost găsite! Găsite: " + actualTitles);
        Assert.assertTrue(actualTitles.size() >= 3, "Numărul de cărți găsite este mai mic decât 3!");
        LoggerUtility.info("Toate cărțile default au fost găsite: " + actualTitles);
    }
}