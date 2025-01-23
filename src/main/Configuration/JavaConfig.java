package main.Configuration;

import main.Beans.Author;
import main.Beans.Book;
import main.Beans.Library;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JavaConfig {
    @Bean
    @Scope("singleton")
    public Author author1(){
        Author author = new Author();
        author.setName("yash");
        return author;
    }

    @Bean
    @Scope("prototype")
    public Book book1(){
        Book book1 = new Book(author1(),"Ai Innovation",299.9);

        return book1;
    }

    @Bean
    @Scope("singleton")
    public Library library1(){
        Library library = new Library(book1(),"Bantwa");

        return  library;
    }
}
