package org.example.library.service;

import org.example.library.model.Book;
import org.example.library.model.BorrowRecord;
import org.example.library.repository.IBookRepository;
import org.example.library.repository.IBorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBorrowRecordRepository borrowRecordRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }



    public String borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            String borrowCode = generateBorrowCode();
            BorrowRecord record = new BorrowRecord();
            record.setBookId(bookId);
            record.setBorrowCode(borrowCode);
            borrowRecordRepository.save(record);
            return borrowCode;
        } else {
            throw new RuntimeException("Book out of stock");
        }
    }

    public Book returnBook(String borrowCode) {
        BorrowRecord record = borrowRecordRepository.findByBorrowCode(borrowCode)
                .orElseThrow(() -> new RuntimeException("Invalid borrow code"));
        Book book = bookRepository.findById(record.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        borrowRecordRepository.delete(record);
        return book;
    }

    private String generateBorrowCode() {
        return String.valueOf((int) (Math.random() * 90000) + 10000);
    }
}