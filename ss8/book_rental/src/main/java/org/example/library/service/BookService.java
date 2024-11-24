package org.example.library.service;

import jakarta.transaction.Transactional;
import org.example.library.model.Book;
import org.example.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        bookOptional.ifPresent(book -> {
            System.out.println("Book retrieved from DB - ID : " + book.getId() + " Title : " + book.getTitle() + "quantity : " + book.getQuantity());
        });
        return bookOptional;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        if (bookRepository.existsById(book.getId())) {
            System.out.println("Before update - Book ID: " + book.getId() + ", Title: " + book.getTitle() + ", Quantity: " + book.getQuantity());
            Book savedBook = bookRepository.save(book);
            System.out.println("After update - Book ID: " + savedBook.getId() + ", Title: " + savedBook.getTitle() + ", Quantity: " + savedBook.getQuantity());
            return savedBook;
        }
        throw new RuntimeException("Book not found");
    }

    @Override
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @Override
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return getAllBooks(pageable);
        }
        return bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

}