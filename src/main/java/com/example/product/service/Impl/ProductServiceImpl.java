package com.example.product.service.Impl;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;

     public ProductServiceImpl(ProductRepository productRepository) {
         this.productRepository = productRepository;
     }

    @Override
    public Product addProduct(Product product) {
        try {
            if (product.getStock() == 0) {
                product.setStatus("Out Of Stock");
            } else {
                product.setStatus("Available");
            }
            return productRepository.save(product);
        } catch (Exception e) {
            System.out.println("Error while adding product: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> getProductsByStatus(String status) {
        try {
            return productRepository.findByStatus(status);
        } catch (Exception e) {
            System.out.println("Error while getting products by status: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    @Override
    public void deleteProduct(int id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new NoSuchElementException("Product not found with ID: " + id);
            }

            productRepository.deleteById(id);
            System.out.println("Product deleted successfully with ID: " + id);

        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
            throw e; // rethrow so controller can handle it (e.g., return 404)
        } catch (Exception e) {
            System.err.println("Unexpected error while deleting product with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Error deleting product with ID " + id, e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        try {
            List<Product> products = productRepository.findByCategoryNative(category);

            if (products == null || products.isEmpty()) {
                throw new NoSuchElementException("No products found for category: " + category);
            }
            return products;

        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
            throw e; // let the controller handle 404-style response
        } catch (Exception e) {
            System.err.println("Unexpected error while fetching products by category '" + category + "': " + e.getMessage());
            throw new RuntimeException("Error fetching products by category: " + category, e);
        }
    }

    @Override
    public List<Product> getProductsByCategoryManual(String category) {
        try {
            List<Product> products = getAllProduct();
            if (products == null || products.isEmpty()) {
                throw new NoSuchElementException("No products available in the system.");
            }

            Map<String, List<Product>> categoryMap = new HashMap<>();

            for (Product product : products) {
                String productCategory = product.getCategory();
                categoryMap.putIfAbsent(productCategory, new ArrayList<>());
                categoryMap.get(productCategory).add(product);
            }

            List<Product> result = categoryMap.getOrDefault(category, new ArrayList<>());
            if (result.isEmpty()) {
                throw new NoSuchElementException("No products found for category: " + category);
            }

            return result;

        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
            throw e; // propagate to controller (e.g., for 404)
        } catch (Exception e) {
            System.err.println("Unexpected error while manually fetching products for category '"
                    + category + "': " + e.getMessage());
            throw new RuntimeException("Error manually fetching products for category: " + category, e);
        }
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
        try {
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
                System.err.println("Product not found with ID: " + id);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error while updating product: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> getLowStockProducts(int stockLimit) {
        try {
            return productRepository.findByStockLessThan(stockLimit);
        } catch (Exception e) {
            System.err.println("Error while getting low-stock products: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
