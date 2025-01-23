package main.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import main.DbConnection;

import java.sql.*;

public class Library {
    public Book book;
    public String libraryname;
    private Connection connection;

    public Library(Book book,String libraryname) {
        this.book = book;
        this.libraryname = libraryname;
    }
    @PostConstruct
    public void dbinitial(){
        try {
            connection = DbConnection.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void display(){
        String selectquery= "SELECT * FROM library";
        try(PreparedStatement preparedStatement = connection.prepareStatement(selectquery)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("Title:"+resultSet.getString("book_title"));
                System.out.println("Library name:"+resultSet.getString("library_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @PreDestroy
    public void cleanup() throws SQLException {
        connection.close();
    }

}
