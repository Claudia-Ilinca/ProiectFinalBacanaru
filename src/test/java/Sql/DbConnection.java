package Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance;
    private Connection connection;

    private DbConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test_library?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String password = "Claudia123";
        connection = DriverManager.getConnection(url, username, password);
    }

    public static synchronized DbConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}