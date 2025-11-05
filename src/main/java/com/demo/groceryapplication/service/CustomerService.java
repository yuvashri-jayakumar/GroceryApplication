package com.demo.groceryapplication.service;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CustomerService {

    List<Customer> findAll();

    Customer addCustomer(Customer customer);

    Customer updateCustomer(long id, Customer customer);

    ResponseEntity<String> deleteCustomer(long id);


    ResponseEntity<String> deleteAddress(long customerId, String type);

    ResponseEntity<String> addOrUpdateAddress(long customerId, Address address);


    Customer searchCustomerByIdPhoneEmail(Customer customer);
}
