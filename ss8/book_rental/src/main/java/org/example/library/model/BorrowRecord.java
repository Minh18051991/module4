package org.example.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@Setter
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String borrowCode;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private boolean returned = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "borrow_record_books",
        joinColumns = @JoinColumn(name = "borrow_record_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public int calculateRentalDays() {
        if (returnDate == null) {
            return 0; // Sách chưa được trả
        }
        return (int) ChronoUnit.DAYS.between(borrowDate, returnDate);
    }

    public double calculateTotalRentalFee() {
        int rentalDays = calculateRentalDays();
        return books.stream()
                .mapToDouble(book -> book.getRentalFeePerDay() * rentalDays)
                .sum();
    }
}