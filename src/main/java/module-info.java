module application.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires org.testng;
    requires org.junit.jupiter.api;

    opens application.bookstore to javafx.fxml;
    opens application.bookstore.models to javafx.base;
    opens application.bookstore.Test to org.testng;

    exports application.bookstore;
    exports application.bookstore.Test to org.testng;
}