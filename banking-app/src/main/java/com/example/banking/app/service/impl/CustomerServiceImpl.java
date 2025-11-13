package com.example.banking.app.service.impl;

import com.example.banking.app.entity.Account;
import com.example.banking.app.entity.Role;
import com.example.banking.app.entity.User;
import com.example.banking.app.payload.CreateCustomerRequest;
import com.example.banking.app.repository.RoleRepository;
import com.example.banking.app.repository.UserRepository;
import com.example.banking.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createCustomer(CreateCustomerRequest req) {
        Objects.requireNonNull(req, "Request cannot be null");

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setFullName(
                (req.getFirstName() != null ? req.getFirstName() : "") +
                        (req.getLastName() != null ? (" " + req.getLastName()) : "")
        );
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User saved = userRepository.save(user);

        // Create a default account
        String accType = (req.getAccountType() == null || req.getAccountType().isBlank()) ? "SAVINGS" : req.getAccountType();
        if (saved.getAccount() == null) {
            Account account = new Account();
            account.setAccountNumber("ACCT" + System.currentTimeMillis());
            account.setAccountType(accType);
            account.setUser(saved);
            account.setBalance(java.math.BigDecimal.ZERO);
            saved.setAccount(account);
            userRepository.save(saved);
        }

        return saved;
    }

    @Override
    public List<User> listCustomers() {
        return userRepository.findAllByRoles_Name("ROLE_USER");
    }
}
