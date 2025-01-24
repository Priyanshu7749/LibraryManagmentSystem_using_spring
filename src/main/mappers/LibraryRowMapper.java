package main.mappers;

import main.Beans.Author;
import main.Beans.Book;
import main.Beans.Library;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRowMapper implements RowMapper<Library> {

    @Override
    public Library mapRow(ResultSet rs, int rowNum) throws SQLException {
        String author_name = rs.getString("author");
        Author author = new Author();
        author.setName(author_name);

        String book_title = rs.getString("book_title");
        Double book_price = rs.getDouble("book_price");
        Book book = new Book(author,book_title,book_price);

        String library_name = rs.getString("library_name");
        Library library = new Library(book,library_name);

        return library;
    }
}
