package com.example.elearning.service;
import com.example.elearning.model.User;
import com.example.elearning.model.Wallet;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.repository.WalletRepository;
import org.springframework.stereotype.Service;
@Service
public class WalletService {
    private  final WalletRepository walletRepository;
    private final UserRepository userRepository;
    public WalletService(WalletRepository walletRepository,UserRepository userRepository){
        this.walletRepository=walletRepository;
        this.userRepository=userRepository;
    }
    /* returning the current balance of user
    step1: starting bby finding wallet by userId
    step2:check if the wallet exists or not
    step3:if wallet does not exist return 0
    step4: otherwise return balance
     */
    public Double getBalance(Long userId){
        if (userId==null){
          throw new IllegalArgumentException("user id cannot be null");
        }
        Wallet wallet=walletRepository.findByUser_Id(userId);
        if (wallet==null){
            return 0.0;
        }
        return wallet.getBalance();
    }
    /*next step to add money to the user wallet(the topup function)
    step1:input validations
    step2:find wallet by findByUserId();
    step3:if wallet does not exist start by creating new wallet
    step4:add the amount
    step5:save it
     */
    public void topUp(Long userId,Double amount){
        if (userId==null || amount==null){
            throw  new IllegalArgumentException("invalid user id and amount");
        }
        if (amount<=0){
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        User user=userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("user not found"+userId));
        /*find wallet first edit it to check inside a function*/
        Wallet wallet=walletRepository.findByUser_Id(userId);
        if (wallet==null){
            wallet=new Wallet();   /*create new wallet */
            wallet.setUser(user);
            wallet.setBalance(0.0);
        }
        wallet.setBalance(wallet.getBalance()+amount);    /*add the amount to the existing balance*/
        walletRepository.save(wallet);     /*save it in the wallet*/
    }
}
