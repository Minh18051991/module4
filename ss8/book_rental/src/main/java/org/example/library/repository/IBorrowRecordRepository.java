package org.example.library.repository;

import org.example.library.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBorrowCode(String borrowCode);
    List<BorrowRecord> findByUserId(Long userId);
    List<BorrowRecord> findByReturnedFalse();
    List<BorrowRecord> findByReturnedTrue();
}