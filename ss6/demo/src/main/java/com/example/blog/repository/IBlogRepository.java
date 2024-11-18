package com.example.blog.repository;

import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContaining(String title);

    Page<Blog> findByTitleContaining(String title, Pageable pageable);

    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Blog> findByCategory(Category category, Pageable pageable);

    @Override
    Blog save(Blog blog);

    @Override
    List<Blog> findAll();

    @Override
    Optional<Blog> findById(Long id);

    @Override
    void deleteById(Long id);
}