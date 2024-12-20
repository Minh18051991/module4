package org.example.product_management.repository;

import org.example.product_management.model.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product);
    void deleteById(int id);
    void update(Product product);
    List<Product> searchByName(String name);
}