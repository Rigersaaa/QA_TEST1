package application.bookstore.controllers;

import application.bookstore.views.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;


public class MainController {
    private final MainView mainView;
    private final Stage stage;

    public MainController(MainView mainView, Stage stage) {
        this.mainView = mainView;
        this.stage = stage;
        setListener();

    }
    private Tab newTab(Tab tab){
        for(Tab tab1 : mainView.getTabPane().getTabs()){
            if(tab1.getText().equals(tab.getText())){
                return tab1;
            }
        }
        mainView.getTabPane().getTabs().add(tab);
        return tab;
    }

    private void setListener() {

        mainView.getViewAuthors().setOnAction(event-> {
            Tab authorTab = new Tab("Authors");
            authorTab.setContent(new AuthorView().getView());
            newTab(authorTab);
        });

        mainView.getViewBooks().setOnAction(event-> {
            Tab booksTab = new Tab("Books");
            booksTab.setContent(new BookView().getView());
            newTab(booksTab);
        });
        mainView.getNewBill().setOnAction(event->{
            Tab order = new Tab("New Order");
            order.setContent(new OrderView(mainView, order).getView());
            newTab(order);
        });
        mainView.getStatsMenu().setOnAction(event->{
            Tab stats = new Tab("Order stats");
            stats.setContent(new StatsView().getView());
            newTab(stats);
        });
        mainView.getStats2Menu().setOnAction(event->{
            Tab stats2 = new Tab("Book stats");
            stats2.setContent(new StatsView().getView2());
            newTab(stats2);
        });
        mainView.getManageUsers().setOnMouseClicked(event->{
            Tab users = new Tab("Users");
            users.setContent(new UserView().getView());
            newTab(users);
        });

        mainView.getLogout().setOnMouseClicked(event->{
            LoginView loginView = new LoginView();
            new LoginController(loginView, new MainView(stage), stage);
            Scene scene = new Scene(loginView.getView(), 600, 600);
            stage.setScene(scene);
        });
    }

}
