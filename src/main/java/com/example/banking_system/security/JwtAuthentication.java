package com.example.banking_system.security;

import com.example.banking_system.entity.AppUser;
import com.example.banking_system.repository.AppUserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class JwtAuthentication extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )throws ServletException, IOException{
        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=authHeader.substring(7);
        String username;
        try {
            username=jwtService.extractUsername(token);
        }catch (Exception e){
            e.printStackTrace();
            filterChain.doFilter(request,response);
            return;
        }

        Optional<AppUser> optionalUser=appUserRepo.findByUsername(username);

        if(optionalUser.isPresent()){
            AppUser user=optionalUser.get();
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .build();

            if(jwtService.isTokenValid(token,userDetails.getUsername()) && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                   userDetails,
                   null,
                   userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
