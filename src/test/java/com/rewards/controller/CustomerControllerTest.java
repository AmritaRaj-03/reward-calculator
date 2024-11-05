package com.rewards.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        List<Customer> response = customerController.getAllCustomers();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Alice", response.get(0).getName());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice", response.getBody().getName());
    }

    @Test
    void testGetTransactionsByCustomerId() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100);
        when(customerService.getTransactionByCustomerId(1L)).thenReturn(Collections.singletonList(transaction));

        ResponseEntity<List<Transaction>> response = customerController.getTransactionsByCustomerId(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(100, response.getBody().get(0).getAmount());
    }

    @Test
    void testCalculateRewards() {
        CustomerRewardsResponse rewardsResponse = new CustomerRewardsResponse();
        rewardsResponse.setCustomerId(1L);
        rewardsResponse.setTotalRewards(120);
        when(customerService.calculateRewards(1L, 3)).thenReturn(rewardsResponse);

        ResponseEntity<CustomerRewardsResponse> response = customerController.calculateRewards(1L, 3);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(120, response.getBody().getTotalRewards());
    }

    @Test
    void testCalculateRewardsForAllCustomers() {
        Map<Long, Integer> rewardsMap = Map.of(1L, 120, 2L, 90);
        when(customerService.calculateRewardsForAllCustomers(3)).thenReturn(rewardsMap);

        ResponseEntity<Map<Long, Integer>> response = customerController.calculateRewardsForAllCustomers(3);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(120, response.getBody().get(1L));
        assertEquals(90, response.getBody().get(2L));
    }
}
