package com.example.product.controller;


import com.example.product.model.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.addProduct(product));
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<Product>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getProductsByStatus(status));
    }



}
