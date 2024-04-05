package com.maids.library.service;

import com.maids.library.entity.Book;
import com.maids.library.entity.BorrowingRecord;
import com.maids.library.entity.Patron;
import com.maids.library.DAO.BookRepository;
import com.maids.library.DAO.BorrowingRecordRepository;
import com.maids.library.DAO.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @Override
    @Transactional
    public ResponseEntity<?> borrowBook(Long bookId, Long patronId, Date borrowedDate) {
        // Existing logic to borrow a book, now includes borrowedDate
        Book book = bookRepository.findById(bookId).orElse(null);
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if (book == null || patron == null) {
            return ResponseEntity.notFound().build();
        }
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowedDate(borrowedDate);
        borrowingRecordRepository.save(record);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public ResponseEntity<?> returnBook(Long bookId, Long patronId, Date returnedDate) {
        // Logic to update the borrowing record with returnedDate
        List<BorrowingRecord> records = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnedDateIsNull(bookId, patronId);
        if (records.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BorrowingRecord record = records.get(0);
        record.setReturnedDate(returnedDate);
        borrowingRecordRepository.save(record);
        return ResponseEntity.ok().build();
    }
}