package main.Beans;

import main.DbConnection;

import java.sql.*;

public class Library {
    public Book book;
    public String libraryname;

    public Library(Book book,String libraryname){
        this.book = book;
        this.libraryname = libraryname;
    }

    public void display(){
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        String selectquery= "SELECT * FROM library";
        try(Connection connection = DbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectquery)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("Title:"+resultSet.getString("book_title"));
                System.out.println("Library name:"+resultSet.getString("library_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
