package org.example.library.service;

import org.example.library.model.BorrowRecord;
import java.util.List;
import java.util.Optional;

public interface IBorrowRecordService {

    // Lấy tất cả bản ghi mượn sách
    List<BorrowRecord> getAllBorrowRecords();

    Optional<BorrowRecord> getBorrowRecordById(Long id);

    // Lấy bản ghi mượn sách theo mã mượn
    Optional<BorrowRecord> getBorrowRecordByBorrowCode(String borrowCode);

    // Lấy tất cả bản ghi mượn sách của một người dùng
    List<BorrowRecord> getBorrowRecordsByUserId(Long userId);

    // Tạo một bản ghi mượn sách mới
    BorrowRecord createBorrowRecord(BorrowRecord borrowRecord);

    // Cập nhật một bản ghi mượn sách
    BorrowRecord updateBorrowRecord(BorrowRecord borrowRecord);

    // Đánh dấu một bản ghi mượn sách là đã trả
    BorrowRecord markAsReturned(String borrowCode);

    // Xóa một bản ghi mượn sách
    void deleteBorrowRecord(Long id);

    // Tính tổng số ngày thuê cho một bản ghi mượn sách
    int calculateRentalDays(String borrowCode);

    // Tính tổng phí thuê cho một bản ghi mượn sách
    double calculateTotalRentalFee(String borrowCode);

    // Kiểm tra xem một bản ghi mượn sách đã được trả hay chưa
    boolean isReturned(String borrowCode);

    // Lấy danh sách các bản ghi mượn sách chưa trả
    List<BorrowRecord> getUnreturnedBorrowRecords();

    // Lấy danh sách các bản ghi mượn sách đã trả
    List<BorrowRecord> getReturnedBorrowRecords();
}