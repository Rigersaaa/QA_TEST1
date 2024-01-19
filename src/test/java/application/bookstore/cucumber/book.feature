Feature: Bookstore Management

  Scenario: Add a Book
    Given the book list is empty
    When I add a book with ISBN "1234567890123", title "Test Book", purchased price "10.0", selling price "20.0", stock "100", and author "Test Author"
    Then the book list should contain a book with ISBN "1234567890123", title "Test Book", purchased price "10.0", selling price "20.0", stock "100", and author "Test Author"

  Scenario: Search for a Book
    Given the book list contains a book with ISBN "1234567890123", title "Test Book", purchased price "10.0", selling price "20.0", stock "100", and author "Test Author"
    When I search for a book with keyword "Test"
    Then I should see the book in the search results

  Scenario: Delete a Book
    Given the book list contains a book with ISBN "1234567890123", title "Test Book", purchased price "10.0", selling price "20.0", stock "100", and author "Test Author"
    When I delete the book with ISBN "1234567890123"
    Then the book should be removed successfully
