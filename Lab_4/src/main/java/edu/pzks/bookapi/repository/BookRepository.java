package edu.pzks.bookapi.repository;

/*
  @author   Stepan Klem
  @project   book-api
  @version  1.0.0
  @since 30.09.2024
*/

import edu.pzks.bookapi.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
