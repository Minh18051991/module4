package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerAPIController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer existCustomer = customerService.findById(id).orElse(null);
        if (existCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customer.setId(existCustomer.getId());
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}