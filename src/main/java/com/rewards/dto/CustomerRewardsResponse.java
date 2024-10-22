package com.rewards.dto;

import java.util.List;

import com.rewards.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsResponse {
	private Long customerId;
	private List<Transaction> transactions;
	private int totalRewards;
}
