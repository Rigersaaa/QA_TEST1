package application.bookstore.views;

import application.bookstore.ui.CreateButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends View {
    private final BorderPane borderPane = new BorderPane();

    private final TextField usernameField = new TextField();

    private final PasswordField passwordField = new PasswordField();
    private final Button loginBtn = new Button("Login");
    private final Label errorLabel = new Label("");
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }
    public Label getErrorLabel() {
        return errorLabel;
    }
    public LoginView() {
        setView();
    }

    private void setView() {
        Label usernameLabel = new Label("Username", usernameField);
        usernameLabel.setContentDisplay(ContentDisplay.RIGHT);
        Label passwordLabel = new Label("Password", passwordField);
        passwordLabel.setContentDisplay(ContentDisplay.RIGHT);

        usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD,14));
        passwordLabel.setFont(Font.font("Helvetica",FontWeight.BOLD,14));


        VBox vBox = new VBox();
        Label loginLabel = new Label("LOGIN");
        loginLabel.setFont(Font.font("Helvetica",FontWeight.BOLD,17));
        vBox.getChildren().addAll(loginLabel, usernameLabel, passwordLabel, loginBtn, errorLabel);

        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);
        Image img = new Image(String.valueOf(CreateButton.class.getResource("/images/pic.png")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(bImg));


        borderPane.setCenter(vBox);
    }

    @Override
    public Parent getView() {
        return borderPane;
    }
}
