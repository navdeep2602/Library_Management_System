package com.maids.library.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.Objects;

public class BorrowRequest {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    @NotNull(message = "Patron ID cannot be null")
    private Long patronId;
    @PastOrPresent(message = "Borrowed date must be in the past or present")
    private Date borrowedDate;

    public BorrowRequest(){}
    public BorrowRequest(Long bookId, Long patronId, Date borrowedDate) {
        this.bookId = bookId;
        this.patronId = patronId;
        this.borrowedDate = borrowedDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowRequest that)) return false;
        return Objects.equals(getBookId(), that.getBookId()) && Objects.equals(getPatronId(), that.getPatronId()) && Objects.equals(getBorrowedDate(), that.getBorrowedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getPatronId(), getBorrowedDate());
    }

    @Override
    public String toString() {
        return "BorrowRequest{" +
                "bookId=" + bookId +
                ", patronId=" + patronId +
                ", borrowedDate=" + borrowedDate +
                '}';
    }
}