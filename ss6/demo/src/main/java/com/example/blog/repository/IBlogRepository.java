package com.example.blog.repository;

import com.example.blog.model.AppUser;
import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findByTitleContaining(String title, Pageable pageable);

    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Blog> findByCategory(Category category, Pageable pageable);

    Page<Blog> findByCategoryId(Long categoryId, Pageable pageable);

    List<Blog> findByAppUser(AppUser appUser);
}