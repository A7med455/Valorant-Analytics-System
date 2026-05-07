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
    private final CardService cardService;
    private final TransactionService transactionService;
    public WalletService(WalletRepository walletRepository,UserRepository userRepository,CardService cardService,TransactionService transactionService){
        this.walletRepository=walletRepository;
        this.userRepository=userRepository;
        this.cardService=cardService;
        this.transactionService=transactionService;
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
    step2:deduct the amount from the user's card
    step3: find or create the wallet
    step4:add amount to wallet balance
    step5:save wallet
    step6: save transaction record
     */
    public void topUp(Long userId,Double amount){
        if (userId==null || amount==null){
            throw  new IllegalArgumentException("invalid user id and amount");
        }
        if (amount<=0){
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        //step1: verify user exists
        User user=userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("user not found"+userId));
        // step2: deduct from card, will throw if no card or imsufficient balance
        cardService.deductFromCard(userId,amount);

        //step3: find or create wallet
        Wallet wallet=walletRepository.findByUser_Id(userId);
        if (wallet==null){
            wallet=new Wallet();
            wallet.setUser(user);
            wallet.setBalance(0.0);
        }
        //step4&5: add amount and save
        wallet.setBalance(wallet.getBalance()+amount);    /*add the amount to the existing balance*/
        walletRepository.save(wallet);

        //step6: record the transaction
        transactionService.saveTransaction(userId,"TOPUP",amount,"Topped up" + amount + "EGP to wallet");
    }
}
