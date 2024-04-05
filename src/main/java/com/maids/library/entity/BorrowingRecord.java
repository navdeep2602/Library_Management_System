package com.maids.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @PastOrPresent(message = "Borrowed date must be in the past or present")
    private Date borrowedDate;
    @FutureOrPresent(message = "Returned Data must be from future or present")
    private Date returnedDate;

    public BorrowingRecord(){}
    public BorrowingRecord(Long id, Book book, Patron patron, Date borrowedDate, Date returnedDate) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
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
        if (!(o instanceof BorrowingRecord that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getBook(), that.getBook()) && Objects.equals(getPatron(), that.getPatron()) && Objects.equals(getBorrowedDate(), that.getBorrowedDate()) && Objects.equals(getReturnedDate(), that.getReturnedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBook(), getPatron(), getBorrowedDate(), getReturnedDate());
    }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "id=" + id +
                ", book=" + book +
                ", patron=" + patron +
                ", borrowedDate=" + borrowedDate +
                ", returnedDate=" + returnedDate +
                '}';
    }
}