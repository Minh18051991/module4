package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import com.example.blog.service.CategoryService;
import com.example.blog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private final IBlogService blogService;
    private final CategoryService categoryService;

    public BlogController(IBlogService blogService, CategoryService categoryService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
    }
 @GetMapping
public String getAllBlogs(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "3") int size,
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

    List<Category> categories = categoryService.findAll(); // Thay đổi này
    model.addAttribute("blogs", blogs);
    model.addAttribute("categories", categories);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", blogs.getTotalPages());
    model.addAttribute("totalItems", blogs.getTotalElements());
    model.addAttribute("pageSize", size);
    model.addAttribute("categoryId", categoryId);
    model.addAttribute("title", title);
    return "index";
}



    @GetMapping("/new")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("categories", categoryService.findAll());
        return "create";
    }

    @PostMapping
    public String saveBlog(@ModelAttribute Blog blog, @RequestParam Long categoryId) {
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        blog.setCategory(category);
        blog.setCreatedAt(LocalDateTime.now());
        blogService.save(blog);
        return "redirect:/blogs";
    }

@GetMapping("/edit/{id}")
    public String editBlogForm(@PathVariable Long id, Model model) {
        Optional<Blog> blog = blogService.findById(id);
        model.addAttribute("blog", blog.get());
        model.addAttribute("categories", categoryService.findAll());
        return "edit";
    }
    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable Long id, @ModelAttribute Blog blog) {
        Blog existingBlog = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        if (blog.getCategory() != null && blog.getCategory().getId() != null) {
            Category category = categoryService.findById(blog.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id"));
            existingBlog.setCategory(category);
        }

        existingBlog.setUpdatedAt(LocalDateTime.now());

        blogService.save(existingBlog);

        return "redirect:/blogs";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteById(id);
        return "redirect:/blogs";
    }

    @GetMapping("/search")
    public String searchBlogs(@RequestParam String title, Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.sort(Blog.class).by(Blog::getCreatedAt).descending());
        model.addAttribute("blogs", blogService.findByTitleContaining(title, pageable));
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }

    @GetMapping("/category/{id}")
    public String listBlogsByCategory(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 2; // Set the number of blogs per page
        Pageable pageable = PageRequest.of(page, pageSize);
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("blogs", blogService.findByCategory(category, pageable));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currentCategoryId", id); // Add current category ID to the model
        return "index";
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        model.addAttribute("blog", blog);
        model.addAttribute("category", blog.getCategory().getName());
        return "view_blog";
    }
}