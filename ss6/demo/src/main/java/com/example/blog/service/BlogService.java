package com.example.blog.service;

import com.example.blog.model.Blog;
import com.example.blog.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {
    @Autowired
    private IBlogRepository blogRepository;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    public List<Blog> findByTitleContaining(String title) {
        return blogRepository.findByTitleContaining(title);
    }
}