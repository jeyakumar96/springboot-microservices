package com.example.banking.app.service;

import com.example.banking.app.entity.User;
import com.example.banking.app.payload.CreateCustomerRequest;

import java.util.List;

public interface CustomerService {
    User createCustomer(CreateCustomerRequest req);
    List<User> listCustomers();
}

