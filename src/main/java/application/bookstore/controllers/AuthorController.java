package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.views.AuthorView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class AuthorController {
    private AuthorView authorView;
    public AuthorController(AuthorView authorView) {
        this.authorView = authorView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
    }

    private void setEditListener() {
        authorView.getFirstNameCol().setOnEditCommit(event -> {
            Author authorToEdit = event.getRowValue();
            String valueReplaced = authorToEdit.getFirstName();
            authorToEdit.setFirstName(event.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateInFile();
            }
            else {
                authorToEdit.setFirstName(valueReplaced);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Try a new value!");
                authorView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

        authorView.getLastNameCol().setOnEditCommit(event -> {
            Author authorToEdit = event.getRowValue();
            String valueReplaced = authorToEdit.getLastName();
            authorToEdit.setLastName(event.getNewValue());
            if (authorToEdit.isValid()){
                authorToEdit.updateInFile();
            }
            else {
                authorToEdit.setLastName(valueReplaced);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
                authorView.getResultLabel().setText("Try a new value!");
                authorView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });
    }

    private void setSearchListener() {
        authorView.getSearchView().getClearBtn().setOnAction(event -> {
            authorView.getSearchView().getSearchField().setText("");
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
        });
        authorView.getSearchView().getSearchBtn().setOnAction(event -> {
            String text = authorView.getSearchView().getSearchField().getText();
            ArrayList<Author> authorList = Author.getList(text);
            authorView.getTableView().setItems(FXCollections.observableArrayList(authorList));
        });
    }

    private void setDeleteListener() {
        authorView.getDeleteBtn().setOnAction(e->{
            List<Author> deleteAuthor = List.copyOf(authorView.getTableView().getSelectionModel().getSelectedItems());
            for (Author author: deleteAuthor) {
                //for(Book book: Book.getBooks())
                    if (author.deleteFromFile()) {
                        authorView.getTableView().getItems().remove(author);
                    //if(book.getAuthor().getFullName().matches(author.getFullName()))
                       // book.deleteFromFile();
                        //BookView.getTableView().getItems().remove(book);

                    authorView.getResultLabel().setText("Author was removed!");
                    authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                } else {
                    authorView.getResultLabel().setText("Could not delete author");
                    authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        authorView.getSaveBtn().setOnAction(e -> {
            String firstName = authorView.getFirstNameField().getText();
            String lastName = authorView.getLastNameField().getText();
            Author author = new Author(firstName, lastName);

                if (!author.exists()) {
                    if(author.saveInFile()) {
                        authorView.getTableView().getItems().add(author);
                        authorView.getResultLabel().setText("Author created successfully!");
                        authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                        authorView.getFirstNameField().setText("");
                        authorView.getLastNameField().setText("");
                    }
                } else {
                    authorView.getResultLabel().setText("Wrong value or author already exists!");
                    authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                }


        });
    }

}
