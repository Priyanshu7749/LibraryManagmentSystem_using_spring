package main;

import main.Beans.Library;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.InputStream;
import java.util.Properties;

public class DbConnection {

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Library.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in the classpath");
            }
            properties.load(input);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    private static String getProperty(String key) {
        Properties properties = loadProperties();
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("not found in config.properties");
        }
        return value;
    }

    @Bean
    public DriverManagerDataSource source() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(getProperty("db.url"));
        dataSource.setUsername(getProperty("db.username"));
        dataSource.setPassword(getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate template() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(source());

        return jdbcTemplate;
    }
}
