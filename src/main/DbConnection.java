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
    public static String getPassword(){
        Properties properties = new Properties();
        try (InputStream input = Library.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            return properties.getProperty("db.password");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Connection getConnection() throws SQLException {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(url, username,password);
    }
}
