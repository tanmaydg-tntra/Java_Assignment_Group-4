package com.example.product.service.Impl;


import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        if (product.getStock() == 0) {
            product.setStatus("Out Of Stock");
        } else {
            product.setStatus("Available");
        }
        return productRepository.save(product);
    }

   public List<Product> getProductsByStatus(String status){
       return productRepository.findByStatus(status);
    }

}
