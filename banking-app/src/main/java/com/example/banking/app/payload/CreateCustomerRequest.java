package com.example.banking.app.payload;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String accountType; // optional, default SAVINGS if null
}
