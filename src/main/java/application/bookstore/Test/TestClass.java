package application.bookstore.Test;

import org.testng.annotations.Test;
import application.bookstore.models.Author;
import static org.testng.AssertJUnit.assertTrue;

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
