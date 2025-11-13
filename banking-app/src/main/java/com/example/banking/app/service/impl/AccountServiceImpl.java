package com.example.banking.app.service.impl;

import com.example.banking.app.entity.Account;
import com.example.banking.app.entity.User;
import com.example.banking.app.entity.Transaction;
import com.example.banking.app.exception.ApiException;
import com.example.banking.app.repository.AccountRepository;
import com.example.banking.app.repository.UserRepository;
import com.example.banking.app.repository.TransactionRepository;
import com.example.banking.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    @Override
    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount, String description) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("CREDIT");
        transaction.setDescription(description);
        transaction.setReferenceId(UUID.randomUUID().toString());

        return txRepo.save(transaction);
    }

    @Override
    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount, String description) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("DEBIT");
        transaction.setDescription(description);
        transaction.setReferenceId(UUID.randomUUID().toString());

        return txRepo.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new ApiException("Cannot transfer to the same account");
        }

        withdraw(fromAccountNumber, amount, "Transfer to " + toAccountNumber + " - " + description);
        deposit(toAccountNumber, amount, "Transfer from " + fromAccountNumber + " - " + description);
    }

}
