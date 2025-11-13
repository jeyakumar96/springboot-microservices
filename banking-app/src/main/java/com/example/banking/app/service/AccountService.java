package com.example.banking.app.service;

import com.example.banking.app.entity.Transaction;

import java.math.BigDecimal;

public interface AccountService {

    Transaction deposit(String accountNumber, BigDecimal amount, String description);

    Transaction withdraw(String accountNumber, BigDecimal amount, String description);

    void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description);
}
