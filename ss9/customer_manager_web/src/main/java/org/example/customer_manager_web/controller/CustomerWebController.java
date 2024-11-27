package org.example.customer_manager_web.controller;

import org.example.customer_manager_web.model.Customer;
import org.example.customer_manager_web.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerWebController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping
    public String listCustomers(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerService.findAll(pageable);
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        customer.ifPresent(value -> model.addAttribute("customer", value));
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }
}