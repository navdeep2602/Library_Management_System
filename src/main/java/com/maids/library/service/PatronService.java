package com.maids.library.service;

import com.maids.library.entity.Patron;
import java.util.List;

public interface PatronService {
    List<Patron> findAll();
    Patron findById(Long id);
    Patron save(Patron patron);
    void deleteById(Long id);
}