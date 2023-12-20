package application.bookstore.views;

import application.bookstore.controllers.OrderController;
import application.bookstore.models.Book;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.ui.CreateButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class OrderView extends View{
    private final BorderPane borderPane = new BorderPane();
    private final TableView<BookOrder> bookOrderTableView= new TableView<>();
    private final HBox formPane = new HBox();
    private final Order order;
    private final TextField nameField = new TextField();
    private final Button saveBtn = new CreateButton();
    private final Label total = new Label("0");
    private final Label totalLabel = new Label("Total: ", total);
    private final Label resultLabel = new Label("");

    private final TableColumn<BookOrder, Integer> quantityCol = new TableColumn<>("Quantity");
    private final TableColumn<BookOrder, String> isbnCol = new TableColumn<>("Isbn");
    private final TableColumn<BookOrder, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<BookOrder, Float> priceCol = new TableColumn<>("Price");
    private final TableColumn<BookOrder, Float> totalPriceCol = new TableColumn<>("Total Price");
    private final TableColumn<BookOrder, String> authorCol = new TableColumn<>("Author");
    private final TableColumn<BookOrder, Integer> stockCol = new TableColumn<>("Stock");

    private final TableView<Book> bookTableView = new TableView<>();
    private final TableColumn<Book, String> booksIsbnCol = new TableColumn<>("Isbn");
    private final TableColumn<Book, String> booksTitleCol = new TableColumn<>("Title");
    private final TableColumn<Book, Float> booksPurchasedPriceCol = new TableColumn<>("Purchased Price");
    private final TableColumn<Book, Float> booksSellingPriceCol = new TableColumn<>("Selling Price");
    private final TableColumn<Book, String> booksAuthorCol = new TableColumn<>("Author");
    private final TableColumn<Book, String> booksStockCol = new TableColumn<>("Stock");



    private Tab tab;
    private MainView mainView;

    public OrderView(MainView mainView, Tab tab) {

        this.mainView=mainView;
        this.tab=tab;
        order = new Order(getCurrentUser().getUsername());
        setForm();
        setTableView();
        setBookTableView();
        new OrderController(this);
    }

    private void setForm() {
        formPane.setPadding(new Insets(15));
        formPane.setAlignment(Pos.CENTER);

        formPane.setSpacing(15);

        Label clientName = new Label("Client name: ", nameField);
        clientName.setContentDisplay(ContentDisplay.TOP);
        totalLabel.setContentDisplay(ContentDisplay.RIGHT);
        formPane.getChildren().addAll(clientName, saveBtn, totalLabel);
    }

    private void setTableView() {
        bookOrderTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        bookOrderTableView.setEditable(true);
        bookOrderTableView.setItems(FXCollections.observableArrayList(order.getBooksOrdered()));

        quantityCol.setEditable(true);
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("Quantity")
        );
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        isbnCol.setEditable(false);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        // to edit the value inside the table view
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setEditable(false);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        priceCol.setEditable(false);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        totalPriceCol.setEditable(false);
        totalPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("fullPrice")
        );
        totalPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        authorCol.setEditable(false);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        stockCol.setEditable(true);
        stockCol.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        bookOrderTableView.getColumns().addAll(quantityCol,stockCol, isbnCol, titleCol, priceCol, totalPriceCol, authorCol);
    }

    private void setBookTableView() {
        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        bookTableView.setEditable(false);
        bookTableView.setItems(FXCollections.observableArrayList(Book.getBooks()));

        booksIsbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        booksStockCol.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        booksTitleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        booksTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        booksPurchasedPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("purchasedPrice")
        );
        booksPurchasedPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        booksSellingPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("sellingPrice")
        );
        booksSellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        booksAuthorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        bookTableView.getColumns().addAll(booksIsbnCol, booksStockCol,booksTitleCol, booksPurchasedPriceCol, booksSellingPriceCol, booksAuthorCol);
    }


    @Override
    public Parent getView() {

        HBox tables = new HBox();
        tables.setAlignment(Pos.CENTER);
        HBox.setHgrow(bookTableView, Priority.ALWAYS);
        HBox.setHgrow(bookOrderTableView, Priority.ALWAYS);
        tables.getChildren().add(bookTableView);
        tables.getChildren().add(bookOrderTableView);
        borderPane.setCenter(tables);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        borderPane.setBottom(vBox);

        return borderPane;
    }


    public TextField getNameField() {
        return nameField;
    }

    public TableView<BookOrder> getTableView() {
        return bookOrderTableView;
    }

    public TableView<Book> getBooksTableView() {
        return bookTableView;
    }

    public Label getTotal() {
        return total;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Order getOrder() {
        return order;
    }

    public TableColumn<BookOrder, Integer> getQuantityCol() {
        return quantityCol;
    }
    public TableColumn<BookOrder, Integer> getStockCol() {
        return stockCol;
    }

    public TableColumn<BookOrder, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<BookOrder, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<BookOrder, Float> getPriceCol() {
        return priceCol;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

}
