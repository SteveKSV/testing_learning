package edu.pzks.bookapi.service;

/*
  @author   Stepan Klem
  @project   book-api
  @version  1.0.0
  @since 30.09.2024
*/

import edu.pzks.bookapi.model.Book;
import edu.pzks.bookapi.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();
    {
        books.add(new Book("1", "The Great Gatsby", "A novel set in the Roaring Twenties", "Fiction"));
        books.add(new Book("2", "1984", "A dystopian novel by George Orwell", "Science Fiction"));
        books.add(new Book("3", "To Kill a Mockingbird", "A novel about racial injustice", "Classic"));
    }

    @PostConstruct
    void init() {
        bookRepository.saveAll(books);
    }

    // Створення нової книги
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Отримання всіх книг
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // Отримання книги за ідентифікатором
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    // Оновлення книги за ідентифікатором
    public Book updateBook(String id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(updatedBook.getTitle());
                    existingBook.setDescription(updatedBook.getDescription());
                    existingBook.setGenre(updatedBook.getGenre());
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    // Видалення книги за ідентифікатором
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }


}
