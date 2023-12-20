package application.bookstore.controllers;

import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.UserView;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private UserView userView;
    public UserController(UserView userView) {
        this.userView = userView;
        setUserCreateListener();
        setUserDeleteListener();
        setUserEditListener();
        setUserSearchListener();
    }

    private void setUserEditListener() {
        userView.getUsernameCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            String valueReplaced=userToEdit.getUsername();
            userToEdit.setUsername(event.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateInFile();
            }
            else {
                userToEdit.setUsername(valueReplaced);
                userView.getTableView().getItems().set(userView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                userView.getResultLabel().setText("Try new value!");
                userView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

        userView.getPasswordCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            String valueReplaced=userToEdit.getPassword();
            userToEdit.setPassword(event.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateInFile();
            }
            else {
                userToEdit.setPassword(valueReplaced);
                userView.getTableView().getItems().set(userView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                userView.getResultLabel().setText("Try new value!");
                userView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

        userView.getRoleCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            Role valueReplaced = userToEdit.getRole();
            userToEdit.setRole(event.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateInFile();
            }
            else {
                userToEdit.setRole(valueReplaced);
                userView.getTableView().getItems().set(userView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                userView.getResultLabel().setText("Try new value!");
                userView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });
    }

    private void setUserSearchListener() {
        userView.getSearchView().getClearBtn().setOnAction(event -> {
            userView.getSearchView().getSearchField().setText("");
            userView.getTableView().setItems(FXCollections.observableArrayList(User.getUsers()));
        });
        userView.getSearchView().getSearchBtn().setOnAction(event -> {
            String text = userView.getSearchView().getSearchField().getText();
            ArrayList<User> userList = User.getList(text);
            userView.getTableView().setItems(FXCollections.observableArrayList(userList));
        });
    }

    private void setUserDeleteListener() {
        userView.getDeleteBtn().setOnAction(event->{
            List<User> deleteUser = List.copyOf(userView.getTableView().getSelectionModel().getSelectedItems());
            for (User user: deleteUser) {
                if (user.deleteFromFile()) {
                    userView.getTableView().getItems().remove(user);
                    userView.getResultLabel().setText("User was removed!");
                    userView.getResultLabel().setTextFill(Color.DARKGREEN);
                } else {
                    userView.getResultLabel().setText("Could not delete user");
                    userView.getResultLabel().setTextFill(Color.DARKORANGE);
                    break;
                }
            }
        });
    }

    private void setUserCreateListener() {
        userView.getSaveBtn().setOnAction(e -> {
            String username = userView.getUserNameField().getText();
            String userPassword = userView.getPasswordField().getText();
            Role userRole = userView.getRoleComboBox().getValue();
            User user = new User(username, userPassword, userRole);

            if (!user.exists()) {
                if (user.saveInFile()) {
                    userView.getTableView().getItems().add(user);
                    userView.getResultLabel().setText("User was created!");
                    userView.getResultLabel().setTextFill(Color.DARKGREEN);
                    userView.getUsernameCol().setText("");
                    userView.getPasswordField().setText("");
                } else {
                    userView.getResultLabel().setText("User could not be created!");
                    userView.getResultLabel().setTextFill(Color.DARKGREEN);
                }
            }
            else {
                userView.getResultLabel().setText("User already exists!");
                userView.getResultLabel().setTextFill(Color.DARKORANGE);
            }
        });
    }

}
