package com.example.banking_system.dto;

import com.example.banking_system.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferResponse {
    private String message;
    private Account sender;
    private Account recipient;
}
