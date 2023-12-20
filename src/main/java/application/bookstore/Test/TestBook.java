package application.bookstore.Test;
import org.testng.annotations.Test;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class TestBook {

    private static final Author testAuthor = new Author("testAuthor", "7");


    @Test
    void test_BookSavedInFile() throws IOException, ClassNotFoundException {
        Book book = new Book("1231231231231","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
//        System.out.println(testAuthor);
//        System.out.println(book.saveInFile());
        boolean isSaved = book.saveInFile();
        Book bookFromList = Book.getBooks().get(0);
        assertTrue(isSaved);
        assertSame(book, bookFromList);

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/books.ser"));
        Book bookFromFile = (Book) is.readObject();
        assertEquals(bookFromList.toString(), bookFromFile.toString());

    }

    @Test
    void test_BookIsNotSavedInFile(){
        Book book = new Book("777","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        boolean isSaved = book.saveInFile();
        assertFalse(isSaved);
        assertEquals(Book.getBooks().size(), 0);
    }

    @Test
    void test_BookIsDeletedFromFile(){
        Book book = new Book("1231231231231","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        book.saveInFile();
//        System.out.println(Book.getBooks().contains(book));
        boolean isDeleted = book.deleteFromFile();
        assertTrue(isDeleted);
        assertFalse(Book.getBooks().contains(book));
    }

    @Test

    void test_BookIsValid(){
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book2 = new Book("1234567891012","StarWars", (float) 123.2, (float) 1234.2, 77, testAuthor );
        String isbn = book2.getIsbn();
        String title = book2.getTitle();
        float purchasedPrice = book2.getPurchasedPrice();
        float sellingPrice = book2.getSellingPrice();
        int stock = book2.getStock();
        boolean isValid = book.isValid();
        assertTrue(isValid, "Book is valid");
        assertTrue(isbn.matches("\\d{13}"), "Isbn contains only 13 digits");
        assertTrue(title.matches("[\\w\\s]+"), "Title is a string with only letters and spaces");
        assertTrue(purchasedPrice > 0, "Purchased price is a positive float value");
        assertTrue(sellingPrice > 0, "Selling price is a positive float value");
        assertTrue(stock > 0, "Stock is a positive integer");
    }

    @Test
    void test_InvalidUpdate(){
        Book book = new Book("1231231231231","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book2 = new Book("1231231231237","Godfather1", (float) 123.2, (float) 1234.2, 4, testAuthor );
        Book book3 = new Book("1231231231238","Godfather3", (float) 123.3, (float) 1234.3, 8, testAuthor );
        book.saveInFile();
        book2.saveInFile();
        book3.saveInFile();
        book.setIsbn("123");
        book.updateInFile();
        assertFalse(book.isValid(), "Invalid Isbn");

        book2.setSellingPrice(-100.0F);
        book2.updateInFile();
        assertFalse(book.isValid(), "Selling price is negative");

        book2.setStock(-5);
        book2.updateInFile();
        assertFalse(book.isValid(), "Stock is negative");

        book3.setTitle("");
        book3.updateInFile();
        assertFalse(book.isValid(), "Title is empty");
    }

    @Test

    void test_ValidUpdate(){
        Book book = new Book("1231231231231","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        book.saveInFile();
        book.setSellingPrice(10000.0F);
        book.setTitle("Inception");
        book.setSellingPrice((float) 77.7);
        book.updateInFile();
        assertTrue(book.isValid());

    }

    @Test
    void test_SaveDuplicateBook() throws FileNotFoundException {
        new PrintWriter("data/users.ser").close();
        Book.getBooks().clear();
        Book book = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book2 = new Book("1234567891011","Godfather2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        book.saveInFile();
        book2.saveInFile();
        assertTrue(book2.exists(), "Book already exists");
        assertFalse(Book.getBooks().contains(book2));
        assertEquals(Book.getBooks().size(), 1);
    }

    @Test
    void test_FindBooksByAuthor(){
        Book.getBooks().clear();
        Book book = new Book("1234567891011","Book1", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book2 = new Book("1111111111111","Book2", (float) 123.1, (float) 1234.1, 7, testAuthor );
        Book book3 = new Book("2222222222222","Book3", (float) 123.1, (float) 1234.1, 7, new Author("random", "random") );
        book.saveInFile();
        book2.saveInFile();
        book3.saveInFile();
        List<Author> list = new ArrayList<>(Book.getList("AndOm").stream().map(Book::getAuthor).toList());
        List<String> list2 = new ArrayList<>();
        list2.add(String.valueOf(list.get(0)));

        assertEquals(Book.getList("testA").stream().map(Book::getAuthor).toList().size(), 2);
        assertEquals(list2, Stream.of("random random").collect(Collectors.toList()));

        list.clear();

        list = new ArrayList<>(Book.getList("tEStAu").stream().map(Book::getAuthor).toList());
        list2.clear();
        list2.add(String.valueOf(list.get(0)));
        list2.add(String.valueOf(list.get(1)));
        assertEquals(list2, Stream.of("testAuthor 7", "testAuthor 7").collect(Collectors.toList()));

        assertEquals(Book.getList("Anonymous").stream().map(Book::getAuthor).toList(), List.of());

    }

    @Test
    void test_FindBooksByTitle() {
        Book.getBooks().clear();
        Book book = new Book("4444444444444","RandomBook1", (float) 123.1, (float) 1234.1, 7, new Author("Author4", "Author4") );
        Book book2 = new Book("3333333333333","RandomBook2", (float) 123.1, (float) 1234.1, 7, new Author("Author3", "Author3") );
        Book book3 = new Book("5555555555555","Complexity", (float) 123.1, (float) 1234.1, 7, new Author("Author5", "Author5") );
        book.saveInFile();
        book2.saveInFile();
        book3.saveInFile();

        assertEquals(Book.getList("Rand").stream().map(Book::getTitle).toList().size(), 2);
//        System.out.println(Book.getList("Rand").stream().map(Book::getTitle).toList());
        assertEquals(Book.getList("AndOm").stream().map(Book::getTitle).toList(), Stream.of("RandomBook1", "RandomBook2").collect(Collectors.toList()));
        assertEquals(Book.getList("Lexity").stream().map(Book::getTitle).toList(), Stream.of("Complexity").collect(Collectors.toList()));
        assertEquals(Book.getList("OmBoOK1").stream().map(Book::getTitle).toList(), Stream.of("RandomBook1").collect(Collectors.toList()));
        assertEquals(Book.getList("SHUTDOWN").stream().map(Book::getTitle).toList(), List.of());
    }

}
