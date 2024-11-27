package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/customers")
    public String viewCustomerPage() {
        return "list"; // Tên của tệp HTML (list.html)
    }
}