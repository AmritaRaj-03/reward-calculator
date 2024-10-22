package com.rewards.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewards.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByCustomerId(Long customerId);

	List<Transaction> findAll();

	List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDateTime startDate,
			LocalDateTime endDate);

	List<Transaction> findAllByTransactionDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime2);
}
