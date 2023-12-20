package application.bookstore.Test;
import application.bookstore.Main;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.View;
import org.testng.annotations.Test;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import org.junit.jupiter.api.*;

import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;

public class TestBookView {

    private static final Author testAuthor = new Author("testAuthor", "7");
    static User admin;

    @BeforeAll
    static void setUp() {

        Main.launchTest();
        admin = new User("admin", "admin", Role.ADMIN);

    }

    @Test
    void test_BookSavedCorrectly(){
        View.setCurrentUser(admin);
        BookView bookView = new BookView();
        Book book =  new Book("1234567891011", "Godfather1", 7, 77, 7, testAuthor);
        bookView.getIsbnField().setText(book.getIsbn());
        bookView.getTitleField().setText(book.getTitle());
        bookView.getPurchasedPriceField().setText(String.valueOf(book.getPurchasedPrice()));
        bookView.getSellingPriceField().setText(String.valueOf(book.getSellingPrice()));
        bookView.getStockField().setText(String.valueOf(book.getStock()));
        bookView.getSaveBtn().fire();

        assertEquals("Book was created!", bookView.getResultLabel().getText());
        Assertions.assertEquals(Book.getBooks().get(0), book);
        Assertions.assertEquals(BookView.getTableView().getItems().get(0), book);

    }

    @Test
    void test_BookNotSaved(){
        View.setCurrentUser(admin);
        BookView bookView = new BookView();
        Book book =  new Book("1234567891011", "Godfather1", -7, 77, 7, testAuthor);
        bookView.getIsbnField().setText(book.getIsbn());
        bookView.getTitleField().setText(book.getTitle());
        bookView.getPurchasedPriceField().setText(String.valueOf(book.getPurchasedPrice()));
        bookView.getSellingPriceField().setText(String.valueOf(book.getSellingPrice()));
        bookView.getStockField().setText(String.valueOf(book.getStock()));
        bookView.getSaveBtn().fire();

        assertEquals("Could not create book!", bookView.getResultLabel().getText());
        assertEquals(Book.getBooks().size(), 0);

    }

    @Test
    void test_SaveDuplicateBook(){

        View.setCurrentUser(admin);
        BookView bookView = new BookView();
        Book book =  new Book("1234567891011", "Godfather1", 7, 77, 7, testAuthor);
        bookView.getIsbnField().setText(book.getIsbn());
        bookView.getTitleField().setText(book.getTitle());
        bookView.getPurchasedPriceField().setText(String.valueOf(book.getPurchasedPrice()));
        bookView.getSellingPriceField().setText(String.valueOf(book.getSellingPrice()));
        bookView.getStockField().setText(String.valueOf(book.getStock()));
        bookView.getSaveBtn().fire();

        bookView.getIsbnField().setText(book.getIsbn());
        bookView.getTitleField().setText(book.getTitle());
        bookView.getPurchasedPriceField().setText(String.valueOf(book.getPurchasedPrice()));
        bookView.getSellingPriceField().setText(String.valueOf(book.getSellingPrice()));
        bookView.getStockField().setText(String.valueOf(book.getStock()));
        bookView.getSaveBtn().fire();

        assertEquals("Book already exists with the same Isbn!", bookView.getResultLabel().getText());
        assertEquals(Book.getBooks().size(), 1);
        assertEquals(BookView.getTableView().getItems().size(), 1);

    }

    @Test
    void test_BookIsDeleted() {
        Book book =  new Book("1234567891011", "Godfather1", 7, 77, 7, testAuthor);
        book.saveInFile();

        View.setCurrentUser(admin);
        BookView bookView = new BookView();
        BookView.getTableView().getSelectionModel().select(0);
        bookView.getDeleteBtn().fire();
        assertEquals("Book was removed!", bookView.getResultLabel().getText());
        assertEquals(BookView.getTableView().getItems().size(), 0);
    }

    @Test
    void test_FindBookByTitle(){
        Book book = new Book("4444444444444","RandomBook1", (float) 123.1, (float) 1234.1, 7, new Author("Author4", "Author4") );
        Book book2 = new Book("3333333333333","RandomBook2", (float) 123.1, (float) 1234.1, 7, new Author("Author3", "Author3") );
        Book book3 = new Book("5555555555555","Complexity", (float) 123.1, (float) 1234.1, 7, new Author("Author5", "Author5") );
        book.saveInFile();
        book2.saveInFile();
        book3.saveInFile();

        View.setCurrentUser(admin);
        BookView bookView = new BookView();

        bookView.getSearchView().getSearchField().setText("Rand");
        bookView.getSearchView().getSearchBtn().fire();
        assertEquals(Book.getList("Rand").stream().map(Book::getTitle).collect(Collectors.toList()), BookView.getTableView().getItems().stream().map(Book::getTitle).collect(Collectors.toList()));
    }

    @Test
    void test_FindBookByAuthor(){
        Book book = new Book("1234567891011","Book1", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book2 = new Book("1111111111111","Book2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book3 = new Book("2222222222222","Book3", (float) 123.1, (float) 1234.1, 7, new Author("random", "random") );
        book.saveInFile();
        book2.saveInFile();
        book3.saveInFile();

        View.setCurrentUser(admin);
        BookView bookView = new BookView();

        bookView.getSearchView().getSearchField().setText("TestA");
        bookView.getSearchView().getSearchBtn().fire();
        assertEquals(Book.getList("TestA").stream().map(Book::getAuthor).collect(Collectors.toList()), BookView.getTableView().getItems().stream().map(Book::getAuthor).collect(Collectors.toList()));

    }
}
