package com.example.banking_system.service;

import com.example.banking_system.dto.LoginRequest;
import com.example.banking_system.dto.LoginResponse;
import com.example.banking_system.dto.RegisterRequest;
import com.example.banking_system.dto.RegisterResponse;
import com.example.banking_system.entity.AppUser;
import com.example.banking_system.repository.AppUserRepo;
import com.example.banking_system.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser registerUser(RegisterRequest request ) throws IllegalArgumentException {
        if(appUserRepo.findByUsername(request.username()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        AppUser user = new AppUser();
        user.setFullname(request.fullname());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("ROLE_USER");
        return appUserRepo.save(user);
    }
    public LoginResponse loginUser(LoginRequest request) throws IllegalArgumentException {
        AppUser user=appUserRepo.findByUsername(request.username()).orElseThrow(()->new IllegalArgumentException(" Invalid username"));

        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new IllegalArgumentException("Invalid username and password");
        }
        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                "Login Successul",
                token

        );
    }
}
