package com.example.banking_system.repository;

import com.example.banking_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySender_IdOrReceiverIdOrderByTimestampDesc(Long senderId, Long receiverId);
}
