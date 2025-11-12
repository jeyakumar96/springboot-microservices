package com.example.banking.app.repository;


import com.example.banking.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
