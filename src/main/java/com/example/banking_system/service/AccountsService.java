package com.example.banking_system.service;

import com.example.banking_system.dto.TransferResponse;
import com.example.banking_system.entity.Account;
import com.example.banking_system.entity.AppUser;
import com.example.banking_system.entity.Transaction;
import com.example.banking_system.repository.AppUserRepo;
import com.example.banking_system.repository.TransactionRepo;
import com.example.banking_system.repository.accountsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountsService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private accountsRepo accountsRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    private Account getOwnedAccount(Long id) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getAppUser() == null ||
                !account.getAppUser().getId().equals(appUser.getId())) {

            throw new RuntimeException("Unauthorized account access");
        }

        return account;
    }

    public Account createAccount(Account account){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        AppUser appUser=appUserRepo.findByUsername(username).orElseThrow(()->new RuntimeException("username not found"));

        account.setAppUser(appUser);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedDate(LocalDateTime.now());
        return accountsRepo.save(account);
    }

    public List<Account> getAllBooks() {
            String username = SecurityContextHolder.getContext()
                    .getAuthentication().getName();

            AppUser appUser = appUserRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return accountsRepo.findByAppUser_Id(appUser.getId());

    }

    public Account getAccountsById(Long id) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getAppUser().getId().equals(appUser.getId())) {
            throw new RuntimeException("You are not authorized to access this account");
        }

        return account;
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {

        Account account = getOwnedAccount(id);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        account.setBalance(account.getBalance().add(amount));

        accountsRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType("deposit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");
        transaction.setReceiver(account);

        transactionRepo.save(transaction);

        return account;
    }
    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {

        Account account = getOwnedAccount(id);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountsRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType("withdraw");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");
        transaction.setSender(account);

        transactionRepo.save(transaction);

        return account;
    }
    @Transactional
    public TransferResponse moneyTransfer(Long s_id, Long r_id, BigDecimal amount) {

        Account s_account = getOwnedAccount(s_id);

        Account r_account = accountsRepo.findById(r_id)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (s_account.getId().equals(r_account.getId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (s_account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        s_account.setBalance(s_account.getBalance().subtract(amount));
        r_account.setBalance(r_account.getBalance().add(amount));

        accountsRepo.save(s_account);
        accountsRepo.save(r_account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType("Money_Transfer");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");
        transaction.setSender(s_account);
        transaction.setReceiver(r_account);

        transactionRepo.save(transaction);

        return new TransferResponse(
                "Money Transferred Successfully",
                s_account,
                r_account
        );
    }
    public List<Transaction> getTransactionHistory(Long accountId) {

        Account account = getOwnedAccount(accountId);

        return transactionRepo.findBySender_IdOrReceiverIdOrderByTimestampDesc(
                account.getId(),
                account.getId()
        );
    }
}
