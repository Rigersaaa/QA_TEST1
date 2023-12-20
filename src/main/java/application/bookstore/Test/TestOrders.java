package application.bookstore.Test;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;

import static org.testng.Assert.*;

public class TestOrders {

    @Test
    void test_OrderSavedInFile() throws IOException, ClassNotFoundException {

        new PrintWriter("data/orders.ser").close(); //clear file
        Order.getOrders().clear();
        String username = "RedBaron";
        String clientName = "client1";
        Order order = new Order(username);

        Author author = new Author("authorname", "authorlastname");
        Author author2 = new Author("authorname2", "authorlastname2");

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, author );
        Book book2 = new Book("1234567891012","StarWars", (float) 123.2, (float) 1234.2, 77, author2 );
        Book book3 = new Book("1231231231237","Godfather1", (float) 123.2, (float) 1234.2, 4, author );

        books.add(book); books.add(book2); books.add(book3);

        BookOrder bookOrder = new BookOrder(3, books.get(0));
        BookOrder bookOrder2 = new BookOrder(7, books.get(1));

        ArrayList<BookOrder> listOfOrders = new ArrayList<>();
        listOfOrders.add(bookOrder); listOfOrders.add(bookOrder2);
        order.setBooksOrdered(listOfOrders);

        order.BillOrder(clientName);
        boolean orderIsSaved = order.saveInFile();

        assertTrue(orderIsSaved);
        assertEquals(Order.getOrders().size(), 1);

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/orders.ser"));
        Order orderFromFile = (Order)is.readObject();

        assertEquals(orderFromFile.toString(), order.toString());
//        System.out.println(orderIsSaved);
//        System.out.println(order);
//        System.out.println(Order.getOrders().size());


    }

    @Test

    void test_OrderNotSavedInFile(){
        Order order = new Order("username");
        order.BillOrder("client2");
        boolean isSaved = order.saveInFile() && order.getBooksOrdered().size() > 0;
        assertFalse(isSaved, "Need to choose at least one book for order.");

    }

    @Test
    void test_OrderIsDeleted() throws FileNotFoundException {
        new PrintWriter("data/orders.ser").close(); //clear file
        Order.getOrders().clear();
        String username = "RedBaron";
        String clientName = "client1";
        Order order = new Order(username);

        Author author = new Author("authorname", "authorlastname");
        Author author2 = new Author("authorname2", "authorlastname2");

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, author );
        Book book2 = new Book("1234567891012","StarWars", (float) 123.2, (float) 1234.2, 77, author2 );
        Book book3 = new Book("1231231231237","Godfather1", (float) 123.2, (float) 1234.2, 4, author );

        books.add(book); books.add(book2); books.add(book3);

        BookOrder bookOrder = new BookOrder(3, books.get(0));
        BookOrder bookOrder2 = new BookOrder(7, books.get(1));

        ArrayList<BookOrder> listOfOrders = new ArrayList<>();
        listOfOrders.add(bookOrder); listOfOrders.add(bookOrder2);
        order.setBooksOrdered(listOfOrders);

        order.BillOrder(clientName);
        order.saveInFile();

        assertEquals(Order.getOrders().size(), 1);
        order.deleteFromFile();
        assertEquals(Order.getOrders().size(), 0);

    }

    @Test

    void test_updateOrder() throws FileNotFoundException {
        new PrintWriter("data/orders.ser").close(); //clear file
        Order.getOrders().clear();
        String username = "RedBaron";
        String clientName = "client1";
        Order order = new Order(username);

        Author author = new Author("authorname", "authorlastname");
        Author author2 = new Author("authorname2", "authorlastname2");

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, author );
        Book book2 = new Book("1234567891012","StarWars", (float) 123.2, (float) 1234.2, 77, author2 );
        Book book3 = new Book("1231231231237","Godfather1", (float) 123.2, (float) 1234.2, 4, author );

        books.add(book); books.add(book2); books.add(book3);

        BookOrder bookOrder = new BookOrder(3, books.get(0));
        BookOrder bookOrder2 = new BookOrder(7, books.get(1));

        ArrayList<BookOrder> listOfOrders = new ArrayList<>();
        listOfOrders.add(bookOrder); listOfOrders.add(bookOrder2);
        order.setBooksOrdered(listOfOrders);

        order.BillOrder(clientName);
        order.saveInFile();

//        System.out.println(Order.getOrders().size());
//        System.out.println(Order.getOrders());

        BookOrder bookOrder3 = new BookOrder(2, books.get(0));
        BookOrder bookOrder4 = new BookOrder(5, books.get(2));

        ArrayList<BookOrder> listOfOrders2 = new ArrayList<>();
        listOfOrders2.add(bookOrder3); listOfOrders2.add(bookOrder4);

        order.setBooksOrdered(listOfOrders2);
        order.BillOrder(clientName);
        order.updateInFile();

//        System.out.println(Order.getOrders().size());
//        System.out.println(Order.getOrders());

        assertEquals(Order.getOrders().size(), 1);
        assertEquals(Order.getOrders().get(0).getTotal(), 8639.2001953125);
        assertEquals(Order.getOrders().get(0).getBooksOrdered().size(), 2);
        assertEquals(7, Order.getOrders().get(0).getBooksOrdered().get(0).getQuantity() + Order.getOrders().get(0).getBooksOrdered().get(1).getQuantity());
    }

    @Test

    void test_PrintOrderFile() throws FileNotFoundException {
        new PrintWriter("data/orders.ser").close(); //clear file
        Order.getOrders().clear();
        String username = "RedBaron";
        String clientName = "client1";
        Order order = new Order(username);

        Author author = new Author("authorname", "authorlastname");
        Author author2 = new Author("authorname2", "authorlastname2");

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, author );
        Book book2 = new Book("1234567891012","StarWars", (float) 123.2, (float) 1234.2, 77, author2 );
        Book book3 = new Book("1231231231237","Godfather1", (float) 123.2, (float) 1234.2, 4, author );

        books.add(book); books.add(book2); books.add(book3);

        BookOrder bookOrder = new BookOrder(3, books.get(0));
        BookOrder bookOrder2 = new BookOrder(7, books.get(1));

        ArrayList<BookOrder> listOfOrders = new ArrayList<>();
        listOfOrders.add(bookOrder); listOfOrders.add(bookOrder2);
        order.setBooksOrdered(listOfOrders);

        order.BillOrder(clientName);
        order.saveInFile();
        order.printBill();
//        System.out.println(order.getOrderID());

        String filepath = "bills/" +order.getOrderID() + ".txt";
//        System.out.println(filepath);
        assertTrue(new File(filepath).exists());

    }

}
