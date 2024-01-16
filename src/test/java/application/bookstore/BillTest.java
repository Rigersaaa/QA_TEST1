package application.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import application.bookstore.models.Bill;
import application.bookstore.models.Book;
import application.bookstore.models.User;
import org.junit.jupiter.api.Test;

class BillTest {

    @Test
    void testSaveInFile() {
        assertTrue((new Bill()).saveInFile());
    }

    @Test
    void testDeleteFromFile() {
        assertFalse((new Bill()).deleteFromFile());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        Bill actualBill = new Bill();
        Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualBill.setDate(date);
        Book soldBooks = new Book();
        actualBill.setSoldBook(soldBooks);
        actualBill.setTotalAmount(10.0f);
        User user = new User("test", "test");

        actualBill.setUser(user);
        Date actualDate = actualBill.getDate();
        Book actualSoldBook = actualBill.getSoldBook();
        float actualTotalAmount = actualBill.getTotalAmount();
        User actualUser = actualBill.getUser();

        // Assert that nothing has changed
        assertEquals(10.0f, actualTotalAmount);
        assertTrue(actualBill.isValid());
        assertSame(soldBooks, actualSoldBook);
        assertSame(user, actualUser);
        assertSame(date, actualDate);
    }
}