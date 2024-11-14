package com.example.product.service;

import com.example.product.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product);
    void remove(int id);
    void update(Product product);
    List<Product> searchByName(String name);

}
