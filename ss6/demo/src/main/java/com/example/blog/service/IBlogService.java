package com.example.blog.service;

import com.example.blog.model.Blog;

import java.util.List;
import java.util.Optional;

public interface IBlogService {
    List<Blog> findAll();
    Optional<Blog> findById(Long id);
    Blog save(Blog blog);
    void deleteById(Long id);
    List<Blog> findByTitleContaining(String title);
}