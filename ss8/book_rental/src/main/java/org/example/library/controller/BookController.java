package org.example.library.controller;

import org.example.library.aspect.LoggingAspect;
import org.example.library.model.Book;
import org.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private LoggingAspect loggingAspect;

    @GetMapping("/new")
    public String showAddBookForm() {
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        int visitCount = loggingAspect.getVisitCount();
        model.addAttribute("books", books);
        model.addAttribute("visitCount", visitCount);
        return "library";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        return "details";
    }

    @GetMapping("/borrow/{id}")
    public String borrowBook(@PathVariable Long id, Model model) {
        String borrowCode = bookService.borrowBook(id);
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("borrowCode", borrowCode);
        model.addAttribute("message", "Book borrowed successfully");
        return "details";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam String borrowCode, Model model) {
        try {
            Book returnedBook = bookService.returnBook(borrowCode);
            model.addAttribute("message", "Book returned successfully");
            model.addAttribute("book", returnedBook);
            return "success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }
}