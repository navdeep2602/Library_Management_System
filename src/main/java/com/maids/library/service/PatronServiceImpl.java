package com.maids.library.service;

import com.maids.library.entity.Patron;
import com.maids.library.DAO.PatronRepository;
import com.maids.library.exception.PatronAlreadyExistsException;
import com.maids.library.exception.PatronNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PatronServiceImpl implements PatronService{
    @Autowired
    private PatronRepository patronRepository;

    @Override
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    @Override
    @Cacheable(value = "patrons", key = "#id")
    @Transactional(readOnly = true)
    public Patron findById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron with ID " + id + " not found"));
    }

    @Override
    @CachePut(value = "patrons", key = "#patron.id")
    @Transactional
    public Patron save(Patron patron) {

        Patron existingPatron = patronRepository.findByContactInformation(patron.getContactInformation());
        if (existingPatron!=null) {
            throw new PatronAlreadyExistsException("A patron with the email " + patron.getContactInformation() + " already exists.");
        }
        return patronRepository.save(patron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#id")
    @Transactional
    public void deleteById(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new PatronNotFoundException("Patron with ID " + id + " not found");
        }
        patronRepository.deleteById(id);
    }
}