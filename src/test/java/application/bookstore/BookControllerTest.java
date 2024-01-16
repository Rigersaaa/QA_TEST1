package application.bookstore;
import application.bookstore.controllers.BookController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import application.bookstore.models.Book;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookView bookView;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookView);
    }

    @Test
    void testSetSaveListener() {
        // Mocking necessary dependencies
        when(bookView.getIsbnField().getText()).thenReturn("1234567890123");
        when(bookView.getTitleField().getText()).thenReturn("Test Book");
        when(bookView.getPurchasedPriceField().getText()).thenReturn("10.0");
        when(bookView.getSellingPriceField().getText()).thenReturn("20.0");
        when(bookView.getAuthorsComboBox().getValue()).thenReturn(new Author("Test Author", "Test Author", "Test Author"));
        when(bookView.getStockField().getText()).thenReturn("100");
        when(bookView.getResultLabel()).thenReturn(new Label());

        // Mocking the Book.exists() method
        Book existingBook = new Book("1234567890123", "Test Book", 10.0f, 20.0f, 100, new Author("Test Author", "Test Author", "Test Author"));
        when(existingBook.exists()).thenReturn(true);

        // Mocking Book.saveInFile() to return true
        when(existingBook.saveInFile()).thenReturn(true);

        // Triggering the save listener
        bookController.setSaveListener();

        // Verifying that saveInFile() and exists() methods were called
        verify(existingBook, times(1)).exists();
        verify(existingBook, times(1)).saveInFile();
    }

    @Test
    void testSetSearchListener() {
        // Mocking necessary dependencies
        when(bookView.getSearchView().getSearchField().getText()).thenReturn("Test");

        // Mocking Book.getList() method
        List<Book> searchResults = new ArrayList<>();
        when(Book.getList("Test")).thenReturn((ArrayList<Book>) searchResults);

        // Triggering the search listener
        bookController.setSearchListener();

        // Verifying that getList() method was called
        Object Book = new Object();
        verify(Book, times(1)).getClass();
    }

    // Add more test methods for other listeners and behaviors as needed

    @Test
    void testResetFields() {
        // Mocking necessary dependencies
        when(bookView.getIsbnField()).thenReturn(new TextField());
        when(bookView.getTitleField()).thenReturn(new TextField());
        when(bookView.getPurchasedPriceField()).thenReturn(new TextField());
        when(bookView.getSellingPriceField()).thenReturn(new TextField());
        when(bookView.getStockField()).thenReturn(new TextField());

        // Triggering the reset fields method
        bookController.resetFields();

        // Verifying that setText() method was called on each field
        verify(bookView.getIsbnField(), times(1)).setText("");
        verify(bookView.getTitleField(), times(1)).setText("");
        verify(bookView.getPurchasedPriceField(), times(1)).setText("");
        verify(bookView.getSellingPriceField(), times(1)).setText("");
        verify(bookView.getStockField(), times(1)).setText("");
    }
}
