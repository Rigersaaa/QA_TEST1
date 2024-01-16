package application.bookstore.views;

import application.bookstore.controllers.MainController;
import application.bookstore.models.Role;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends View {
    private final MenuBar menuBar = new MenuBar();
    private final TabPane tabPane = new TabPane();

    private final Menu booksMenu= new Menu("Books");
    private final MenuItem viewBooksItem = new MenuItem("View Books");
    private final MenuItem viewAuthorsItem = new MenuItem("View Authors");

    private final Menu salesMenu= new Menu("Bill Item");
    private final MenuItem newBillItem = new MenuItem("Create new bill");

    private final Label manageUsers = new Label("Admin Site");
    private final Menu adminSite = new Menu("", manageUsers);

    private final MenuItem stats = new MenuItem("Order statistics");
    private final MenuItem stats2 = new MenuItem("Book statistics");

    private final Label logout = new Label("Logout");
    private final Menu logoutMenu = new Menu("", logout);

    public MainView(Stage mainStage){
        new MainController(this, mainStage);
    }


    @Override
    public Parent getView() {
        BorderPane borderPane = new BorderPane();

        booksMenu.getItems().addAll(viewBooksItem, viewAuthorsItem);
        salesMenu.getItems().add(newBillItem);
        menuBar.getMenus().addAll(booksMenu, salesMenu, logoutMenu);

        Tab tab = new Tab("Books");
        tab.setContent(new BookView().getView());

        Role currentRole = (getCurrentUser() != null ? getCurrentUser().getRole() : null);
        if (currentRole != null) {
            if (currentRole == Role.ADMIN) {
                menuBar.getMenus().add(adminSite);
            }
            if (currentRole == Role.MANAGER || currentRole == Role.ADMIN) {
                salesMenu.getItems().addAll(stats,stats2);
            }
            tabPane.getTabs().add(tab);
        }

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, tabPane);
        borderPane.setTop(vBox);

        StackPane stackPane = new StackPane();
        Text text = new Text("Hello " + getCurrentUser().getUsername() + ", welcome to the bookstore!");
        stackPane.getChildren().add(text);
        borderPane.setBottom(stackPane);
        return borderPane;
    }

    public MenuItem getViewBooks() {
        return viewBooksItem;
    }

    public MenuItem getViewAuthors() {
        return viewAuthorsItem;
    }

    public MenuItem getNewBill() {
        return newBillItem;
    }

    public Label getManageUsers() {
        return manageUsers;
    }

    public MenuItem getStatsMenu() {
        return stats;
    }
    public MenuItem getStats2Menu() {
        return stats2;
    }

    public Label getLogout() {
        return logout;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

}
