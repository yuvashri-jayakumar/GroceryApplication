package com.demo.groceryapplication.repo.impl;

import com.demo.groceryapplication.model.Product;
import com.demo.groceryapplication.repo.CustomProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomProductRepositoryImpl implements CustomProductRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Product> getProductsByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }
}
