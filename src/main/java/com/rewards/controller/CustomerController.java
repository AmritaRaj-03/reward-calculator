package com.rewards.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	// Get customer by ID
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

	// Get transactions by customer ID
	@GetMapping("/{customerId}/transactions")
	public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable Long customerId) {
		List<Transaction> transactions = customerService.getTransactionByCustomerId(customerId);
		return ResponseEntity.ok(transactions);
	}

	// Calculate rewards for a specific customer
	@GetMapping("/{customerId}/rewards")
	public ResponseEntity<CustomerRewardsResponse> calculateRewards(@PathVariable Long customerId,
			@RequestParam(value = "months", defaultValue = "3") int months) {
		CustomerRewardsResponse rewardsResponse = customerService.calculateRewards(customerId, months);
		return ResponseEntity.ok(rewardsResponse);
	}

	// Calculate total rewards for all customers
	@GetMapping("/rewards")
	public ResponseEntity<Map<Long, Integer>> calculateRewardsForAllCustomers(
			@RequestParam(value = "months", defaultValue = "3") int months) {
		Map<Long, Integer> rewardsMap = customerService.calculateRewardsForAllCustomers(months);
		return ResponseEntity.ok(rewardsMap);
	}
}
