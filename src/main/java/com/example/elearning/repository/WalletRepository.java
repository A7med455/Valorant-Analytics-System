package com.example.elearning.repository;

import com.example.elearning.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Used in WalletService.getBalance() and WalletService.topUp()
    // Traverses Wallet → user → Id via JPA derived query
    Wallet findByUser_Id(Long userId);