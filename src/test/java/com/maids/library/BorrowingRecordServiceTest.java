package com.maids.library;

import com.maids.library.service.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.maids.library.DAO.BookRepository;
import com.maids.library.DAO.BorrowingRecordRepository;
import com.maids.library.DAO.PatronRepository;
import com.maids.library.entity.Book;
import com.maids.library.entity.BorrowingRecord;
import com.maids.library.entity.Patron;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class BorrowingRecordServiceTest {
    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void borrowBook_WithValidBookAndPatron_ShouldReturnOk() {
        Book book = new Book();
        Patron patron = new Patron();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
        BorrowingRecord record = new BorrowingRecord();
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(record);

        ResponseEntity<?> response = borrowingRecordService.borrowBook(1L, 1L, new Date());

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void borrowBook_WithInvalidBookOrPatron_ShouldReturnNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty()); // No book found

        ResponseEntity<?> response = borrowingRecordService.borrowBook(1L, 1L, new Date());

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    public void returnBook_WithValidRecord_ShouldReturnOk() {
        // Assuming the setup is correct and the necessary fields are set
        List<BorrowingRecord> records = Collections.singletonList(new BorrowingRecord());
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(records);
        // Just return any instance of BorrowingRecord for the sake of the test
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(new BorrowingRecord());

        ResponseEntity<?> response = borrowingRecordService.returnBook(1L, 1L, new Date());

        assertEquals(ResponseEntity.ok().build(), response);
    }


    @Test
    public void returnBook_WithNoExistingRecord_ShouldReturnNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnedDateIsNull(anyLong(), anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = borrowingRecordService.returnBook(1L, 1L, new Date());
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}