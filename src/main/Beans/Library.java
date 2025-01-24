package main.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import main.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

//@Component
public class Library {
    public Book book;
    public String libraryname;
    private Connection connection;

    @Autowired
    public Library(Book book, String libraryname) {
        this.book = book;
        this.libraryname = libraryname;
    }

    @PostConstruct
    public void dbinitial() {
        try {
            connection = DbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Adddata() {

        String insert_query = "INSERT INTO library(author,book_title,book_price,library_name) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setString(1, book.getAuthor().getName());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setString(4, libraryname);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        String selectQuery = "SELECT * FROM library";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Title: " + resultSet.getString("book_title"));
                System.out.println("Library name: " + resultSet.getString("library_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeData(String title) {
        String deleteQuery = "DELETE FROM library WHERE book_title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, title);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Book removed");
            } else {
                System.out.println("Book not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAvailability(String title) {
        String selectQuery = "SELECT * FROM library WHERE book_title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Book is available.");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearBooksInLibrary(String libraryName) {
        String deleteQuery = "DELETE FROM library WHERE library_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, libraryName);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Cleared all books in the library.");
            } else {
                System.out.println("Library not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBookPrice(String title, double newPrice) {
        String updateQuery = "UPDATE library SET book_price = ? WHERE book_title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, title);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Book price updated.");
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findBooksByAuthor(String authorName) {
        String selectQuery = "SELECT * FROM library WHERE author = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, authorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Title: " + resultSet.getString("book_title"));
                System.out.println("Library name: " + resultSet.getString("library_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @PreDestroy
    public void cleanup() throws SQLException {
        connection.close();
    }

    @Override
    public String toString() {
        return libraryname;
    }
}
