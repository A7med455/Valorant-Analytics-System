package com.example.elearning.service;

import com.example.elearning.model.Transaction;
import com.example.elearning.model.User;
import com.example.elearning.repository.TransactionRepository;
import com.example.elearning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService( TransactionRepository transactionRepository,UserRepository userRepository)
    {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    //record any new transaction
    public void saveTransaction(Long userId, String type,Double amount,String description)
    {
         if(userId == null)
        {
            throw new IllegalArgumentException("User Id cannot be null");
        }
        if (type == null || type.trim().isEmpty())
        {
            throw new IllegalArgumentException("Transaction type cannot be empty");
        }
        if (amount == null)
        {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (description == null || description.trim().isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if(!type.equals("TOPUP")&&!type.equals("PURCHASE"))
        {
            throw new IllegalArgumentException("Transaction type must be either TOPUP or PURCHASE");
        }

        if(amount == 0)
        {
            throw new IllegalArgumentException("Amount cannot be zero");
        }

        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
        {
            throw new IllegalArgumentException("User not found");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(Long userId)
    {
        if(userId == null)
        {
            throw new IllegalArgumentException("UserId cannot be null");
        }

        return transactionRepository.findByUser_Id(userId);
    }


}
