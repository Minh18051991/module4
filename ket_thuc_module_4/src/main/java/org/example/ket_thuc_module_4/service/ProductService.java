package org.example.ket_thuc_module_4.service;

import org.example.ket_thuc_module_4.model.Product;
import org.example.ket_thuc_module_4.model.Category;
import org.example.ket_thuc_module_4.repository.IProductRepository;
import org.example.ket_thuc_module_4.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    @Autowired
    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            product.setCategory(categoryRepository.findById(product.getCategory().getId()).orElse(null));
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}