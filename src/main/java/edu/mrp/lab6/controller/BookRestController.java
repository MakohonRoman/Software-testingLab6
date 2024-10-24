package edu.mrp.lab6.controller;/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/

import edu.mrp.lab6.model.Book;
import edu.mrp.lab6.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> showAll() {
        List<Book> books = bookService.getAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> showOneById(@PathVariable String id) {
        Book book = bookService.getById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> insert(@RequestBody Book book) {
        Book createdBook = bookService.create(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping
    public ResponseEntity<Book> edit(@RequestBody Book book) {
        Book updatedBook = bookService.update(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.delById(id);
        return ResponseEntity.noContent().build(); // Повертає 204 No Content
    }
}
