package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;

import java.io.*;
import java.util.ArrayList;

public class Book extends BaseModel implements Serializable{
    private String isbn;
    private String title;
    private float purchasedPrice;
    private float sellingPrice;
    private Author author;
    private int stock;
    private static final String FILE_PATH = "data/books.ser";
    private static final File DATA_FILE = new File(FILE_PATH);
    @Serial
    private static final long serialVersionUID = 1234567L;
    private static final ArrayList<Book> books = new ArrayList<>();

    public Book(String isbn, String title, float purchasedPrice, float sellingPrice,int stock, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.purchasedPrice = purchasedPrice;
        this.sellingPrice = sellingPrice;
        this.author = author;
        this.stock = stock;
    }

    public Book() {

    }

    public float getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(float purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
            this.sellingPrice = sellingPrice;
    }

    public String getIsbn() {
        return isbn;
    }
    public int getStock(){
        return stock;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean saveInFile() {
        boolean saved = super.save(DATA_FILE);
        if (saved && !this.exists())
            books.add(this);
        return saved;
    }

    public boolean isValid() {
        return isbn.matches("\\d{13}") && stock > 0 && !(sellingPrice < 0) && !(purchasedPrice < 0) && title.matches("[\\w\\s]+");
    }

    @Override
    public boolean deleteFromFile(){
        books.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, books);
        }
        catch (Exception e){
            books.add(this);
            e.getStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInFile(){
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, books);
        }
        catch (IOException e){
            e.getStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Book> getBooks() {
        if (books.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Book temp = (Book) inputStream.readObject();
                    if (temp != null)
                        books.add(temp);
                    else
                        break;
                }
                inputStream.close();
            }
            catch (EOFException e) {
                System.out.println("End of book file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return books;
    }
    public static ArrayList<Book> getList(String text) {
        ArrayList<Book> list = new ArrayList<>();
        for(Book book: getBooks()) {
            if (book.getTitle().toUpperCase().matches(".*" + text.toUpperCase() +".*")||book.getIsbn().matches(".*" + text + ".*")||book.getAuthor().getFullName().toUpperCase().matches(".*" + text.toUpperCase() + ".*"))
                list.add(book);

        }

        return list;
    }

    public boolean exists(){
        for (Book book: books) {
            if (book.getIsbn().equals(getIsbn()))
                return true;
        }
        return false;
    }

    public String toString(){
        return "Book Isbn: " + getIsbn() + "title: " + getTitle() + "purchasedPrice " + getPurchasedPrice() + "sellingPrice " + getSellingPrice() + " stock: " + getStock();
    }
}
