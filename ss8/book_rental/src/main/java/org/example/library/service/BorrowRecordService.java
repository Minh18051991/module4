package org.example.library.service;

import org.example.library.model.BorrowRecord;
import org.example.library.model.Book;
import org.example.library.repository.IBorrowRecordRepository;
import org.example.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BorrowRecordService implements IBorrowRecordService {

    @Autowired
    private IBorrowRecordRepository borrowRecordRepository;

    @Autowired
    private IBookRepository bookRepository;

    private Map<Long, Integer> cart = new HashMap<>();

    @Override
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    @Override
    public Optional<BorrowRecord> getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id);
    }

    @Override
    public Optional<BorrowRecord> getBorrowRecordByBorrowCode(String borrowCode) {
        return borrowRecordRepository.findByBorrowCode(borrowCode);
    }

    @Override
    public List<BorrowRecord> getBorrowRecordsByUserId(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }

    @Override
    public BorrowRecord createBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public BorrowRecord updateBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public BorrowRecord markAsReturned(String borrowCode) {
        BorrowRecord record = getBorrowRecordByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        record.setReturned(true);
        record.setReturnDate(LocalDateTime.now());

        // Increase the book quantity
        for (Book book : record.getBooks()) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        return borrowRecordRepository.save(record);
    }

    @Override
    public void deleteBorrowRecord(Long id) {
        borrowRecordRepository.deleteById(id);
    }

    @Override
    public int calculateRentalDays(String borrowCode) {
        BorrowRecord record = getBorrowRecordByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnDate() == null) {
            throw new RuntimeException("Borrow record has not been returned yet");
        }

        long rentalDays = java.time.Duration.between(record.getBorrowDate(), record.getReturnDate()).toDays();
        return (int) rentalDays;
    }

    @Override
    public double calculateTotalRentalFee(String borrowCode) {
        BorrowRecord record = getBorrowRecordByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnDate() == null) {
            throw new RuntimeException("Borrow record has not been returned yet");
        }

        long rentalDays = java.time.Duration.between(record.getBorrowDate(), record.getReturnDate()).toDays();
        double feePerDay = 1.5; // Example fee per day
        return rentalDays * feePerDay;
    }

    @Override
    public boolean isReturned(String borrowCode) {
        BorrowRecord record = getBorrowRecordByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        return record.isReturned();
    }

    @Override
    public List<BorrowRecord> getUnreturnedBorrowRecords() {
        return borrowRecordRepository.findByReturnedFalse();
    }

    @Override
    public List<BorrowRecord> getReturnedBorrowRecords() {
        return borrowRecordRepository.findByReturnedTrue();
    }

    public void addToCart(Long bookId, int quantity) {
        cart.put(bookId, cart.getOrDefault(bookId, 0) + quantity);
    }

    public Map<Long, Integer> getCart() {
        return cart;
    }

    public String confirmBorrow(Map<Long, Integer> bookId) {
        // Check if the book is available
        Book book = (Book) bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available");
        }

        // Generate a 5-digit borrow code
        String borrowCode = String.format("%05d", new Random().nextInt(100000));

        // Decrease the book quantity
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        // Save the borrow record to the database
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBorrowCode(borrowCode);
        borrowRecord.setBorrowDate(LocalDateTime.now());
        borrowRecord.setReturned(false);
        borrowRecord.setBooks(Collections.singletonList(book));
        borrowRecordRepository.save(borrowRecord);

        return borrowCode;
    }

    public void returnBook(String borrowCode) {
        BorrowRecord record = getBorrowRecordByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Invalid borrow code"));

        if (record.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        record.setReturned(true);
        record.setReturnDate(LocalDateTime.now());
        borrowRecordRepository.save(record);

        for (Book book : record.getBooks()) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }
    }
}