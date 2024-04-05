package com.maids.library.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

public class ReturnRequest {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    @NotNull(message = "Patron ID cannot be null")
    private Long patronId;

    private Date returnedDate;

    public ReturnRequest(){}
    public ReturnRequest(Long bookId, Long patronId, Date returnedDate) {
        this.bookId = bookId;
        this.patronId = patronId;
        this.returnedDate = returnedDate;
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

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnRequest that)) return false;
        return Objects.equals(getBookId(), that.getBookId()) && Objects.equals(getPatronId(), that.getPatronId()) && Objects.equals(getReturnedDate(), that.getReturnedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getPatronId(), getReturnedDate());
    }

    @Override
    public String toString() {
        return "ReturnRequest{" +
                "bookId=" + bookId +
                ", patronId=" + patronId +
                ", returnedDate=" + returnedDate +
                '}';
    }
}