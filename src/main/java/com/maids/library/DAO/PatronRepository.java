package com.maids.library.DAO;

import com.maids.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Patron findByContactInformation(String contactInformation);
}