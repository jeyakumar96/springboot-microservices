package com.example.banking.app.controller;


import com.example.banking.app.entity.Account;
import com.example.banking.app.entity.Transaction;
import com.example.banking.app.payload.CreateAccountRequest;
import com.example.banking.app.payload.TransferRequest;
import com.example.banking.app.repository.AccountRepository;
import com.example.banking.app.service.impl.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepo;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest req) {
        Account a = accountService.createAccount(req.getCustomerId(), req.getAccountType());
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }

    @PostMapping("/{acc}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable("acc") String acc,
                                               @RequestParam BigDecimal amount,
                                               @RequestParam(required=false) String desc) {
        return ResponseEntity.ok(accountService.deposit(acc, amount, desc));
    }

    @PostMapping("/{acc}/withdraw")
    public ResponseEntity<Transaction> withdraw(@PathVariable("acc") String acc,
                                                @RequestParam BigDecimal amount,
                                                @RequestParam(required=false) String desc) {
        return ResponseEntity.ok(accountService.withdraw(acc, amount, desc));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        accountService.transfer(req.getFromAccount(), req.getToAccount(), req.getAmount(), req.getDescription());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{acc}/balance")
    public ResponseEntity<BigDecimal> balance(@PathVariable("acc") String acc) {
        Account a = accountRepo.findByAccountNumber(acc).orElseThrow(() -> new RuntimeException("Account not found"));
        return ResponseEntity.ok(a.getBalance());
    }

    @GetMapping("/{acc}/transactions")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable("acc") String acc) {
        Account a = accountRepo.findByAccountNumber(acc).orElseThrow(() -> new RuntimeException("Account not found"));
        return ResponseEntity.ok(a.getTransactions());
    }
}
