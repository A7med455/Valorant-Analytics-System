package com.example.elearning.controller;
import com.example.elearning.model.Card;
import com.example.elearning.model.Transaction;
import com.example.elearning.model.User;
import com.example.elearning.model.Wallet;
import com.example.elearning.service.CardService;
import com.example.elearning.service.TransactionService;
import com.example.elearning.service.UserService;
import com.example.elearning.service.WalletService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WalletController {
    private final WalletService walletService;
    private final SessionUser sessionUser;
    private final CardService cardService;
    private final TransactionService transactionService;

    public WalletController(WalletService walletService, SessionUser sessionUser, CardService cardService, TransactionService transactionService) {
        this.walletService = walletService;
        this.sessionUser = sessionUser;
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    //view balance of the wallet
    @GetMapping("/wallet")
    public String viewWallet(Model model) {
        //first check user logged in or not
        if (sessionUser.getUserId() == null) {
            return "redirect:/login";
        }
        Long userId = sessionUser.getUserId();
        Card card = cardService.getCardByUser(userId);
        // get user from DB
        Double balance = walletService.getBalance(sessionUser.getUserId());
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        model.addAttribute("balance", balance);
        model.addAttribute("name", sessionUser.getName());
        model.addAttribute("card", card);
        model.addAttribute("transcations", transactions);
        return "wallet";
    }

    @PostMapping("/wallet/topup")
    public String handleTopUp(@RequestParam double amount, Model model) {
        if (sessionUser.getUserId() == null) {
            return "redirect:/login";
        }
        Long userId = sessionUser.getUserId();
        //check for validations
        if (amount <= 0) {
            model.addAttribute("error", "Invalid Amount");
            return loadWalletModel(model, userId);
        }
        // update balance
        try {
            walletService.topUp(userId, amount);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return loadWalletModel(model, userId);
        }
        return "redirect:/wallet";
    }

    @GetMapping("/wallet/add-card")
    public String showAddCardForm(Model model) {
        if (sessionUser.getUserId() == null) {
             return "redirect:/login";
        }
        model.addAttribute("sessionUser", sessionUser);
         return "add-card";
    }

    @PostMapping("/wallet/add-card")
    public String handleAddCard(@RequestParam String cardHolderName, @RequestParam String cardNumber, @RequestParam int expiryMonth,
                                @RequestParam int expiryYear,
                                @RequestParam String cvv,
                                @RequestParam Double initialBalance, Model model) {
        if (sessionUser.getUserId() == null) {
             return "redirect:/login";
        }
        Long userId = sessionUser.getUserId();
        try {
            cardService.validateCard(cardNumber, expiryMonth, expiryYear, cvv, cardHolderName);
            Card card = new Card();
            card.setCardHolderName(cardHolderName);
            card.setExpiryMonth(expiryMonth);
            card.setExpiryYear(expiryYear);
            card.setCvv(cvv);
            cardService.saveCard(userId, card, cardNumber, initialBalance);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("sessionUser", sessionUser);
            return "add-card";
        }
        return "redirect:/wallet";
    }

    private String loadWalletModel(Model model, Long userId) {
        model.addAttribute("balance", walletService.getBalance(userId));
        model.addAttribute("name", sessionUser.getName());
        model.addAttribute("card", cardService.getCardByUser(userId));
        model.addAttribute("transactions", transactionService.getTransactionsByUser(userId));
        return "wallet";
    }
}

