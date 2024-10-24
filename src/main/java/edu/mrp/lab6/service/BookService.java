package edu.mrp.lab6.service;/*
/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/

import edu.mrp.lab6.model.Book;
import edu.mrp.lab6.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();
    {
        books.add(new Book("1", "Book1", "Author1", "111-1111111111", "Description1"));
        books.add(new Book("2", "Book2", "Author2", "222-2222222222", "Description2"));
        books.add(new Book("3", "Book3", "Author3", "333-3333333333", "Description3"));
    }

    @PostConstruct
    public void init() { // Змінено на public
        bookRepository.deleteAll();
        bookRepository.saveAll(books);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delById(String id) {
        bookRepository.deleteById(id);
    }
}
