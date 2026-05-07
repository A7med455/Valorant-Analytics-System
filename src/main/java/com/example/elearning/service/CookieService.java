package com.example.elearning.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService
{
    public void createRememberMeCookie(String email, HttpServletResponse response)
    {
        Cookie cookie = new Cookie("email", email);
        cookie.setMaxAge(7*24*60*60);//set TTL to 7 Days
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    //clear the cookie on logout
    public void clearRememberMeCookie(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("email", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }



}
