package com.demo.groceryapplication.controller;

import com.demo.groceryapplication.model.Address;
import com.demo.groceryapplication.model.Customer;
import com.demo.groceryapplication.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        System.out.println("Method called");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Method executed");
    }

    @Test
    void shouldReturnAllCustomers() throws Exception {
        //given

        List<Customer> mockCustomers = List.of(
                new Customer("yuva", "yuva@gmail.com", "97512124",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "home")),
                        new Date()),
                new Customer("magil", "magil@gmail.com", "123456",
                        List.of(new Address("456", "street", "area", "city", "state", "6123001", "home")),
                        new Date())
        );
        given(customerService.findAll()).willReturn(mockCustomers);
        //when


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers").accept(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldAddCustomer() throws Exception {
        Customer mockCustomer =
                new Customer("yuva", "yuva@gmail.com", "97512124",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "Home")),
                        new Date());
        Customer newCustomer =
                new Customer("yuva", "yuva@gmail.com", "97512124",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "Home")),
                        new Date());

        when(customerService.addCustomer(any(Customer.class))).thenReturn(newCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCustomer));

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("yuva"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        long id =1L;
        Customer mockCustomer =
                new Customer("yuva", "yuvasri@gmail.com", "123456789",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "Home")),
                        new Date());


        when(customerService.updateCustomer(any(Long.class), any(Customer.class))).thenReturn(mockCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/customers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCustomer));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("yuvasri@gmail.com"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {
        long id =99L;

        Customer mockCustomer =
                new Customer("yuva", "yuvasri@gmail.com", "123456789",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "Home")),
                        new Date());
        when(customerService.updateCustomer(anyLong(), any(Customer.class))).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/customers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCustomer));

        MvcResult result = mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound())
                                .andReturn();


    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        long id =1L;

        when(customerService.deleteCustomer(eq(id))).thenReturn(ResponseEntity.ok("Deleted Successfully"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/customers/{id}", id);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"))
                .andReturn();


    }

    @Test
    void shouldAddOrUpdateAddress() throws Exception {

        Address address = new Address("123", "street", "area", "city", "state", "600066", "Home");
        long id = 1L;

        Customer mockCustomer =
                new Customer("yuva", "yuvasri@gmail.com", "123456789",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "Home")),
                        new Date());
        when(customerService.addOrUpdateAddress(eq(id),any(Address.class))).thenReturn(ResponseEntity.ok("Address added/updated successfully"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customers/{id}/address",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect( content().string("Address added/updated successfully"))
                .andReturn();


    }

    @Test
    void shouldDeleteAddress() throws Exception {


        long id = 1L;
        when(customerService.deleteAddress(eq(id),any(String.class))).thenReturn(ResponseEntity.ok("Address deleted successfully"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/customers/{id}/address",id)
                .param("type","Home");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect( content().string("Address deleted successfully"))
                .andReturn();


    }

    @Test
    void shouldGetCustomerWhenTheSearchInputMatches() throws Exception {
        long id =1L;
        List<Customer> mockCustomers = List.of(
                new Customer("yuva", "yuva@gmail.com", "97512124",
                        List.of(new Address("123", "street", "area", "city", "state", "600066", "home")),
                        new Date()),
                new Customer("magil", "magil@gmail.com", "123456",
                        List.of(new Address("456", "street", "area", "city", "state", "6123001", "home")),
                        new Date())
        );
        Customer search =
                new Customer(1L, "yuva@gmail.com", "123456");
        when(customerService.searchCustomerByIdPhoneEmail(any(Customer.class))).thenReturn(mockCustomers.iterator().next());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(search));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("yuva@gmail.com"))
                .andReturn();


    }
}