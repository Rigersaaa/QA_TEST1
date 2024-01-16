module application.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens application.bookstore to javafx.fxml;
    opens application.bookstore.models to javafx.base;
    exports application.bookstore;
}