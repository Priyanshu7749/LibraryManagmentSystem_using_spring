package main.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import main.DbConnection;
import main.mappers.LibraryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;

public class Library {
    public Book book;
    public String libraryname;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public Library(Book book, String libraryname) {
        this.book = book;
        this.libraryname = libraryname;
    }

    @PostConstruct
    public void dbinitial() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbConnection.class);
        jdbcTemplate = context.getBean(JdbcTemplate.class);
    }


    public void Adddata() {

        String insert_query = "INSERT INTO library(author,book_title,book_price,library_name) VALUES(?,?,?,?)";

        int insert_result = jdbcTemplate.update(insert_query,book.getAuthor().name,book.getTitle(),book.getPrice(),libraryname);
        if (insert_result > 0) {
            System.out.println("Data inserted successfully.");
        }
        else{
            System.out.println("Data not inserted.");
        }
    }

    public void display() {
        String selectQuery = "SELECT * FROM library";
        List<Library> libraryList = jdbcTemplate.query(selectQuery,new LibraryRowMapper());
        for(Library library : libraryList){
            System.out.println("Book Title:"+library.book.getTitle());
            System.out.println("Library Name:"+library.libraryname);
        }
    }

    public void removeData(String title) {
        String deleteQuery = "DELETE FROM library WHERE book_title = ?";

        int delete_result = jdbcTemplate.update(deleteQuery,title);
        if (delete_result > 0) {
            System.out.println("Data inserted successfully.");
        }
        else{
            System.out.println("Data not inserted.");
        }
    }

    public void checkAvailability(String title) {
        String selectQuery = "SELECT * FROM library WHERE book_title = ?";
        List<Library> checkavailable = jdbcTemplate.query(selectQuery, new LibraryRowMapper(), title);

        if (checkavailable.isEmpty()) {
            System.out.println("No books found for the title: " + title);
        } else {
            System.out.println("Book found.");
        }
    }


    public void clearBooksInLibrary(String libraryName) {
        String clearbookquery = "DELETE FROM library WHERE library_name = ?";
        int clearbook_result = jdbcTemplate.update(clearbookquery,libraryName);
        if (clearbook_result > 0) {
            System.out.println("Library cleared.");
        }
        else{
            System.out.println("no library found.");
        }
    }

    public void updateBookPrice(String title, double newPrice) {
        String updateQuery = "UPDATE library SET book_price = ? WHERE book_title = ?";
        int update_result = jdbcTemplate.update(updateQuery,newPrice,title);
        if (update_result > 0) {
            System.out.println("book price updated.");
        }
        else{
            System.out.println("book not found.");
        }
    }

    public void findBooksByAuthor(String authorName) {
        String selectQuery = "SELECT * FROM library WHERE author = ?";

        List<Library> books = jdbcTemplate.query(selectQuery, new LibraryRowMapper(), authorName);

        if (books.isEmpty()) {
            System.out.println("No books found for the author: " + authorName);
        } else {
            System.out.println("Books by " + authorName + ":");
            for (Library library : books) {
                System.out.println("Book Title: " + library.book.getTitle());
                System.out.println("Library Name: " + library.libraryname);
            }
        }
    }


    @PreDestroy
    public void cleanup() throws SQLException {
    }

    @Override
    public String toString() {
        return libraryname;
    }
}
