package com.example.demo.service;

import com.example.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface ICustomerService {
    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
}
