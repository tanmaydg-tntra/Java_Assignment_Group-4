package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatus(String status);
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(int minPrice, int maxPrice);
    List<Product> findByPriceLessThanEqual(int maxPrice);
    List<Product> findByPriceGreaterThanEqual(int minPrice);
}
