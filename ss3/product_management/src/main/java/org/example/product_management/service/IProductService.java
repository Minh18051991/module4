package org.example.product_management.service;

import org.example.product_management.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product);
    void remove(int id);
    void update(int id, Product product);
    List<Product> search(String name);

}
