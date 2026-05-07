package com.example.elearning.controller;

import com.example.elearning.model.User;
import com.example.elearning.service.CookieService;
import com.example.elearning.service.UserService;
import com.example.elearning.session.SessionUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 1- Get /login  show login page
 2-post/login process login form
 3-get / signup show signup page
 4- post/ signup process login form
 5- get/ logout log the user out


  redirect was used so url changes automatically and empty the form or anything was saved or written before
*/


@Controller
public class AuthController
{
    private final UserService userService;//  check on business logic
    private final SessionUser sessionUser; //  opens session for the logged in user
    private final CookieService cookieService;
    //Constructor injection
    public AuthController( UserService userService ,SessionUser sessionUser,CookieService cookieService)
    {
        this.userService = userService;
        this.sessionUser = sessionUser;
        this.cookieService = cookieService;
    }

    @GetMapping("/") //forward user to login to check if he has an account or not
    // for root URL (localhost:8080/ )
    public String root()
    {
        if(sessionUser.getUserId() == null) // means user is not logged in
        {
            return "redirect:/login";
        }
        if(sessionUser.getRole().equals("ADMIN"))
        {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/home";
    }

    @GetMapping("/login")
    //URL(localhost:8080/login)
    public String showLoginPage()
    {
        if(sessionUser.getUserId() != null)
        {
            if(sessionUser.getRole().equals("ADMIN"))
            {
                return "redirect:/admin/dashboard";
            }else
            {
                return "redirect:/home";
            }
        }
        return "login";
    }

    //if user doesn't check the box rememberMe should be false to avoid null
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute User user, Model model, @RequestParam(required = false) String rememberMe,HttpServletResponse response)
    {
        //check if input is not empty
        if(user.getEmail() == null || user.getEmail().isEmpty()
        || user.getPassword() == null || user.getPassword().isEmpty())
        {
            model.addAttribute("error", "Please enter valid email or password");
            return "login";
        }
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

        // Create Session
        sessionUser.setUserId(foundUser.getId());
        sessionUser.setName(foundUser.getFname() + " " + foundUser.getLname());
        sessionUser.setRole(foundUser.getRole());

        //RememberMe equals "on" means check box is checked
        if(rememberMe!=null && rememberMe.equals("on"))
        {
            cookieService.createRememberMeCookie(foundUser.getEmail(),response);
        }
        if (foundUser.getRole().equals("ADMIN"))
        {
            return "redirect:/admin/dashboard";
        }
       return "redirect:/home";
    }

    @GetMapping("/signup") // for get request
    public String showSignupPage()
    {
        if(sessionUser.getUserId() != null)
        {
            if(sessionUser.getRole().equals("ADMIN"))
            {
                return "redirect:/admin/dashboard";
            }else
            {
                return "redirect:/home";
            }
        }
        //URL ( localhost:8080/signup)
        return "signup";
    }

    @PostMapping("/signup") //to handle the form from html
    public String handleSignUp(@ModelAttribute User user, Model model)
    {
        if(user.getEmail() == null || user.getEmail().isEmpty()
        || user.getPassword() == null || user.getPassword().isEmpty()
        ||user.getFname() == null || user.getFname().isEmpty()
            ||user.getLname() == null || user.getLname().isEmpty())
        {
            model.addAttribute("error", "All fields are required");
            return "signup";
        }
        if(userService.findByEmail(user.getEmail()) != null)
        {
            // means user already has account
            model.addAttribute("error","Email is already registered.");
            return "signup";
        }
        try
        {
            userService.save(user); // send user object to be saved in DB through service layer
        }catch (Exception e)
        {
            model.addAttribute("error","Something went wrong, please try again");
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpServletResponse response)
    {
        sessionUser.clear(); // destroy session
        cookieService.clearRememberMeCookie(response);
        return "redirect:/login";
    }
}
