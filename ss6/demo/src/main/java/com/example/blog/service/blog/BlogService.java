package com.example.blog.service.blog;

import com.example.blog.model.AppUser;
import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import com.example.blog.repository.IAppUserRepository;
import com.example.blog.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {
    @Autowired
    private IBlogRepository blogRepository;
    


    // Create
    @Override
    public Blog createBlog(Blog blog) {
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    // Read
    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
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

    @Override
    public Page<Blog> findByCategoryId(Long categoryId, Pageable pageable) {
        return blogRepository.findByCategoryId(categoryId, pageable);
    }

    // Update
    @Override
    public Blog updateBlog(Long id, Blog blogDetails) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        
        blog.setTitle(blogDetails.getTitle());
        blog.setContent(blogDetails.getContent());
        blog.setCategory(blogDetails.getCategory());
        blog.setImg(blogDetails.getImg());
        blog.setUpdatedAt(LocalDateTime.now());
        
        return blogRepository.save(blog);
    }

    // Delete
    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    // Additional useful methods
    @Override
    public List<Blog> findByAppUser(AppUser appUser) {
        return blogRepository.findByAppUser(appUser);
    }



}