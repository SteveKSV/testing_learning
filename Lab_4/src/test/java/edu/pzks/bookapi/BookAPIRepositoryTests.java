package edu.pzks.bookapi;

import edu.pzks.bookapi.model.Book;
import edu.pzks.bookapi.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

 /*
  @author - Stepan Klem
  @project - Lab_4
  @date: 10.10.2024
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class BookAPIRepositoryTests {

    @Autowired
    BookRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        // Create sample books for testing with diverse attributes
        Book harryPotter = new Book("1", "Harry Potter and the Sorcerer's Stone",
                "A young boy discovers he is a wizard ##test", "Fantasy");
        Book theHobbit = new Book("2", "The Hobbit",
                "A hobbit embarks on an adventure to reclaim treasure ##test", "Fantasy");
        Book mobyDick = new Book("3", "Moby Dick",
                "The quest for revenge against a giant whale ##test", "Adventure");

        // Save the books in the repository
        underTest.saveAll(List.of(harryPotter, theHobbit, mobyDick));
    }

    @AfterEach
    void tearDown() {
        // Delete books that were marked for testing
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getDescription().contains("###test"))
                .toList();
        underTest.deleteAll(booksToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void shouldFindBookById() {
        // given
        Book book = new Book("1", "1984", "Dystopian novel ##test", "Dystopia");
        underTest.save(book);

        // when
        Book foundBook = underTest.findById(book.getId()).orElse(null);

        // then
        assertNotNull(foundBook);
        assertEquals(book.getTitle(), foundBook.getTitle());
    }
    @Test
    void shouldSaveNewBook() {
        // given
        Book newBook = new Book("4", "1984", "Dystopian novel ##test", "Fiction");

        // when
        underTest.save(newBook);
        Book retrievedBook = underTest.findById("4").orElse(null);

        // then
        assertNotNull(retrievedBook);
        assertEquals(newBook.getTitle(), retrievedBook.getTitle());
        assertEquals(newBook.getDescription(), retrievedBook.getDescription());
        assertEquals(newBook.getGenre(), retrievedBook.getGenre());
    }

    @Test
    void shouldUpdateExistingBook() {
        // given
        Book bookToUpdate = new Book("1", "Harry Potter and the Sorcerer's Stone",
                "A young boy discovers he is a wizard ##test", "Fantasy");
        underTest.save(bookToUpdate);

        // when
        bookToUpdate.setDescription("A young wizard's journey ##test");
        underTest.save(bookToUpdate);
        Book updatedBook = underTest.findById("1").orElse(null);

        // then
        assertNotNull(updatedBook);
        assertEquals("A young wizard's journey ##test", updatedBook.getDescription());
    }

    @Test
    void shouldDeleteBook() {
        // given
        Book bookToDelete = new Book("1", "Harry Potter and the Sorcerer's Stone",
                "A young boy discovers he is a wizard ##test", "Fantasy");
        underTest.save(bookToDelete);

        // when
        underTest.delete(bookToDelete);
        Book deletedBook = underTest.findById("1").orElse(null);

        // then
        assertNull(deletedBook);
    }

    @Test
    void shouldFindBooksByGenre() {
        // given
        Book book1 = new Book("1", "Harry Potter and the Sorcerer's Stone",
                "A young boy discovers he is a wizard ##test", "Fantasy");
        Book book2 = new Book("2", "The Hobbit",
                "A hobbit embarks on an adventure to reclaim treasure ##test", "Fantasy");
        Book book3 = new Book("3", "Moby Dick",
                "The quest for revenge against a giant whale ##test", "Adventure");

        // Save the books to the repository
        underTest.saveAll(List.of(book1, book2, book3));

        // when
        List<Book> allBooks = underTest.findAll(); // Fetch all books from the repository
        List<Book> fantasyBooks = allBooks.stream() // Filter the books by genre
                .filter(book -> book.getGenre().equals("Fantasy"))
                .toList();

        // then
        assertEquals(2, fantasyBooks.size());
        assertTrue(fantasyBooks.stream().allMatch(book -> book.getGenre().equals("Fantasy")));
    }
}
