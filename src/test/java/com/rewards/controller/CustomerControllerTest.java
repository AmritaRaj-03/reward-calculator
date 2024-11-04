package com.rewards.controller;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Customer;
import com.rewards.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class CustomerControllerTest {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> mockCustomers = new ArrayList<>();
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("John Doe");
		mockCustomers.add(customer);

		when(customerService.getAllCustomers()).thenReturn(mockCustomers);

		List<Customer> customers = customerController.getAllCustomers();

		assertEquals(1, customers.size());
		assertEquals("John Doe", customers.get(0).getName());
		verify(customerService, times(1)).getAllCustomers();
	}

	@Test
	public void testGetCustomerById() {
		Long customerId = 1L;
		Customer mockCustomer = new Customer();
		mockCustomer.setId(customerId);
		mockCustomer.setName("John Doe");

		when(customerService.getCustomerById(customerId)).thenReturn(mockCustomer);

		ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

		assertEquals(mockCustomer, response.getBody());
		assertEquals(200, response.getStatusCode().value());
		verify(customerService, times(1)).getCustomerById(customerId);
	}

	@Test
	public void testCalculateRewards() {
		Long customerId = 1L;
		CustomerRewardsResponse mockResponse = new CustomerRewardsResponse();
		mockResponse.setCustomerId(customerId);
		mockResponse.setTotalRewards(120);
		mockResponse.setTransactions(new ArrayList<>());

		when(customerService.calculateRewards(customerId, 3)).thenReturn(mockResponse);

		ResponseEntity<CustomerRewardsResponse> response = customerController.calculateRewards(customerId, 3);

		assertEquals(mockResponse, response.getBody());
		assertEquals(200, response.getStatusCode().value());
		verify(customerService, times(1)).calculateRewards(customerId, 3);
	}
	

}
