package com.example.banking.app.payload;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private Long customerId;
    private String accountType;
}
