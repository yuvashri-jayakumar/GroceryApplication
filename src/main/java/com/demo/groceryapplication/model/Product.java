package com.demo.groceryapplication.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private String subcategory;
    private String brand;
    private String unitSize;
    private Double price = 0.0;
    private Integer stockQty;
    private String description;
//    public Product(){
//        System.out.println("Product Object created");
//    }

    public Product(String id, String name, String category, String subcategory, String brand, String unitSize, Double price, Integer stockQty, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.brand = brand;
        this.unitSize = unitSize;
        this.price = price != null? price : 0.0;
        this.stockQty = stockQty;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void testProduct(){
        System.out.println("Product Test successfull");
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
