package com.example.product.service.Impl;


import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;



     public ProductServiceImpl(ProductRepository productRepository) {
         this.productRepository = productRepository;
     }

    @Override
    public Product addProduct(Product product) {
        if (product.getStock() == 0) {
            product.setStatus("Out Of Stock");
        } else {
            product.setStatus("Available");
        }
        return productRepository.save(product);
    }

  @Override
   public List<Product> getProductsByStatus(String status){
        return productRepository.findByStatus(status);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByCategory (String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductByPriceRange(Integer min, Integer max){
        if(min != null && max != null){
            return productRepository.findByPriceBetween(min, max);
        }

        else if(min != null){
            return productRepository.findByPriceGreaterThanEqual(min);
        }

        else if(max != null){
            return productRepository.findByPriceLessThanEqual(max);
        }
        else{
            return productRepository.findAll();
        }
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(int id, Product newProduct) {
        Product existing = productRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(newProduct.getName());
            existing.setPrice(newProduct.getPrice());
            existing.setCategory(newProduct.getCategory());
            existing.setStock(newProduct.getStock());

            if (newProduct.getStock() == 0) {
                existing.setStatus("Out Of Stock");
            } else {
                existing.setStatus("Available");
            }

            return productRepository.save(existing);
        } else {
            return null; // or throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public List<Product> getLowStockProducts(int stockLimit) {
        return productRepository.findByStockLessThan(stockLimit);
    }


}
