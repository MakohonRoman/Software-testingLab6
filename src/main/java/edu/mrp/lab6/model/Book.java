package edu.mrp.lab6.model;/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String description;

    public Book(String title, String author, String isbn, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
    }

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
