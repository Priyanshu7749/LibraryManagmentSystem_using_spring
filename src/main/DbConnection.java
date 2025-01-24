package main;

import main.Beans.Library;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    public static final String url = "jdbc:postgresql://localhost:5432/librarymangment";
    public static final String username = "postgres";
    public static final String password = getPassword();

    public static String getPassword() {
        Properties properties = new Properties();
        try (InputStream input = Library.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in the classpath");
            }
            properties.load(input);
            String dbPassword = properties.getProperty("db.password");
            if (dbPassword == null) {
                throw new RuntimeException("Database password not found in config.properties");
            }
            return dbPassword;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found.", e);
        }

        if (password == null) {
            throw new SQLException("Database password is null");
        }

        return DriverManager.getConnection(url, username, password);
    }
}
