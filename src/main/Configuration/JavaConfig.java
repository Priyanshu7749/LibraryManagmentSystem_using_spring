package main.Configuration;

import main.Beans.Author;
import main.Beans.Book;
import main.Beans.Library;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Scanner;

@Configuration
public class JavaConfig {
    //    Scanner scanner = new Scanner(System.in);
    @Bean
    @Scope("singleton")
    public Author author() {
        return new Author();
    }

    @Bean
    @Scope("prototype")
    public Book book(Author author) {
        return new Book(author,null,0.0);

    }

    @Bean
    @Scope("singleton")
    public Library library(Book book){
        return new Library(book,null);
    }
}
