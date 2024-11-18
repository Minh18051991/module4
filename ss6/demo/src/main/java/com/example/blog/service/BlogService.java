// src/main/java/com/example/blog/service/BlogService.java
package com.example.blog.service;

import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import com.example.blog.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {
    @Autowired
    private IBlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> findByTitleContaining(String title) {
        return blogRepository.findByTitleContaining(title);
    }

    @Override
    public Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        return blogRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Blog> findByTitleContaining(String title, Pageable pageable) {
        return blogRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public Page<Blog> findByCategory(Category category, Pageable pageable) {
        return blogRepository.findByCategory(category, pageable);
    }

}