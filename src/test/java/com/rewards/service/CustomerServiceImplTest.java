package com.rewards.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;

import com.rewards.dto.CustomerRewardsResponse;

import java.util.Optional;

public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerDao;

	@Mock
	private TransactionRepository transactionRepo;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test
	}

	@Test
	public void testCalculateRewardsForCustomer() {
		Long customerId = 1L;

		// Mock the customer repository to return a valid customer
		Customer mockCustomer = new Customer();
		mockCustomer.setId(customerId);
		when(customerDao.findById(customerId)).thenReturn(Optional.of(mockCustomer));

		// Prepare the mock transactions
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(1L, 120.0, LocalDateTime.now(), null));
		transactions.add(new Transaction(2L, 80.0, LocalDateTime.now(), null));

		// Mock the transaction repository to return these transactions
		when(transactionRepo.findByCustomerIdAndTransactionDateBetween(anyLong(), any(), any()))
				.thenReturn(transactions);

		// Call the method under test
		CustomerRewardsResponse response = customerService.calculateRewards(customerId, 3);

		// Assert the values
		assertEquals(120, response.getTotalRewards());
		assertEquals(customerId, response.getCustomerId());
		assertEquals(transactions, response.getTransactions());
	}

	@Test
	public void testInvalidMonthsShouldThrowException() {
		Long customerId = 1L;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			customerService.calculateRewards(customerId, 13);
		});

		String expectedMessage = "Months must be between 1 and 12.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testCalculateRewardsWithNoTransactions() {
		Long customerId = 1L;
		Customer mockCustomer = new Customer();
		mockCustomer.setId(customerId);
		when(customerDao.findById(customerId)).thenReturn(Optional.of(mockCustomer));
		when(transactionRepo.findByCustomerIdAndTransactionDateBetween(anyLong(), any(), any()))
				.thenReturn(new ArrayList<>()); // No transactions

		CustomerRewardsResponse response = customerService.calculateRewards(customerId, 3);

		assertEquals(0, response.getTotalRewards()); // Expecting zero rewards
		assertEquals(customerId, response.getCustomerId());
		assertTrue(response.getTransactions().isEmpty()); // Expecting empty transaction list
	}

	@Test
	public void testCustomerNotFound() {
		Long customerId = 1L;
		when(customerDao.findById(customerId)).thenReturn(Optional.empty()); // No customer found

		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			customerService.calculateRewards(customerId, 3);
		});

		String expectedMessage = "Customer not found";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
