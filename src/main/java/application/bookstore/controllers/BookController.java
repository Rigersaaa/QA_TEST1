package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final BookView bookView;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setDefaultAuthor();
        setSearchListener();
    }
    private void setSaveListener() {
        bookView.getSaveBtn().setOnAction(event -> {
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();
            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            int stock = Integer.parseInt(bookView.getStockField().getText());
            Book book = new Book(isbn, title, purchasedPrice, sellingPrice,stock, author);

            if (!book.exists()){
                if (book.saveInFile()) {
                    BookView.getTableView().getItems().add(book);
                    bookView.getResultLabel().setText("Book was created!");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                    resetFields();
                } else {
                    bookView.getResultLabel().setText("Could not create book!");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                }

            }
                else {
                bookView.getResultLabel().setText("Book already exists with the same Isbn!");
                bookView.getResultLabel().setTextFill(Color.DARKORANGE);
            }

        });
    }
    private void setSearchListener() {
        bookView.getSearchView().getClearBtn().setOnAction(event -> {
            bookView.getSearchView().getSearchField().setText("");
            BookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
        });
        bookView.getSearchView().getSearchBtn().setOnAction(event -> {
            String text = bookView.getSearchView().getSearchField().getText();
            if(!text.isEmpty()) {
                ArrayList<Book> bookList = Book.getList(text);
                BookView.getTableView().setItems(FXCollections.observableArrayList(bookList));
            }
        });
    }

    private void setDefaultAuthor(){
        bookView.getAuthorsComboBox().setOnMouseClicked(event->{
            bookView.getAuthorsComboBox().getItems().setAll(Author.getAuthors());
            if (!Author.getAuthors().isEmpty())
                bookView.getAuthorsComboBox().setValue(Author.getAuthors().get(0));
        });

    }
    private void setDeleteListener(){
        bookView.getDeleteBtn().setOnAction(event->{
            List<Book> deleteBook = List.copyOf(BookView.getTableView().getSelectionModel().getSelectedItems());
            for (Book book: deleteBook) {
                if (book.deleteFromFile()) {
                    BookView.getTableView().getItems().remove(book);
                    bookView.getResultLabel().setText("Book was removed!");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                } else {
                    bookView.getResultLabel().setText("Could not delete book");
                    bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                    break;
                }
            }
        });
    }

    private void setEditListener() {
        bookView.getIsbnCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            String valueReplaced = bookToEdit.getIsbn();
            bookToEdit.setIsbn(event.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateInFile();
            }
            else {
                bookToEdit.setIsbn(valueReplaced);
                BookView.getTableView().getItems().set(BookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try a new value!");
                bookView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

        bookView.getTitleCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            String valueReplaced = bookToEdit.getTitle();
            bookToEdit.setTitle(event.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateInFile();
            }
            else {
                bookToEdit.setTitle(valueReplaced);
                BookView.getTableView().getItems().set(BookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try a new value!");
                bookView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

        bookView.getPurchasedPriceCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            float valueReplaced = bookToEdit.getPurchasedPrice();
            bookToEdit.setPurchasedPrice(event.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateInFile();
            }
            else {
                bookToEdit.setPurchasedPrice(valueReplaced);
                BookView.getTableView().getItems().set(BookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try a new value!");
                bookView.getResultLabel().setTextFill(Color.DARKORANGE);
            }
        });

        bookView.getSellingPriceCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            float valueReplaced = bookToEdit.getSellingPrice();
            bookToEdit.setSellingPrice(event.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateInFile();
            }
            else {
                bookToEdit.setSellingPrice(valueReplaced);
                BookView.getTableView().getItems().set(BookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try a new value!");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });
        bookView.getStockCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            int valueReplaced = bookToEdit.getStock();
            bookToEdit.setStock(event.getNewValue());
            if (bookToEdit.isValid()){
                bookToEdit.updateInFile();
            }
            else {
                bookToEdit.setStock(valueReplaced);
                BookView.getTableView().getItems().set(BookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                bookView.getResultLabel().setText("Try a new value!");
                bookView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });
    }

    private void resetFields() {
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
        bookView.getStockField().setText("");
    }
}
