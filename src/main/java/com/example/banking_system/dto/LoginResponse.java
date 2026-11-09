package com.example.banking_system.dto;

import jakarta.validation.constraints.NotNull;

public record LoginResponse (
    Long id,
    String username,
    String message,
    String token
){}
