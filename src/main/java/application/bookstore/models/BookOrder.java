package application.bookstore.models;

import java.io.Serial;
import java.io.Serializable;

public class BookOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private int quantity;
    private String isbn;
    private String title;
    private float price;
    private Author author;
    private int stock;
    private transient Book book;

    public BookOrder(int quantity, Book book) {

        setQuantity(quantity);
        setIsbn(book.getIsbn());
        setTitle(book.getTitle());
        setUnitPrice(book.getSellingPrice());
        setAuthor(book.getAuthor());
        this.stock = book.getStock();
        this.book=book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalQuantity(String clientName){
        int sum = 0;
        for(Order o : Order.getOrders()){
            for(BookOrder b : o.getBooksOrdered())
            if(o.getClientName().matches(clientName))
                sum += b.getQuantity();

        }
        return sum;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
    public int getStock(){
        return stock;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setUnitPrice(float price) {
        this.price = price;
    }

    public float getFullPrice() {
        return quantity * price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return String.format("%-7s %-20s OneBookPrice: %-7.2f Total: %.2f",quantity + " books ", title, price, getFullPrice());
    }
}
