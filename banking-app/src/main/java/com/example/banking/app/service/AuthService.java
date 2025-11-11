package com.example.banking.app.service;


import com.example.banking.app.payload.LoginDto;
import com.example.banking.app.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}