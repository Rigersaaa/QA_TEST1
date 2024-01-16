package application.bookstore.ui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RefreshButton extends  Button{
    public RefreshButton() {
        super.setText("Refresh");
        super.setGraphic(getImage());
        super.setPrefWidth(10);
        super.setPrefHeight(5);
    }

    private ImageView getImage() {
        ImageView imageView = new ImageView(String.valueOf(RefreshButton.class.getResource("/images/refresh_icon.png")));
        return imageView;
    }
}
