package org.example.customer_manager_web.repository;

import org.example.customer_manager_web.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
