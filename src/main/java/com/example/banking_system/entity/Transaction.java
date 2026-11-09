package com.example.banking_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String status;

    @ManyToOne
    @JoinColumn(name="sender_account_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver_account_id")
    private Account receiver;


}
