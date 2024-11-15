package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String getAllBlogs(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "index";
    }

    @GetMapping("/{id}")
    public String getBlogById(@PathVariable Long id, Model model) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            return "blog";
        } else {
            return "error";
        }
    }

    @GetMapping("/new")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "create";
    }

    @PostMapping
    public String saveBlog(@ModelAttribute Blog blog) {
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            return "update";
        } else {
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable Long id, @ModelAttribute Blog blog) {
        blog.setId(id);
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteById(id);
        return "redirect:/blogs";
    }

    @GetMapping("/search")
    public String searchBlogs(@RequestParam String title, Model model) {
        List<Blog> blogs = blogService.findByTitleContaining(title);
        model.addAttribute("blogs", blogs);
        return "index";
    }
}