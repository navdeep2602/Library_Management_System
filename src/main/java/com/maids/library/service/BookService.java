package com.maids.library.service;

import com.maids.library.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAll();
    public Book findById(Long id);
    public Book save(Book book);
    public void deleteById(Long id);
}