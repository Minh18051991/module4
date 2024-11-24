package org.example.library.controller;

import jakarta.servlet.http.HttpSession;
import org.example.library.model.Book;
import org.example.library.model.BorrowRecord;
import org.example.library.model.Cart;
import org.example.library.model.User;
import org.example.library.service.BorrowRecordService;
import org.example.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private BorrowRecordService borrowRecordService;

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        List<Book> cartItems = new ArrayList<>();
        for (Long bookId : cart.getItems().keySet()) {
            Book book = bookService.getBookById(bookId).orElse(null);
            if (book != null) {
                book.setQuantity(cart.getItems().get(bookId));
                cartItems.add(book);
            }
        }

        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long bookId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItem(bookId);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/confirm")
    @Transactional
    public String confirmCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/user/login";
        }

        try {
            List<Book> borrowedBooks = new ArrayList<>();
            for (Long bookId : cart.getItems().keySet()) {
                Book book = bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Book not found: " + bookId));
                int borrowedQuantity = cart.getItems().get(bookId);

                if (book.getQuantity() < borrowedQuantity) {
                    throw new RuntimeException("Insufficient quantity for book: " + book.getTitle());
                }

                book.setQuantity(book.getQuantity() - borrowedQuantity);
                Book updatedBook = bookService.updateBook(book);
                
                Book borrowedBook = new Book();
                borrowedBook.setId(updatedBook.getId());
                borrowedBook.setTitle(updatedBook.getTitle());
                borrowedBook.setQuantity(borrowedQuantity);
                borrowedBooks.add(borrowedBook);
            }

            BorrowRecord borrowRecord = new BorrowRecord();
            borrowRecord.setUser(user);
            borrowRecord.setBooks(borrowedBooks);
            borrowRecord.setBorrowDate(LocalDateTime.now());
            borrowRecord.setBorrowCode(UUID.randomUUID().toString());
            borrowRecord.setReturned(false);

            BorrowRecord savedBorrowRecord = borrowRecordService.createBorrowRecord(borrowRecord);

            cart.clear();
            session.setAttribute("cart", cart);
            model.addAttribute("borrowCode", savedBorrowRecord.getBorrowCode());
            return "borrow-success";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi xác nhận mượn sách: " + e.getMessage());
            return "cart";
        }
    }
}