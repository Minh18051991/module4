package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import com.example.blog.service.blog.IBlogService;
import com.example.blog.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BlogApiController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String title) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogs;
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
            blogs = blogService.findByCategory(category, pageable);
        } else if (title != null && !title.isEmpty()) {
            blogs = blogService.findByTitleContaining(title, pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/blogs")
    public ResponseEntity<Page<Blog>> getBlogsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        Page<Blog> blogs = blogService.findByCategory(category, pageable);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        return blog.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/blogs/search")
    public ResponseEntity<Page<Blog>> searchBlogs(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findByTitleContaining(title, pageable);
        return ResponseEntity.ok(blogs);
    }
}