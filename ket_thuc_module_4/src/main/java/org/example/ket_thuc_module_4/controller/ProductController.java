package org.example.ket_thuc_module_4.controller;

import org.example.ket_thuc_module_4.model.Product;
import org.example.ket_thuc_module_4.model.Category;
import org.example.ket_thuc_module_4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model, 
                               @RequestParam(defaultValue = "0") int page, 
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(required = false) String keyword) {
        Page<Product> productPage;
        if (keyword != null && !keyword.isEmpty()) {
            productPage = productService.findByNameContaining(keyword, PageRequest.of(page, size));
        } else {
            productPage = productService.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("products", productPage);
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultipleProducts(@RequestParam("ids") List<Long> ids) {
        productService.deleteAllById(ids);
        return "redirect:/products";
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return productService.getAllCategories();
    }
}