package com.example.elearning.controller;

import com.example.elearning.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/*
 1- Get /login  show login page
 2-post/login process login form
 3-get / signup show signup page
 4- post/ signup process login form
 5- get/ logout log the user out

missing things:  it does not exist yet
    1 import the UserService Class
    2 import Session user class
  ----------------
  redirect was used so url changes automatically and empty the form or anything was saved or written before
*/


@Controller
public class AuthController
{
    private final UserService userService;//  check on business logic
    private final SessionUser sessionUser; //  opens session for the logged in user

    //dependency injection
    public AuthController( UserService userService ,SessionUser sessionUser)
    {
        this.userService = userService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/") //forward user to login to check if he has account or not
    // for root URL (localhost:8080/ )
    public String root()
    {
        if(sessionUser.getUserId() == null) // means user is not logged in
        {
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @GetMapping("/login")
    //URL(localhost:8080/login)
    public String showLoginPage()
    {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute User user, Model model)
    {
        //1- check if user in DB or no
        // email is PK for user in DB
        User foundUser = userService.findByEmail(user.getEmail());
        // email or password might be wrong so user must try again
        if (foundUser == null ||
                !foundUser.getPassword().equals(user.getPassword())
        ) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
        // all is good so make a session for him
        sessionUser.setUserId(foundUser.getId());
        sessionUser.setName(foundUser.getFname() + " " + foundUser.getLname());
        sessionUser.setRole(foundUser.getRole());

        if (foundUser.getRole().equals("ADMIN")) {
            // still not decided yet where admin should be forwarded
            /* suggestion
                make a special page for him to modify and do all CRUD operations
             */
            return "redirect:/admin/dashboard";
        }
       return "redirect:/home";
    }

    @GetMapping("/signup") // for get request
    public String showSignupPage()
    {
        //URL ( localhost:8080/signup)
        return "signup";
    }

    @PostMapping("/signup") //to handle the form from html
    public String handleSignUp(@ModelAttribute User user, Model model)
    {
        if(userService.findByEmail(user.getEmail()) != null)
        {
            // means user already has account
            model.addAttribute("error","Email is already registered.");
            return "signup";
        }

        userService.save(user); // send user object to be saved in DB through service layer
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String handleLogout()
    {
        sessionUser.clear(); // destroy session
        return "redirect:/login";
    }
}
