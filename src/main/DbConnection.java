package main;

import main.Beans.Library;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.InputStream;
import java.util.Properties;


public class DbConnection {

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

    @Bean
    public DriverManagerDataSource source(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/librarymangment");
        dataSource.setUsername("postgres");
        dataSource.setPassword(getPassword());

        return dataSource;
    }

    @Bean
    public JdbcTemplate template(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(source());

        return jdbcTemplate;
    }
}