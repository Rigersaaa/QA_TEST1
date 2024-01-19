package application.bookstore.cucumber;

import application.bookstore.models.Author;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import application.bookstore.models.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookStepDefinitions {

    private List<Book> bookList = new ArrayList<>();
    private List<Book> searchResults;
    private Book expectedBook;
    private boolean found;

    @Given("the book list is empty")
    public void the_book_list_is_empty() {
        bookList.clear();
    }

    @When("I add a book with ISBN {string}, title {string}, purchased price {string}, selling price {string}, stock {string}, and author {string}")
    public void i_add_a_book_with_isbn_title_purchased_price_selling_price_stock_and_author(
            String isbn, String title, String purchasedPrice, String sellingPrice, String stock, String author) {
        Author bookAuthor = new Author(author);
        Book newBook = new Book(isbn, title, Float.parseFloat(purchasedPrice), Float.parseFloat(sellingPrice), Integer.parseInt(stock), bookAuthor);
        bookList.add(newBook);
    }

    @When("I search for a book with keyword {string}")
    public void i_search_for_a_book_with_keyword(String title) {
        found = false; // Reset found status for each search
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                found = true;
                break;
            }
        }
    }

    @Then("I should see the book in the search results")
    public void i_should_see_the_book_in_the_search_results() {
        assertTrue(found, "The book should be found in the search results");
    }

    @When("I delete the book with ISBN {string}")
    public void i_delete_the_book_with_isbn(String isbn) {
        expectedBook = findBookByISBN(isbn);
        if (expectedBook != null) {
            bookList.remove(expectedBook);
            expectedBook.deleteFromFile();
        }
    }

    @Then("the book should be removed successfully")
    public void the_book_should_be_removed_successfully() {
        assertTrue(!bookList.contains(expectedBook), "The book should be removed from the book list");
    }

    // Additional helper method to find a book by ISBN
    private Book findBookByISBN(String isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    @Then("the book list should contain a book with ISBN {string}, title {string}, purchased price {string}, selling price {string}, stock {string}, and author {string}")
    public void theBookListShouldContainABookWithISBNTitlePurchasedPriceSellingPriceStockAndAuthor(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        // Implement the verification logic here
    }
}
