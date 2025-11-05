package com.demo.groceryapplication.service.impl;

import com.demo.groceryapplication.model.Product;
import com.demo.groceryapplication.repo.CustomProductRepository;
import com.demo.groceryapplication.repo.ProductRepository;
import com.demo.groceryapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomProductRepository customProductRepository;

    public List<Product> getProducts() {

        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> getProductsByName(String name) {
        return customProductRepository.getProductsByName(name);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
