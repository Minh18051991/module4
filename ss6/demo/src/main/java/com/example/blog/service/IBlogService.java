package com.example.blog.service;

import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBlogService {
    List<Blog> findAll();
    Optional<Blog> findById(Long id);
    Blog save(Blog blog);
    void deleteById(Long id);
    List<Blog> findByTitleContaining(String title);
    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Blog> findByTitleContaining(String title, Pageable pageable);
    Page<Blog> findByCategory(Category category, Pageable pageable);
}