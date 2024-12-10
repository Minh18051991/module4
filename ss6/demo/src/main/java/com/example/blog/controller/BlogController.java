package com.example.blog.controller;

import com.example.blog.model.AppUser;
import com.example.blog.model.Blog;
import com.example.blog.service.blog.IBlogService;
import com.example.blog.service.category.ICategoryService;
import com.example.blog.service.user.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IAppUserService appUserService;

    // Hiển thị danh sách blog
    @GetMapping
    public String getAllBlogs(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findAll(pageable);
        model.addAttribute("blogs", blogs);
        return "index";
    }

    // Hiển thị form tạo blog mới
    @GetMapping("/new")
    public String showNewBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("categories", categoryService.findAll());
        return "create";
    }

    // Tạo blog mới
    @PostMapping("/new")
    public String createNewBlog(@ModelAttribute Blog blog,
                                @RequestParam("file") MultipartFile file,
                                @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserService.findByUserName(userDetails.getUsername());
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        // Kiểm tra các trường bắt buộc
        if (blog.getTitle() == null || blog.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Blog title is required");
        }
        if (blog.getContent() == null || blog.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Blog content is required");
        }
        if (blog.getCategory() == null) {
            throw new IllegalArgumentException("Blog category is required");
        }

        // Xử lý upload file
        if (!file.isEmpty()) {
            try {
                // Lưu file
                byte[] bytes = file.getBytes();
                Path path = Paths.get("uploads/" + file.getOriginalFilename());
                Files.write(path, bytes);

                // Lưu đường dẫn file vào blog
                blog.setImg(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        blog.setAppUser(currentUser);
        Blog createdBlog = blogService.createBlog(blog);
        return "redirect:/blogs/" + createdBlog.getId();
    }

    // Hiển thị chi tiết blog
    @GetMapping("/{id}")
    public String showBlogDetails(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        model.addAttribute("blog", blog);
        return "view_blog";
    }

    // Hiển thị form chỉnh sửa blog
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Blog blog = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        AppUser currentUser = appUserService.findByUserName(userDetails.getUsername());
        if (currentUser == null || !blog.getAppUser().getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("You don't have permission to edit this blog");
        }

        model.addAttribute("blog", blog);
        model.addAttribute("categories", categoryService.findAll());
        return "edit";
    }

    // Cập nhật blog
    @PostMapping("/{id}/edit")
    public String updateBlog(@PathVariable Long id,
                             @ModelAttribute Blog blog,
                             @RequestParam("file") MultipartFile file,
                             @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserService.findByUserName(userDetails.getUsername());
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        Blog existingBlog = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        if (!existingBlog.getAppUser().getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("You don't have permission to edit this blog");
        }

        // Kiểm tra các trường bắt buộc
        if (blog.getTitle() == null || blog.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Blog title is required");
        }
        if (blog.getContent() == null || blog.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Blog content is required");
        }
        if (blog.getCategory() == null) {
            throw new IllegalArgumentException("Blog category is required");
        }

        // Xử lý upload file mới (nếu có)
        if (!file.isEmpty()) {
            try {
                // Lưu file mới
                byte[] bytes = file.getBytes();
                Path path = Paths.get("uploads/" + file.getOriginalFilename());
                Files.write(path, bytes);

                // Cập nhật đường dẫn file mới vào blog
                blog.setImg(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Giữ nguyên ảnh cũ nếu không có file mới
            blog.setImg(existingBlog.getImg());
        }

        Blog updatedBlog = blogService.updateBlog(id, blog);
        return "redirect:/blogs/" + updatedBlog.getId();
    }

    // Xóa blog
    @PostMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserService.findByUserName(userDetails.getUsername());
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        Blog blog = blogService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        if (!blog.getAppUser().getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("You don't have permission to delete this blog");
        }

        blogService.deleteById(id);
        return "redirect:/blogs";
    }
}