package com.example.elearning.controller;
import com.example.elearning.model.User;
import com.example.elearning.model.Wallet;
import com.example.elearning.service.UserService;
import com.example.elearning.service.WalletService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class WalletController {
    private final WalletService walletService;
    private  final SessionUser sessionUser;

    public WalletController(WalletService walletService,SessionUser sessionUser){
        this.walletService=walletService;
        this.sessionUser=sessionUser;
    }
    //view balance of the wallet
    @GetMapping("/wallet")
    public String viewWallet(Model model){
        //first check user logged in or not
        if (sessionUser.getUserId()==null){
            return "redirect:/login";
        }
        // get user from DB
        Double balance=walletService.getBalance(sessionUser.getUserId());
        model.addAttribute("balance",balance);
        model.addAttribute("name",sessionUser.getName());
        return "wallet";
    }

    @PostMapping("/wallet/topup")
    public String handleTopUp(@RequestParam double amount,Model model) {
        if (sessionUser.getUserId() == null) {
            return "redirect:/login";
        }
        //check for validations
        if (amount <= 0) {
            Double balance =walletService.getBalance(sessionUser.getUserId());
            model.addAttribute("error", "Invalid Amount");
            model.addAttribute("balance",balance);
            return "wallet";
        }
        // update balance
        try {
            walletService.topUp(sessionUser.getUserId(),amount);
        }
        catch (Exception e){
            Double balance=walletService.getBalance(sessionUser.getUserId());
            model.addAttribute("error","invalid inputs");
            model.addAttribute("balance",balance);
            return "wallet";
        }
        return "redirect:/wallet";
    }
}
