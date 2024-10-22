package com.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewards.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
