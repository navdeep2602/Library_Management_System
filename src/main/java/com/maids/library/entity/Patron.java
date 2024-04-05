package com.maids.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "patrons")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name must not be null")
    private String name;
    @NotNull(message = "Contact Information must not be null")
    private String contactInformation;

    @OneToMany(mappedBy = "patron")
    private Set<BorrowingRecord> borrowingRecords;

    public Patron()
    {}
    public Patron(String name, String contactInformation, Set<BorrowingRecord> borrowingRecords) {
        this.name = name;
        this.contactInformation = contactInformation;
        this.borrowingRecords = borrowingRecords;
    }
    public Patron(Long id,String name, String contactInformation, Set<BorrowingRecord> borrowingRecords) {
        this.id=id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.borrowingRecords = borrowingRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron patron)) return false;
        return Objects.equals(getId(), patron.getId()) && Objects.equals(getName(), patron.getName()) && Objects.equals(getContactInformation(), patron.getContactInformation()) && Objects.equals(getBorrowingRecords(), patron.getBorrowingRecords());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getContactInformation(), getBorrowingRecords());
    }

    @Override
    public String toString() {
        return "Patron{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                ", borrowingRecords=" + borrowingRecords +
                '}';
    }
}