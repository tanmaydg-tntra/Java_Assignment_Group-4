package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatus(String status);

    @Query(value = "SELECT * FROM products WHERE category= :category ", nativeQuery = true)
    List<Product> findByCategoryNative(@Param("category") String category);
    List<Product> findByPriceBetween(int minPrice, int maxPrice);
    List<Product> findByPriceLessThanEqual(int maxPrice);
    List<Product> findByPriceGreaterThanEqual(int minPrice);
    List<Product> findByStockLessThan(int stockLimit);

}
