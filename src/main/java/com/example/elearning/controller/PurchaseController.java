package com.example.elearning.controller;

import com.example.elearning.service.PurchaseService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final SessionUser sessionUser;

    public PurchaseController(PurchaseService purchaseService, SessionUser sessionUser) {
        this.purchaseService = purchaseService;
        this.sessionUser = sessionUser;
    }

    @PostMapping("/{courseId}")
    public String purchaseCourse(@PathVariable Long courseId) {
        if (!sessionUser.isLoggedIn()) {
            return "redirect:/login";
        }
        try {
            Long userId = sessionUser.getUserId();
            purchaseService.purchaseCourse(userId, courseId);
            return "redirect:/profile/my-courses";
        } catch (IllegalArgumentException e) {
            return "redirect:/courses?error=" + e.getMessage();
        }
    }
}
