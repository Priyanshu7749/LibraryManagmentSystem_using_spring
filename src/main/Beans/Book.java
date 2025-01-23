package main.Beans;

public class Book {
    public Author author;
    public String title;
    public double price;

    public Book(Author author,String title,double price){
        this.author = author;
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Author:"+author+","+"Title:"+title+","+"Price:"+price;
    }
}
