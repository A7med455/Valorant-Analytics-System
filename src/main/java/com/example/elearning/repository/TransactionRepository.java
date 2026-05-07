package com.example.elearning.repository;

import com.example.elearning.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
    // find all transactions where user_id=(value)
    List<Transaction> findByUser_Id(Long userId);
}
