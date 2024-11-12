package org.example.customer_data_management.service;

import org.example.customer_data_management.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {
    private static final Map<Integer, Customer> customers = new HashMap<>();

    static {
        customers.put(1,new Customer(1, "John", "john@example.com", "123 Main St"));
        customers.put(2,new Customer(2, "Adam", "Adam@example.com", "456 Elm St"));
        customers.put(3,new Customer(3, "Smith", "Smith@example.com", "789 Oak St"));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer findById(int id) {
        return customers.get(id);

    }

    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public void update(int id, Customer customer) {
        customers.put(id, customer);
    }

    @Override
    public void deleteById(int id) {
        customers.remove(id);
    }

}
