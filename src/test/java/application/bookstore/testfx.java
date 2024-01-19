package application.bookstore;

import application.bookstore.controllers.BookController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class testfx extends ApplicationTest {

    private BookView bookView;
    @Override
    public void start(Stage stage) {
        // Start the JavaFX application for testing
        new MyJavaFXApp().start(stage);
    }

    @Override
    public void stop() {
        FxToolkit.hideStage();
    }


    @Override
    public void start(Stage stage) {
        bookView = new BookView();
        BookController bookController = new BookController(bookView);
        Scene scene = new Scene(bookView.getView(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testBookCreation() {
        clickOn(bookView.getIsbnField()).write("123456789");
        clickOn(bookView.getTitleField()).write("Test Book");
        clickOn(bookView.getPurchasedPriceField()).write("10.0");
        clickOn(bookView.getSellingPriceField()).write("15.0");
        clickOn(bookView.getStockField()).write("20");

        // Simulate selecting an author in the ComboBox
        interact(() -> {
            bookView.getAuthorsComboBox().getItems().add(new Author("John"));
            bookView.getAuthorsComboBox().setValue(new Author("John"));
        });

        clickOn(bookView.getSaveBtn());

        // Assuming there is a label to display the result
        verifyThat("#resultLabel", hasText("Book was created!"));

        // Assuming there is a TableView to display books
        TableView<Book> tableView = BookView.getTableView();
        assertEquals(1, tableView.getItems().size());
    }

    // Add similar tests for other functionalities such as editing, deleting, and searching
}