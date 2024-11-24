package org.example.library.service;

import org.example.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Page<Book> getAllBooks(Pageable pageable);
    Optional<Book> getBookById(Long id);
    Book addBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long id);
    Page<Book> searchBooks(String keyword, Pageable pageable);

}