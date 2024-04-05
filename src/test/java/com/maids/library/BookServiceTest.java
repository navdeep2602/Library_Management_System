package com.maids.library;

import com.maids.library.DAO.BookRepository;
import com.maids.library.entity.Book;
import com.maids.library.exception.BookNotFoundException;
import com.maids.library.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooksTest() {
        // Setup our mock repository
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    void findBookByIdTest() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.findById(1L);

        assertNotNull(foundBook);
    }

    @Test
    void findBookByIdNotFoundTest() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(1L);
        });

        String expectedMessage = "Book with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveValidBookTest() {
        Book book = new Book("title","author",81728,"msg");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.save(book);
        assertNotNull(savedBook);
    }

    @Test
    void deleteExistingBookTest() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteNonExistingBookTest() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteById(1L);
        });

        String expectedMessage = "Book with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}