package org.example.library.controller;

import jakarta.servlet.http.HttpSession;
import org.example.library.model.Book;
import org.example.library.model.BorrowRecord;
import org.example.library.model.Cart;
import org.example.library.service.IBookService;
import org.example.library.service.IBorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private IBorrowRecordService borrowRecordService;

    @GetMapping("/")
    public String landing(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/home";
        }
        return "landing_page";
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "3") int size,
                       Model model,
                       HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/user/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookService.getAllBooks(pageable);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        return "home";
    }

    @PostMapping("/addtocart")
    public String addToCart(@RequestParam Long bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(bookId).orElse(null);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book not found");
            return "redirect:/home";
        }
        if(book.getQuantity() == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book out of stock");
            return "redirect:/home";
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.addItem(bookId, 1);
        redirectAttributes.addFlashAttribute("message", "Book added to cart");
        return "redirect:/home";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword, Model model, Pageable pageable) {
        Page<Book> searchResults = bookService.searchBooks(keyword, pageable);
        model.addAttribute("books", searchResults);
        return "books/search-results";

    }

    @PostMapping("/initiate-return")
    public String initiateReturn(@RequestParam String borrowCode, Model model) {
        BorrowRecord borrowRecord = borrowRecordService.getBorrowRecordByBorrowCode(borrowCode)
                .orElse(null);
        if (borrowRecord == null) {
            return "redirect:/home";
        }

        model.addAttribute("borrowRecord", borrowRecord);
        return "borrow-record-details";
    }


}