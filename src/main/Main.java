package main;

import main.Beans.Author;
import main.Beans.Book;
import main.Beans.Library;
import main.Configuration.JavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        Scanner scanner = new Scanner(System.in);

        Book book = context.getBean(Book.class);
        Library library = context.getBean(Library.class);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Insert Data");
            System.out.println("2. View Data");
            System.out.println("3. Remove Data");
            System.out.println("4. Check Book Availability");
            System.out.println("5. Clear All Books in a Library");
            System.out.println("6. Update Book Price");
            System.out.println("7. Find Books by Author");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Author Name: ");
                    String authorName = scanner.nextLine();

                    System.out.print("Enter Book Title: ");
                    String bookTitle = scanner.nextLine();

                    System.out.print("Enter Book Price: ");
                    double bookPrice = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter Library Name: ");
                    String libraryName = scanner.nextLine();

                    Author author = context.getBean(Author.class);
                    author.setName(authorName);

                    book = new Book(author, bookTitle, bookPrice);
                    library = new Library(book, libraryName);
                    library.initializeDB();
                    library.Adddata();
                    break;

                case 2:
                    library.display();
                    break;
                case 3:
                    System.out.print("Enter Book Title to Remove: ");
                    String titleToRemove = scanner.nextLine();
                    library.removeData(titleToRemove);
                    break;

                case 4:
                    System.out.print("Enter Book Title to Check: ");
                    String titleToCheck = scanner.nextLine();
                    library.checkAvailability(titleToCheck);
                    break;

                case 5:
                    System.out.print("Enter Library Name to Clear: ");
                    String libraryToClear = scanner.nextLine();
                    library.clearBooksInLibrary(libraryToClear);
                    break;

                case 6:
                    System.out.print("Enter Book Title to Update Price: ");
                    String titleToUpdate = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();
                    library.updateBookPrice(titleToUpdate, newPrice);
                    break;

                case 7:
                    System.out.print("Enter Author Name to Search: ");
                    String authorToSearch = scanner.nextLine();
                    library.findBooksByAuthor(authorToSearch);
                    break;

                case 8:
                    System.out.println("Exiting the program.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}