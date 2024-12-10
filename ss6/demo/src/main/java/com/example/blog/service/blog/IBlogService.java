package com.example.blog.service.blog;

import com.example.blog.model.AppUser;
import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBlogService {
    Blog createBlog(Blog blog);
    Page<Blog> findAll(Pageable pageable);
    Optional<Blog> findById(Long id);
    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Blog> findByTitleContaining(String title, Pageable pageable);
    Page<Blog> findByCategory(Category category, Pageable pageable);
    Page<Blog> findByCategoryId(Long categoryId, Pageable pageable);
    Blog updateBlog(Long id, Blog blogDetails);
    void deleteById(Long id);
    List<Blog> findByAppUser(AppUser appUser);


}