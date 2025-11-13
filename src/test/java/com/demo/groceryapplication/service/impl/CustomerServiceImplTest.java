package com.demo.groceryapplication.service.impl;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.repo.CustomerRepository;
import com.demo.groceryapplication.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private List<Customer> mockCustomers;

    private Customer customer;

    private Address address;
    @BeforeEach
    void setUp(){
         mockCustomers = List.of(
                new Customer("yuva", "yuva@gmail.com", "97512124",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "home")),
                        new Date()),
                new Customer("magil", "magil@gmail.com", "123456",
                        List.of(new Address("456", "street", "area", "city", "state", "6123001", "home")),
                        new Date())
        );
        customer =  new Customer("yuva", "yuva@gmail.com", "97512124",
                List.of(new Address("123", "street", "area", "city", "state", "600066", "home")),
                new Date());

        address = new Address("789", "Gandhi street", "DLF", "chennai", "TN", "600066", "office");
    }

    @Test
    void shouldFindAllCustomers() {

        when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<Customer> actual = customerService.findAll();

        assertEquals( mockCustomers.size(),actual.size());

    }

    @Test
    void shouldAddCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer actual = customerService.addCustomer(customer);

        assertEquals(customer.getName(), actual.getName());


    }

    @Test
    void shouldUpdateCustomerIfCustomerExists() {
        long id = 1L;
        Customer input = new Customer(id,"test@gmail.com","11111");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));


        Customer expected = new Customer("updated", input.getEmail(), input.getPhone(),
                customer.getAddresses(), new Date());
        when(customerRepository.save(customer)).thenReturn(expected);

        Customer actual = customerService.updateCustomer(id, input);

        assertEquals(expected.getName(), actual.getName());
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingCustomer() {
        long id = 99L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        Customer result = customerService.updateCustomer(id, customer);

        Assertions.assertThat(result).isNull();
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).save(any());
    }
    @Test
    void shouldDeleteCustomerWhenExists() {
        long id = 1L;
        String expected = "Deleted Successfully";
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        ResponseEntity<String> result = customerService.deleteCustomer(id);

        assertEquals(expected, result.getBody());
        verify(customerRepository, times(1)).delete(any());
    }

    @Test
    void shouldNotDeleteCustomerWhenNotExists() {
        long id = 1L;
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> result = customerService.deleteCustomer(id);

        Assertions.assertThat(result.getStatusCode().value()).isEqualTo(404);
        verify(customerRepository, never()).delete(any());
    }

    @Test
    void shouldDeleteAddress() {
        long id = 1L;
       // when(customerRepository.deleteAddress(id,address.getType())).thenReturn(ResponseEntity.ok("Address deleted successfully"));
        ResponseEntity<String> result = customerService.deleteAddress(id, "home");
        Assertions.assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertEquals("Address deleted successfully", result.getBody());

        verify(customerRepository, times(1)).deleteAddress(id,customer.getAddresses().getFirst().getType());
    }

    @Test
    void shouldUpdateAddressWhenAddressExists() {
        long id = 1L;
        String type ="office";
        when(customerRepository.addressExistsByType(id, type)).thenReturn(true);

        ResponseEntity<String> result = customerService.addOrUpdateAddress(id,address);

        assertEquals( "Address added/updated successfully", result.getBody());
        verify(customerRepository, times(1)).updateAddress(id,type,address);


    }

@Test
void shouldAddAddressWhenAddressNotExists() {

    long id = 1L;
    when(customerRepository.addressExistsByType(id, "office")).thenReturn(false);

    ResponseEntity<String> result = customerService.addOrUpdateAddress(id, address);

    assertEquals("Address added/updated successfully", result.getBody());
    verify(customerRepository, times(1)).addAddress(id, address);
}

    @Test
    void shouldSearchCustomerByIdPhoneEmail() {
        when(customerRepository.findByIdPhoneEmail(anyLong(), anyString(), anyString())).thenReturn(customer);

        Customer result = customerService.searchCustomerByIdPhoneEmail(customer);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("yuva@gmail.com");
        verify(customerRepository).findByIdPhoneEmail(customer.getId(), customer.getPhone(), customer.getEmail());
    }
}