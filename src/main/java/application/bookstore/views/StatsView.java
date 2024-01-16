package application.bookstore.views;

import application.bookstore.controllers.StatsController;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class StatsView extends View{
    private final VBox vBox = new VBox();


    public StatsView() {
        new StatsController(this);
    }

    public Parent getView2() {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<Order> orders = Order.getOrders();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(Order o : orders) {
            int match=0;
            for (PieChart.Data d: pieChartData) {
                if (o.getClientName().matches(d.getName())) {
                    d.setPieValue(d.getPieValue()+o.getTotal());
                    match=1;
                    break;
                }
            }
            if (match==0) {
                pieChartData.add(new PieChart.Data(o.getClientName(), o.getTotal()));
            }
        }
        final PieChart chart = new PieChart(pieChartData);
        // client -> nr of books..
        chart.setTitle("Book sale statistics");

        vBox.getChildren().addAll(chart);

        return vBox;
    }
    public Parent getView() {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Order.getOrders().clear();
        ArrayList<Order> orders = Order.getOrders();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();


        for(Order o : orders) {
            for (BookOrder b: o.getBooksOrdered()){
                int match = 0;
                for (PieChart.Data d: pieChartData) {
                    if (b.getTitle().matches(d.getName())) {
                        d.setPieValue(d.getPieValue() + b.getQuantity());
                        match = 1;
                        break;
                    }
                }
                if (match==0) {
                    pieChartData.add(new PieChart.Data(b.getTitle(), b.getQuantity()));
                }
            }
        }
        //books -> nr sold
        for (PieChart.Data d: pieChartData){
            System.out.println(d.getName() + " " + d.getPieValue());
        }
        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle("Order statistics");

        vBox.getChildren().addAll(chart);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("ClientName");
        yAxis.setLabel("Book Quantity");
        XYChart.Series<String,Number> series1 = new XYChart.Series<>();

        for(Order o : orders)
            for (BookOrder b : o.getBooksOrdered() ) {
                series1.getData().add(new XYChart.Data(o.getClientName(), b.getTotalQuantity(o.getClientName())));
        }
        bc.getData().add(series1);
        vBox.getChildren().add(bc);

        return vBox;
    }
}
