package com.demo.groceryapplication;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.repo.CustomerRepository;
import com.demo.groceryapplication.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerServiceApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();

        testCustomer = new Customer();
        testCustomer.setName("yuva");
        testCustomer.setEmail("yuva@gmail.com");
        testCustomer.setPhone("123456789");
        testCustomer.setAddresses(List.of(
                new Address("123", "street", "area", "city", "state", "600066", "home")
        ));
        testCustomer.setCreatedAt(new Date());

        testCustomer = customerRepository.save(testCustomer);
    }

    @Test
    void shouldReturnAllCustomers(){

       List<Customer> customers = customerService.findAll();
       assertThat(customers).hasSize(1);
    }

    @Test
    void shouldAddCustomer(){
        Customer newCustomer = new Customer();
        newCustomer.setName("magil");
        newCustomer.setEmail("magil@gmail.com");
        newCustomer.setPhone("123456");
        newCustomer.setCreatedAt(new Date());
        Customer savedCustomer  = customerService.addCustomer(newCustomer);
        assertThat(savedCustomer.getId()).isNotZero();
        assertThat(customerService.findAll()).hasSize(2);
    }




}
