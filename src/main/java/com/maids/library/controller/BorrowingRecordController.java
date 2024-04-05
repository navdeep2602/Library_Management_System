package com.maids.library.controller;

import com.maids.library.DTO.BorrowRequest;
import com.maids.library.DTO.ReturnRequest;
import com.maids.library.service.BorrowingRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@Valid @RequestBody BorrowRequest request) {
        return borrowingRecordService.borrowBook(request.getBookId(), request.getPatronId(), request.getBorrowedDate());
    }

    @PutMapping("/return")
    public ResponseEntity<?> returnBook(@Valid @RequestBody ReturnRequest request) {
        return borrowingRecordService.returnBook(request.getBookId(), request.getPatronId(), request.getReturnedDate());
    }
}