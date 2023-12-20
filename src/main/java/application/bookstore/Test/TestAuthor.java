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

public class TestAuthor {

    @Test
    void test_AuthorSavedInFile() throws IOException, ClassNotFoundException {
        Author.getAuthors().clear();
        new PrintWriter("data/authors.ser").close(); //clear file
        Author author = new Author("authorname", "authorlname");
        boolean isSaved = author.saveInFile();
        assertTrue(isSaved, "Author saved successfully");
        assertSame(author, Author.getAuthors().get(0));
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/authors.ser"));
        Author authorFromFile = (Author)is.readObject();
        Author authorFromList = Author.getAuthors().get(0);
        assertEquals(authorFromFile.getFullName(), authorFromList.getFullName());

    }

    @Test
    void test_AuthorNotSavedInFile(){
        Author author = new Author("", "authorlname");
        Author author2 = new Author("authorname1", "authorlname1");
        Author author3 = new Author("authorname2", "authorlname2");
        boolean isSaved = author.saveInFile();
        author2.saveInFile();
        author3.saveInFile();
        assertFalse(isSaved, "Author not saved --missing name or lastname");
        assertEquals(Author.getAuthors().size(), 2);

    }
    @Test
    void test_AuthorIsDeletedFromFile(){
        Author author = new Author("authorname", "authorlname");
        author.saveInFile();
//        System.out.println(Author.getAuthors().size());
        boolean isDeleted = author.deleteFromFile();
        assertTrue(isDeleted);
        assertFalse(Author.getAuthors().contains(author));
    }

    @Test
    void test_AuthorIsValid(){
        Author author = new Author("authorname", "authorlname");
        assertTrue(author.isValid());

        Author author2 = new Author("authorname2", "authorlname2");
        String fname = author2.getFirstName();
        String lname = author2.getLastName();

        assertTrue(fname.length() > 0, "Valid name with length > 0");
        assertTrue(lname.length() > 0, "Valid lastname with length > 0");

    }

    @Test
    void test_InvalidUpdate(){
        Author author = new Author("authorname", "authorlname");
        author.saveInFile();
        author.setFirstName("");
        author.updateInFile();
        assertFalse(author.isValid(), "Invalid name");

        Author author2 = new Author("authorname2", "authorlname2");
        author2.saveInFile();
        author2.setLastName("");
        assertFalse(author2.isValid(), "Invalid lastname");

    }

    @Test

    void test_ValidUpdate(){
        Author author = new Author("authorname", "authorlname");
        author.saveInFile();
        author.setFirstName("New name");
        author.setLastName("New last name");
        author.updateInFile();
        assertTrue(author.isValid());
    }

//    @Test
//    void test_saveDuplicateAuthor() throws FileNotFoundException {
//        new PrintWriter("data/authors.ser").close();
//        Author.getAuthors().clear();
//
//        Author author = new Author("authorname", "authorlname");
//        author.saveInFile();
//        Author author2 = new Author("authorname", "authorlname");
//        author2.saveInFile();
//
//       assertTrue(author2.exists(), "Author already exists");
//       assertFalse(Author.getAuthors().contains(author2));
//       assertEquals(Author.getAuthors().size(), 1);
//
//    }

    @Test
    void test_ExistsMethod(){
        Author author = new Author("authorname", "authorlname");
        Author author2 = new Author("authorname2", "authorlname2");
        Author author3 = new Author("authorname3", "authorlname3");
        author2.saveInFile();
        author.saveInFile();

        assertTrue(author.exists());
        assertFalse(author3.exists());
    }

    @Test
    void test_SearchAuthor(){
        Author.getAuthors().clear();
        Author author = new Author("authorname", "authorlname");
        Author author2 = new Author("authorname2", "authorlname2");
        Author author3 = new Author("random", "random");
        Author author4 = new Author("yagami", "light");
        author.saveInFile();
        author2.saveInFile();
        author3.saveInFile();
        author4.saveInFile();

//        System.out.println(Author.getList("thor").stream().map(Author::getFullName).toList());
//        System.out.println(Stream.of("authorname authorlname", "authorname2 authorlname2").collect(Collectors.toList()));

       int nrOfAuthorsFound = Author.getList("thor").stream().map(Author::getFullName).toList().size();
       assertSame(nrOfAuthorsFound, 2);

       assertEquals(Author.getList("thor").stream().map(Author::getFullName).toList(), Stream.of("authorname authorlname", "authorname2 authorlname2").collect(Collectors.toList()));
       assertEquals(Author.getList("ThoR").stream().map(Author::getFullName).toList(), Stream.of("authorname authorlname", "authorname2 authorlname2").collect(Collectors.toList()));
       assertEquals(Author.getList("YAG").stream().map(Author::getFullName).toList(), Stream.of("yagami light").collect(Collectors.toList()));
       assertEquals(Author.getList("RAN").stream().map(Author::getFullName).toList(), Stream.of("random random").collect(Collectors.toList()));
       assertEquals(Author.getList("Auth1or").stream().map(Author::getFullName).toList(), List.of());


    }
}
