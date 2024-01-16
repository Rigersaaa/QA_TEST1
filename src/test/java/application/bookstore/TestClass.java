package application.bookstore;


import application.bookstore.models.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestClass {

    @Test
    void test_Random(){
        assertTrue(true);
    }

    @Test

    void test_Author(){
        Author author = new Author("author", "7");
        System.out.println(author);
        assertTrue(true);
    }

}

