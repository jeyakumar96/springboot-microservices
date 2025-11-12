package com.example.banking.app.service.impl;


import com.example.banking.app.entity.Account;
import com.example.banking.app.entity.Customer;
import com.example.banking.app.entity.Transaction;
import com.example.banking.app.exception.ApiException;
import com.example.banking.app.repository.AccountRepository;
import com.example.banking.app.repository.CustomerRepository;
import com.example.banking.app.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final TransactionRepository txRepo;

    @Transactional
    public Account createAccount(Long customerId, String type) {
        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new ApiException("Customer not found"));
        Account a = new Account();
        a.setAccountNumber(generateAccountNumber());
        a.setAccountType(type);
        a.setCustomer(c);
        a.setBalance(BigDecimal.ZERO);
        return accountRepo.save(a);
    }

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount, String desc) {
        Account a = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));
        a.setBalance(a.getBalance().add(amount));
        accountRepo.save(a);
        Transaction tx = new Transaction();
        tx.setAccount(a);
        tx.setAmount(amount);
        tx.setType("CREDIT");
        tx.setDescription(desc);
        tx.setReferenceId(UUID.randomUUID().toString());
        return txRepo.save(tx);
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount, String desc) {
        Account a = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));
        if (a.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient funds");
        }
        a.setBalance(a.getBalance().subtract(amount));
        accountRepo.save(a);
        Transaction tx = new Transaction();
        tx.setAccount(a);
        tx.setAmount(amount);
        tx.setType("DEBIT");
        tx.setDescription(desc);
        tx.setReferenceId(UUID.randomUUID().toString());
        return txRepo.save(tx);
    }

    @Transactional
    public void transfer(String fromAccNum, String toAccNum, BigDecimal amount, String desc) {
        if (fromAccNum.equals(toAccNum)) throw new ApiException("Cannot transfer to same account");
        withdraw(fromAccNum, amount, "Transfer to " + toAccNum + " - " + desc);
        deposit(toAccNum, amount, "Transfer from " + fromAccNum + " - " + desc);
    }

    private String generateAccountNumber() {
        return "ACCT" + System.currentTimeMillis();
    }
}
