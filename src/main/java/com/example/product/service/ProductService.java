package com.example.product.service;


import com.example.product.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getProductsByStatus(String status);
    void deleteProduct(int id);
    List<Product> getProductsByCategory(String category);
}
