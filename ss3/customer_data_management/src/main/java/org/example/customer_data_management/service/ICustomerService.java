package org.example.customer_data_management.service;

import org.example.customer_data_management.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();

    Customer findById(int id);

    void save(Customer customer);

    void update(int id, Customer customer);

    void deleteById(int id);
}
