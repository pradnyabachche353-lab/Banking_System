package com.example.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank (message = "Full namee is required ")
        String fullname,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is Required")
        @Size(min=8, message = "Password must be at least 8 character")
        String password
) {}
