package com.example.banking_system.repository;

import com.example.banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface accountsRepo extends JpaRepository<Account, Long> {
    List<Account> findByAppUser_Id(Long userId);

}
