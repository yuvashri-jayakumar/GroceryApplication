package com.demo.groceryapplication.repo;

import com.demo.groceryapplication.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomProductRepository {

    public List<Product> getProductsByName(String name);
}
