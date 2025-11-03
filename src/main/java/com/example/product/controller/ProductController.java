package com.example.product.controller;


import com.example.product.model.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = service.addProduct(product);
            if (addedProduct == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
            return ResponseEntity.ok(addedProduct);
        } catch (Exception e) {
            System.out.println("Error while adding product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Product>> getByStatus(@PathVariable String status) {
        try {
            List<Product> products = service.getProductsByStatus(status);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.out.println("Error while getting products by status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id){
        service.deleteProduct(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory (@PathVariable String category) {
        return service.getProductsByCategory(category);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> getProductsByPriceRange( @RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {

        return ResponseEntity.ok(service.getProductByPriceRange(min, max));
    }

    @GetMapping
        public ResponseEntity<List<Product>> getAllProduct(){
            return ResponseEntity.ok(service.getAllProduct());
        }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            Product updated = service.updateProduct(id, product);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.out.println("Error while updating product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/low-stock/{stockLimit}")
    public ResponseEntity<List<Product>> getLowStockProducts(@PathVariable int stockLimit) {
        try {
            List<Product> products = service.getLowStockProducts(stockLimit);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ArrayList<>());
        }
    }
}


