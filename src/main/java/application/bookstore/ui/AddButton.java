package application.bookstore.ui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class AddButton extends Button {
    public AddButton() {
        super.setText("Add");
        super.setGraphic(getImage());
        super.setTextFill(Color.WHITE);
        super.setStyle("-fx-background-color: blue");
    }

    private ImageView getImage() {
        ImageView imageView = new ImageView(String.valueOf(CreateButton.class.getResource("/images/save_icon.png")));
        return imageView;
    }
}
