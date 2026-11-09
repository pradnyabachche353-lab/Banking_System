package com.example.banking_system.dto;

public record RegisterResponse(
        Long id,
        String username,
        String message
) {}