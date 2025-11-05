package com.demo.groceryapplication.service.impl;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.repo.CustomerRepository;
import com.demo.groceryapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return (Customer) customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setAddresses(customer.getAddresses());
            return customerRepository.save(existingCustomer);
        } else {
            return null;
        }


    }

    @Override
    public ResponseEntity<String> deleteCustomer(long id) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            customerRepository.delete(existingCustomer);
            return ResponseEntity.ok("Deleted Successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteAddress(long customerId, String type) {
        try {
            customerRepository.deleteAddress(customerId, type);
            return ResponseEntity.ok("Address deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occured" + e.getMessage());
        }

    }

    @Override
    public ResponseEntity<String> addOrUpdateAddress(long customerId, Address address) {
        if (customerRepository.addressExistsByType(customerId, address.getType())) {
            System.out.println("Address exists");
            //update the customer address
            customerRepository.updateAddress(customerId, address.getType(), address);
        } else {
            customerRepository.addAddress(customerId, address);
        }
        return ResponseEntity.ok("Address added/updated successfully");
    }

    @Override
    public Customer searchCustomerByIdPhoneEmail(Customer customer) {
        return customerRepository.findByIdPhoneEmail(customer.getId(), customer.getPhone(), customer.getEmail());
    }
}
