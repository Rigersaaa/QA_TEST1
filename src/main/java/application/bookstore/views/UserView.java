package application.bookstore.views;

import application.bookstore.controllers.UserController;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserView extends View{
    private final BorderPane borderPane = new BorderPane();
    private final TableView<User> userTableView = new TableView<>();
    private final TextField userNameField = new TextField();
    private final TextField passwordField = new TextField();
    private final TableColumn<User, String> usernameCol = new TableColumn<>("Username");
    private final TableColumn<User, String> passwordCol = new TableColumn<>("Password");
    private final ComboBox<Role> roleComboBox = new ComboBox<>();
    private final TableColumn<User, Role> roleCol = new TableColumn<>("Role");
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();

    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a user");

    private final HBox hBox = new HBox();

    public ComboBox<Role> getRoleComboBox() {
        return roleComboBox;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TableColumn<User, String> getPasswordCol() {
        return passwordCol;
    }

    public TableColumn<User, Role> getRoleCol() {
        return roleCol;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public TableColumn<User, String> getUsernameCol() {
        return usernameCol;
    }


    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public TableView<User> getTableView() {
        return userTableView;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public UserView() {
        setTableView();
        setForm();
        new UserController(this);
    }

    @Override
    public Parent getView() {
        borderPane.setCenter(userTableView);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(7);
        vBox.getChildren().addAll(hBox, resultLabel);
        borderPane.setBottom(vBox);
        borderPane.setTop(searchView.getSearchPane());
        return borderPane;
    }

    private void setForm() {
        hBox.setPadding(new Insets(15));
        hBox.setAlignment(Pos.CENTER);

        hBox.setSpacing(15);

        Label usernameLabel = new Label("Username: ", userNameField);
        usernameLabel.setContentDisplay(ContentDisplay.TOP);

        Label passwordLabel = new Label("Password: ", passwordField);
        passwordLabel.setContentDisplay(ContentDisplay.TOP);

        Label roleLabel = new Label("Role", roleComboBox);
        roleComboBox.getItems().setAll(Role.ADMIN, Role.MANAGER, Role.LIBRARIAN);
        roleComboBox.setValue(Role.MANAGER);

        hBox.getChildren().addAll(usernameLabel, passwordLabel, roleLabel, saveBtn, deleteBtn);
    }

    private void setTableView() {
        userTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userTableView.setEditable(true);
        userTableView.setItems(FXCollections.observableArrayList(User.getUsers()));

        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password")
        );
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());

        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(Role.values()));
        userTableView.getColumns().addAll(usernameCol, passwordCol, roleCol);
    }
}
