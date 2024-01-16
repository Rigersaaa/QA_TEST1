package application.bookstore.controllers;

import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.views.OrderView;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class OrderController {
    private final OrderView orderView;

    public OrderController(OrderView orderView) {
        this.orderView = orderView;
        Order.getOrders();
        setEditBookOrderListener();
        setSelectBookOrderListener();
        setDeleteBookOrderListener();
        setCreateOrderListener();
    }
    private void setCreateOrderListener() {
        orderView.getSaveBtn().setOnMousePressed(e -> {

            orderView.getOrder().BillOrder(orderView.getNameField().getText());
            if (orderView.getOrder().saveInFile()) {
                orderView.getOrder().printBill();
                orderView.getResultLabel().setText("Order created!");
                orderView.getResultLabel().setTextFill(Color.DARKGREEN);

                for(BookOrder b: orderView.getOrder().getBooksOrdered()){
                    b.getBook().setStock(b.getBook().getStock() - b.getQuantity());
                    orderView.getBooksTableView().getItems().remove(b.getBook());
                    orderView.getBooksTableView().getItems().add(b.getBook());
                    b.getBook().updateInFile();

                }

                resetFields();
            }
            else {
                orderView.getResultLabel().setText("Enter full client name!");
                orderView.getResultLabel().setTextFill(Color.DARKORANGE);
            }
        });
    }

    private void setDeleteBookOrderListener(){
        orderView.getTableView().setOnMousePressed(e->{
            if (e.getClickCount() == 2) {
                BookOrder bookOrder = orderView.getTableView().getSelectionModel().getSelectedItem();
                orderView.getOrder().getBooksOrdered().remove(bookOrder);
                orderView.getBooksTableView().getItems().add(bookOrder.getBook());
                orderView.getTableView().getItems().remove(bookOrder);
                orderView.getTotal().setText(((Float)orderView.getOrder().getTotal()).toString());
            }
        });
    }

    private void setSelectBookOrderListener(){
        orderView.getBooksTableView().setOnMousePressed(event->{
                if (event.getClickCount() == 2) {
                    BookOrder bookOrder = new BookOrder(1, orderView.getBooksTableView().getSelectionModel().getSelectedItem());
                    orderView.getOrder().getBooksOrdered().add(bookOrder);
                    orderView.getBooksTableView().getItems().remove(orderView.getBooksTableView().getSelectionModel().getSelectedItem());
                    orderView.getTableView().getItems().add(bookOrder);
                    orderView.getTotal().setText(((Float)orderView.getOrder().getTotal()).toString());
                }
        });
    }

    private void setEditBookOrderListener() {
        orderView.getQuantityCol().setOnEditCommit(event -> {
            BookOrder bookOrderToEdit = event.getRowValue();
            int valueReplaced = bookOrderToEdit.getQuantity();
                if(event.getNewValue() < bookOrderToEdit.getStock()) {
                    bookOrderToEdit.setQuantity(event.getNewValue());
                }
                else {
                    orderView.getResultLabel().setText("Not enough books in the stock!");
                    orderView.getResultLabel().setTextFill(Color.DARKRED);
                    System.out.println("Stock " + bookOrderToEdit.getStock());
                    System.out.println("Value replaced " + valueReplaced);
                    System.out.println("Value  " + event.getNewValue());
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.show();
                }

            if (bookOrderToEdit.getQuantity() > 0){
                System.out.println(orderView.getOrder().getBooksOrdered());
                orderView.getTotal().setText(((Float)orderView.getOrder().getTotal()).toString());
            }
            else {
                bookOrderToEdit.setQuantity(valueReplaced);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(bookOrderToEdit), bookOrderToEdit);
                orderView.getResultLabel().setText("Try new value!");
                orderView.getResultLabel().setTextFill(Color.DARKGREEN);
            }
        });

    }
    private void resetFields() {
        orderView.getNameField().setText("");
    }
}
