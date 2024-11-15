package com.example.blog.repository;

import com.example.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContaining(String title);

    @Override
    Blog save(Blog blog);

    @Override
    List<Blog> findAll(); // Read all

    @Override
    Optional<Blog> findById(Long id); // Read by ID

    @Override
    void deleteById(Long id); // Delete by ID
}