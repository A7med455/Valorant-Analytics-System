package com.example.elearning.controller;

import com.example.elearning.model.Course;
import com.example.elearning.model.User;
import com.example.elearning.service.CourseService;
import com.example.elearning.service.EnrollmentService;
import com.example.elearning.service.UserService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final CourseService courseService; // handles course business logic
    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private final SessionUser sessionUser;

    // dependency injection
    public HomeController(CourseService courseService, UserService userService,  EnrollmentService enrollmentService,
                          SessionUser sessionUser) {

        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
        this.sessionUser = sessionUser;

    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("sessionUser", sessionUser);
        return "home";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        if (!sessionUser.isLoggedIn()) {
            return "redirect:/login";
        }
        User user = userService.findById(sessionUser.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("sessionUser", sessionUser);
        return "profile";
    }
    @GetMapping("/wallet")
    public String showWallet() {
        if (!sessionUser.isLoggedIn()) {
            return "redirect:/login";
        }
        return "redirect:/wallet";
    }
    @GetMapping("/profile/my-courses")
    public String showMyCourses(Model model) {
        if (!sessionUser.isLoggedIn()) {
            return "redirect:/login";
        }
        List<Course> myCourses = enrollmentService.getEnrolledCourses(sessionUser.getUserId());
        model.addAttribute("myCourses", myCourses);
        model.addAttribute("sessionUser", sessionUser);
        return "my-courses";
    }
}