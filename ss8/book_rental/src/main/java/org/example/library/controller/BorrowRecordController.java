package org.example.library.controller;

import org.example.library.model.Book;
import org.example.library.model.BorrowRecord;
import org.example.library.service.IBorrowRecordService;
import org.example.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class BorrowRecordController {

    @Autowired
    private IBorrowRecordService borrowRecordService;

    @Autowired
    private IBookService bookService;

    @PostMapping("/confirm-return")
    public String returnBooks(@RequestParam String borrowCode, Model model) {
        BorrowRecord borrowRecord = borrowRecordService.getBorrowRecordByBorrowCode(borrowCode)
                .orElse(null);
        if (borrowRecord == null) {
            return "redirect:/return";
        }

        borrowRecord.setReturnDate(LocalDateTime.now());
        borrowRecord.setReturned(true);
        borrowRecordService.updateBorrowRecord(borrowRecord);

        // Update book quantities
        borrowRecord.getBooks().forEach(book -> {
            Book existingBook = bookService.getBookById(book.getId()).orElse(null);
            if (existingBook != null) {
                existingBook.setQuantity(existingBook.getQuantity() + book.getQuantity());
                bookService.updateBook(existingBook);
            }
        });

        double fee = borrowRecord.calculateTotalRentalFee();
        model.addAttribute("fee", fee);
        return "return-success";
    }
}