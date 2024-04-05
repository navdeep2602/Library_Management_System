package com.maids.library.DAO;

import com.maids.library.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByBookIdAndPatronIdAndReturnedDateIsNull(Long bookId, Long patronId);

}