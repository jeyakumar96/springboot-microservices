package com.example.banking.app.repository;

import com.example.banking.app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountNumber(String accountNumber);

  Optional<Account> findByUserId(Long userId);
}
