package com.example.banking_system.controller;

import com.example.banking_system.dto.TransferResponse;
import com.example.banking_system.entity.Account;
import com.example.banking_system.entity.AppUser;
import com.example.banking_system.entity.Transaction;
import com.example.banking_system.repository.accountsRepo;
import com.example.banking_system.service.AccountsService;
import com.example.banking_system.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account){
        return accountsService.createAccount(account);
    }

    @GetMapping("/accounts")
    public List<Account> getAllaccounts(){
        return accountsService.getAllBooks();
    }

    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountsService.getAccountsById(id);
    }

    @PutMapping("/deposit/{id}")
    public Account deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountsService.deposit(id, amount);
    }

    @PutMapping("withdraw/{id}")
    public Account withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountsService.withdraw(id, amount);
    }

    @PutMapping("/transfer/{s_id}/{r_id}")
    public TransferResponse transferMoney(@PathVariable  Long s_id,@PathVariable Long r_id, @RequestParam BigDecimal amount){
        return accountsService.moneyTransfer(s_id, r_id, amount);
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id){
        return accountsService.getTransactionHistory(id);
    }

}
