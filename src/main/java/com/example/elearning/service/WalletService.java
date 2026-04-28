package com.example.elearning.service;
import com.example.elearning.model.Wallet;
import com.example.elearning.repository.WalletRepository;
import org.springframework.stereotype.Service;
@Service
public class WalletService {
    private  final WalletRepository walletRepository;
    public WalletService(WalletRepository walletRepository){
        this.walletRepository=walletRepository;
    }
    /* returning the current balance of user
    step1: starting bby finding wallet by userId
    step2:check if the wallet exists or not
    step3:if wallet doesnt exist return 0
    step4: otherwise return balance
     */
    public Double getBalance(long userId){
        if (userId==null){
          throw new IllegalArgumentException("user id cannot be null");
        }
        Wallet wallet=walletRepository.findByUserId(userId);
        if (wallet==null){
            return 0.0;
        }
        return wallet.getBalance();
    }
    /*next step to add money to the user wallet(the topup function)
    step1:input validations
    step2:find wallet by findByUserId();
    step3:if wallet not exist start by creating new wallet
    step4:add the amount
    step5:save it
     */
    public void topUp(long userId,Double amount){
        if (userId==null || amount==null){
            throw  new IllegalArgumentException("invalid user id and amount");
        }
        if (amount<=0){
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        /*find wallet first*/
        Wallet wallet=walletRepository.findByUserId(userId);
        if (wallet==null){
            wallet=new Wallet();   /*create new wallet */
            wallet.setUserId(userId);
            wallet.setBalance(0.0);
        }
        wallet.setBalance(wallet.getBalance()+amount);    /*add the amount to the existing balance*/
        walletRepository.save(wallet);     /*save it in the wallet*/
    }
}
