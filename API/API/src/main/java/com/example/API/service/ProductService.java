package com.example.API.service;

import com.example.API.model.Category;
import com.example.API.model.Product;
import com.example.API.repository.CategoryRepository;
import com.example.API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getTopSellingProducts() {
        return productRepository.findTop10ByOrderBySoldQuantityDesc();
    }

    public List<Product> getRecentProducts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return productRepository.findTop10ByCreatedDateAfterOrderByCreatedDateDesc(sevenDaysAgo);
    }
}
