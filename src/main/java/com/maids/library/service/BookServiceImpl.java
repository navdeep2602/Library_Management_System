package com.maids.library.service;

import com.maids.library.entity.Book;
import com.maids.library.DAO.BookRepository;
import com.maids.library.exception.BookNotFoundException;
import com.maids.library.exception.InvalidBookException;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    @CachePut(value = "books", key = "#book.id")
    @Transactional
    public Book save(Book book) {

        if(book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidBookException("The book title cannot be empty.");
        }return bookRepository.save(book);
    }
    @CacheEvict(value = "books", key = "#id")
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}