package com.rewards.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	public CustomerRepository customerDao;

	@Autowired
	public TransactionRepository transactionDao;
	
	@Override
	public List<Customer> getAllCustomers() {
	    return customerDao.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {
		// TODO Auto-generated method stub
		return customerDao.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
	}

	@Override
	public List<Transaction> getTransactionByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return transactionDao.findByCustomerId(customerId);
	}

	 @Override
	    public CustomerRewardsResponse calculateRewards(Long customerId, int months) {
	        // Validate months
	        if (months < 1 || months > 12) {
	            throw new IllegalArgumentException("Months must be between 1 and 12.");
	        }

	        // Fetch the customer
	        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

	        // Fetch transactions for the last 'months' period
	        LocalDate endDate = LocalDate.now();
	        LocalDate startDate = endDate.minusMonths(months);
	        List<Transaction> transactions = transactionDao.findByCustomerIdAndTransactionDateBetween(customerId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());

	        // Calculate reward points
	        int totalPoints = calculateTotalPoints(transactions);

	        // Return a response object containing customerId, transactions, and total rewards
	        return new CustomerRewardsResponse(customerId, transactions, totalPoints);
	    }

	 @Override
	    public Map<Long, Integer> calculateRewardsForAllCustomers(int months) {
	        // Ensure that the months are within a valid range
	        if (months < 1 || months > 12) {
	            throw new IllegalArgumentException("Invalid months value. It should be between 1 and 12.");
	        }

	        Map<Long, Integer> rewardsMap = new HashMap<>();
	        List<Customer> customers = customerDao.findAll();

	        LocalDate endDate = LocalDate.now();
	        LocalDate startDate = endDate.minusMonths(months);

	        for (Customer customer : customers) {
	            List<Transaction> transactions = transactionDao.findByCustomerIdAndTransactionDateBetween(customer.getId(), startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
	            int totalPoints = calculateTotalPoints(transactions);
	            rewardsMap.put(customer.getId(), totalPoints); // Associate customer ID with their reward points
	        }

	        return rewardsMap;
	    }

    // Helper method to calculate total points based on transactions
    private int calculateTotalPoints(List<Transaction> transactions) {
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 50 && amount <= 100) {
                totalPoints += (amount - 50);
            } else if (amount > 100) {
                totalPoints += (50 + (amount - 100) * 2);
            }
        }

        return totalPoints;
    }

}

