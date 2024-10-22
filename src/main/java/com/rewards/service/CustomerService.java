package com.rewards.service;

import java.util.List;
import java.util.Map;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;

public interface CustomerService {

	Customer getCustomerById(Long id);

	List<Transaction> getTransactionByCustomerId(Long customerId);

	CustomerRewardsResponse calculateRewards(Long customerId, int months);

	Map<Long, Integer> calculateRewardsForAllCustomers(int months);

	List<Customer> getAllCustomers();
}
