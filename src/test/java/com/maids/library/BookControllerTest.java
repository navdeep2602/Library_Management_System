package com.maids.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.library.controller.BookController;
import com.maids.library.entity.Book;
import com.maids.library.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @Autowired
    private BookController bookController;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllBooksTest() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> allBooks = Arrays.asList(book1, book2);

        when(bookService.findAll()).thenReturn(allBooks);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(allBooks.size()));
    }

    @Test
    void getBookByIdTest() throws Exception {
        Book book = new Book();
        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void createBookTest() throws Exception {
        Book book = new Book("title","author",87383,"msg");
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBookTest() throws Exception {
        Book existingBook = new Book("title1","author1",17731,"msg1");
        when(bookService.findById(1L)).thenReturn(existingBook);
        when(bookService.save(any(Book.class))).thenReturn(existingBook);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingBook)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookTest() throws Exception {
        doNothing().when(bookService).deleteById(1L);

        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isOk());
    }
}