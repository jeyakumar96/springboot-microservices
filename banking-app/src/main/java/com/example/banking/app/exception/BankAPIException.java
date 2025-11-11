package com.example.banking.app.exception;

import org.springframework.http.HttpStatus;

public class BankAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BankAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BankAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}