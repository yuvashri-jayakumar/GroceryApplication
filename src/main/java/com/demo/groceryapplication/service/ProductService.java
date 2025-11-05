package com.demo.groceryapplication.service;

import com.demo.groceryapplication.model.Product;

import java.util.List;


public interface ProductService {

    public List<Product> getProducts();

    public List<Product> getProductsByName(String name) ;
    public Product addProduct(Product product);

    public Product updateProduct(Product product);

    public void deleteProduct(String id);
}
