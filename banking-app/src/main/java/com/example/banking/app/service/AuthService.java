package com.example.banking.app.service;


import com.example.banking.app.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}