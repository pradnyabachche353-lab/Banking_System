package com.example.banking_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private AppUser appUser;
}
