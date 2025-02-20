package com.example.API.repository;

import com.example.API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findTop10ByOrderBySoldQuantityDesc();
    List<Product> findTop10ByCreatedDateAfterOrderByCreatedDateDesc(LocalDateTime date);
}
