package com.example.banking.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    private String type; // CREDIT / DEBIT / TRANSFER
    private BigDecimal amount;
    private String description;

    @ManyToOne
    @JsonBackReference
    private Account account;

    private String referenceId;
}
