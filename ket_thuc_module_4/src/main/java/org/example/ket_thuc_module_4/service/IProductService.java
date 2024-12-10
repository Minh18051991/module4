package org.example.ket_thuc_module_4.service;

import org.example.ket_thuc_module_4.model.Product;
import org.example.ket_thuc_module_4.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product save(Product product);
    void deleteById(Long id);
    void deleteAllById(List<Long> ids);
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    List<Category> getAllCategories();
}