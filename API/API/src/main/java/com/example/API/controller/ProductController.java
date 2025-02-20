package com.example.API.controller;

import com.example.API.model.Category;
import com.example.API.model.Product;
import com.example.API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Hiển thị tất cả danh mục
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return productService.getAllCategories();
    }

    // 2. Hiển thị tất cả sản phẩm theo danh mục
    @GetMapping("/categories/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // 3. Hiển thị 10 sản phẩm có số lượng bán nhiều nhất
    @GetMapping("/top-selling")
    public List<Product> getTopSellingProducts() {
        return productService.getTopSellingProducts();
    }

    // 4. Hiển thị 10 sản phẩm được tạo trong vòng 7 ngày
    @GetMapping("/recent")
    public List<Product> getRecentProducts() {
        return productService.getRecentProducts();
    }
}
