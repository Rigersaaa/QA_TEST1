package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public  class Order extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private String clientName;
    private String username;
    private String orderID;
    private ArrayList<BookOrder> booksOrdered;
    private String date;

    private static final ArrayList<Order> orders = new ArrayList<>();
    public static final String FILE_PATH = "C:\\Users\\pc\\Downloads\\Software-Test-Head\\Software-Test-Head\\src\\main\\resources\\data\\orders.ser";
    public static final File DATA_FILE = new File(FILE_PATH);

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter idFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");


    public Order(String username) {
        booksOrdered = new ArrayList<>();
        setUsername(username);
    }

    public void BillOrder(String clientName){
        setClientName(clientName);
        LocalDateTime time = LocalDateTime.now();
        setDate(dtf.format(time));
        setOrderID("Order_" +idFormatter.format(time));
        booksOrdered = (ArrayList<BookOrder>) booksOrdered.clone();
    }

    public static ArrayList<Order> getList(String text) {
        ArrayList<Order> ordersList = new ArrayList<>();
        for(Order order: getOrders())
            if (order.getClientName().toUpperCase().matches(".*" + text.toUpperCase() + ".*"))
                ordersList.add(order);
        return ordersList;
    }


    @Override
    public String toString() {
        String s = "Order: " + orderID + "\nDate: " + date + "\nClient: " + clientName+ "\nBooks Ordered: \n";
        for (BookOrder bookOrder: booksOrdered)
            s += bookOrder+ "\n";
            s += String.format("\n--*-*--*--*--\nTotal: %.2f", getTotal());
        return s;
    }

    public float getTotal(){
        float sum = 0;
        for(BookOrder bookOrder : booksOrdered)
            sum += bookOrder.getFullPrice();

        return sum;
    }
    public void printBill(){
        try {
            PrintWriter printWriter = new PrintWriter("bills/" + orderID + ".txt");
            printWriter.println(this);
            printWriter.close();
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

    public ArrayList<BookOrder> getBooksOrdered() {
        return booksOrdered;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setBooksOrdered(ArrayList<BookOrder> booksOrdered) {
        this.booksOrdered = booksOrdered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean saveInFile() {
        boolean saved = super.save(Order.DATA_FILE);
        if (saved)
            orders.add(this);
        return saved;
    }

    @Override
    public boolean updateInFile() {
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return clientName.length() > 0;
    }

    @Override
    public boolean deleteFromFile() {
        orders.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        }
        catch (Exception e){
            orders.add(this);
            e.getStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Order> getOrders() {
        if (orders.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Order temp = (Order) inputStream.readObject();
                    if (temp != null)
                        orders.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of orders file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return orders;
    }
}
