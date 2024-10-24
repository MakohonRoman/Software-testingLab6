package edu.mrp.lab6;

/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/


import edu.mrp.lab6.model.Book;
import edu.mrp.lab6.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Lab6BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();  // Очищення репозиторію перед кожним тестом

        bookRepository.save(new Book("1", "Book1", "Author1", "111-1111111111", "Description1"));
        bookRepository.save(new Book("2", "Book2", "Author2", "222-2222222222", "Description2"));
        bookRepository.save(new Book("3", "Book3", "Author3", "333-3333333333", "Description3"));
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();  // Очищення після кожного тесту
    }

    // Тест 1: Перевірка, що всі книги додалися правильно
    @Test
    void testSetShouldContain_3_Records() {
        List<Book> books = bookRepository.findAll();
        assertEquals(3, books.size(), "Репозиторій повинен містити 3 записи.");
    }

    // Тест 2: Перевірка додавання нової книги та її ID
    @Test
    void shouldGiveIdForNewRecord() {
        // Створення нової книги
        Book newBook = new Book("New Book", "New Author", "444-4444444444", "Description4");
        bookRepository.save(newBook);

        // Перевірка, що книга збереглась з коректним ID
        Book savedBook = bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equals("New Book"))
                .findFirst()
                .orElse(null);

        assertNotNull(savedBook);
        assertNotNull(savedBook.getId());
        assertEquals(24, savedBook.getId().length(), "ID нової книги має бути 24 символи.");
    }

    // Тест 3: Перевірка видалення книги за ID
    @Test
    void testDeleteById() {
        // Видалення книги з ID "1"
        bookRepository.deleteById("1");

        // Перевірка, що книга видалена
        assertFalse(bookRepository.findById("1").isPresent(), "Книга з ID '1' повинна бути видалена.");
    }

    // Тест 4: Перевірка оновлення книги
    @Test
    void testUpdateBook() {
        // Отримуємо книгу з ID "2" та змінюємо її опис
        Book book = bookRepository.findById("2").orElse(null);
        assertNotNull(book);
        book.setDescription("Updated Description");
        bookRepository.save(book);

        // Перевірка, що зміни збережені
        Book updatedBook = bookRepository.findById("2").orElse(null);
        assertNotNull(updatedBook);
        assertEquals("Updated Description", updatedBook.getDescription(), "Опис книги повинен бути оновленим.");
    }

    // Тест 5: Перевірка отримання книги за ID
    @Test
    void testFindById() {
        // Отримання книги з ID "3"
        Book book = bookRepository.findById("3").orElse(null);

        // Перевірка, що книга знайдена і її дані коректні
        assertNotNull(book);
        assertEquals("Book3", book.getTitle(), "Назва книги повинна бути 'Book3'.");
        assertEquals("Author3", book.getAuthor(), "Автор книги повинен бути 'Author3'.");
    }
}
