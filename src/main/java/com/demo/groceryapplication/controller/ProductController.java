package com.demo.groceryapplication.controller;

import com.demo.groceryapplication.model.Product;
import com.demo.groceryapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> listProducts(){
        System.out.println("Getting All Products");
        return productService.getProducts();

    }

    @GetMapping("/search")
    public List<Product> getProductsByName(@RequestParam String name){
        System.out.println("Getting Products By Name" +  name);
        return productService.getProductsByName(name);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
       Product newProduct = productService.addProduct(product);
        System.out.println("New Product Added successfully "+ newProduct.getName());
        return newProduct;
    }

    @PutMapping
    public Product updateProductById(@RequestBody Product product){
        Product newProduct = productService.updateProduct(product);
        System.out.println("Product updated successfully "+ newProduct.getName());
        return newProduct;
    }

    @DeleteMapping("{id}")
    public void deleteProductById(@PathVariable String id){
        productService.deleteProduct(id);
        System.out.println("Product deleted successfully "+ id);
    }
}
