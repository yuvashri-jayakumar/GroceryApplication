package com.demo.groceryapplication.controller;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) {
        return customerService.deleteCustomer(id);
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<String> addOrUpdateAddress(@PathVariable long id, @RequestBody Address address) {
        return customerService.addOrUpdateAddress(id, address);
    }


    @DeleteMapping("/{id}/address")
    public ResponseEntity<String> deleteAddress(@PathVariable long id, @RequestParam String type) {
        return customerService.deleteAddress(id, type);
    }

    @GetMapping("/search")
    public Customer searchCustomer(@RequestBody Customer customer) {
        return customerService.searchCustomerByIdPhoneEmail(customer);
    }


}
