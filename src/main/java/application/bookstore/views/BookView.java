package application.bookstore.views;

import application.bookstore.controllers.BookController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.Role;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class BookView extends View{
    private final BorderPane borderPane = new BorderPane();
    private static final TableView<Book> tableView = new TableView<>();
    private final HBox formPane = new HBox();
    private final TextField isbnField = new TextField();
    private final TextField titleField = new TextField();
    private final TextField purchasedPriceField = new TextField();
    private final TextField sellingPriceField = new TextField();
    private final TextField stockField = new TextField();
    private final ComboBox<Author> authorsComboBox = new ComboBox<>();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<Book, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<Book, Float> purchasedPriceCol = new TableColumn<>("Purchased Price");
    private final TableColumn<Book, Float> sellingPriceCol = new TableColumn<>("Selling Price");
    private final TableColumn<Book, Integer> stockCol = new TableColumn<>("Stock");

    private final TableColumn<Book, String> authorCol = new TableColumn<>("Author");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a book");

    public static TableView<Book> getTableView() {
        return tableView;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getStockField() {
        return stockField;
    }


    public TextField getPurchasedPriceField() {
        return purchasedPriceField;
    }

    public TextField getSellingPriceField() {
        return sellingPriceField;
    }

    public ComboBox<Author> getAuthorsComboBox() {
        return authorsComboBox;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public TableColumn<Book, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<Book, String> getTitleCol() {
        return titleCol;
    }
    public TableColumn<Book, Integer> getStockCol() {
        return stockCol;
    }


    public TableColumn<Book, Float> getPurchasedPriceCol() {
        return purchasedPriceCol;
    }

    public TableColumn<Book, Float> getSellingPriceCol() {
        return sellingPriceCol;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public BookView() {
        setTableView();
        setForm();
        new BookController(this);
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);

        Label isbn = new Label("Isbn: ", isbnField);
        isbn.setContentDisplay(ContentDisplay.TOP);

        Label title = new Label("Title: ", titleField);
        title.setContentDisplay(ContentDisplay.TOP);

        Label purchasedPrice = new Label("Purchased price", purchasedPriceField);
        purchasedPrice.setContentDisplay(ContentDisplay.TOP);

        Label sellingPrice = new Label("Selling price", sellingPriceField);
        sellingPrice.setContentDisplay(ContentDisplay.TOP);

        Label stock = new Label("Stock: ", stockField);
        stock.setContentDisplay(ContentDisplay.TOP);

        Label author = new Label("Author", authorsComboBox);
        authorsComboBox.getItems().setAll(Author.getAuthors());

        if (!Author.getAuthors().isEmpty())
            authorsComboBox.setValue(Author.getAuthors().get(0));
        author.setContentDisplay(ContentDisplay.TOP);
        formPane.getChildren().addAll(isbn, title, purchasedPrice, sellingPrice,stock,
                                        author, saveBtn, deleteBtn);
    }

    private void setTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(Book.getBooks()));

        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        purchasedPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("purchasedPrice")
        );
        purchasedPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        sellingPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("sellingPrice")
        );
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        stockCol.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        tableView.getColumns().addAll(isbnCol, titleCol, purchasedPriceCol, sellingPriceCol,stockCol, authorCol);
    }


    @Override
    public Parent getView() {
        borderPane.setCenter(tableView);

        if ((super.getCurrentUser().getRole() == Role.ADMIN) || (super.getCurrentUser().getRole() == Role.MANAGER)) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(5);
            vBox.getChildren().addAll(formPane, resultLabel);
            borderPane.setBottom(vBox);
        }
        borderPane.setTop(searchView.getSearchPane());
        return borderPane;
    }
}
