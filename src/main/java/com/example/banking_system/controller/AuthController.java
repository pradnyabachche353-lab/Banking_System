package com.example.banking_system.controller;

import com.example.banking_system.dto.LoginRequest;
import com.example.banking_system.dto.LoginResponse;
import com.example.banking_system.dto.RegisterRequest;
import com.example.banking_system.dto.RegisterResponse;
import com.example.banking_system.entity.AppUser;
import com.example.banking_system.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AppUserService appUserService;

    @PostMapping("/auth/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        System.out.println("register controller reached");

        AppUser savedUser=appUserService.registerUser(request);

        return new RegisterResponse(
             savedUser.getId(),
             savedUser.getUsername(),
                "Registration succssful"
        );
    }
    @PostMapping("/auth/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return appUserService.loginUser(request);
    }
}
