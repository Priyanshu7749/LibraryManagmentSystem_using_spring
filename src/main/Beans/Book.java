package main.Beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class Book {
    private Author author;
    private String title;
    private double price;

    @Autowired
    public Book(Author author,String title,double price){
        this.author = author;
        this.title = title;
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title:"+title+","+"Price:"+price;
    }
}
