package edu.pzks.bookapi.controller;

import edu.pzks.bookapi.model.Book;
import edu.pzks.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
  @author   Stepan Klem
  @project   book-api
  @version  1.0.0
  @since 30.09.2024
*/

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;
    // Створення нової книги (Create)
    @PostMapping("/")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    // Отримання всіх книг (Read)
    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    // Отримання книги за ідентифікатором (Read)
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    // Оновлення книги за ідентифікатором (Update)
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }

    // Видалення книги за ідентифікатором (Delete)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }
}