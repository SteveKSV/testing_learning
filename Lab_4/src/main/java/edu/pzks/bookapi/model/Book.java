package edu.pzks.bookapi.model;

/*
  @author   Stepan Klem
  @project   book-api
  @version  1.0.0
  @since 30.09.2024
*/

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Document(collection = "books")
public class Book {
    private String id;
    private String title;
    private String description;
    private String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return getId().equals(book.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
