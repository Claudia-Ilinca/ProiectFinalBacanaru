package Sql.Queries;

import Sql.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTable {
    private DbConnection dbConnection;

    public BookTable() throws SQLException {
        dbConnection = DbConnection.getInstance();
    }

    public void insertBook(String title, String author, String isbn) throws SQLException {
        String query = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
        PreparedStatement stm = dbConnection.getConnection().prepareStatement(query);
        stm.setString(1, title);
        stm.setString(2, author);
        stm.setString(3, isbn);
        stm.executeUpdate();
    }

    public ResultSet getBooks() throws SQLException {
        String query = "SELECT * FROM books";
        return dbConnection.getConnection().createStatement().executeQuery(query);
    }

    public void deleteBook(String title) throws SQLException {
        String query = "DELETE FROM books WHERE title = ?";
        PreparedStatement stm = dbConnection.getConnection().prepareStatement(query);
        stm.setString(1, title);
        stm.executeUpdate();
    }
}