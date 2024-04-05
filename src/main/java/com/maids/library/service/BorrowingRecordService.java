package com.maids.library.service;

import org.springframework.http.ResponseEntity;
import java.util.Date;

public interface BorrowingRecordService {
    ResponseEntity<?> borrowBook(Long bookId, Long patronId, Date borrowedDate);
    ResponseEntity<?> returnBook(Long bookId, Long patronId, Date returnedDate);
}