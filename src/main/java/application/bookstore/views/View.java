package application.bookstore.views;

import application.bookstore.models.User;
import javafx.scene.Parent;

public abstract class View {
    static private User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currUser) {
        currentUser = currUser;
    }

    public abstract Parent getView();
}
