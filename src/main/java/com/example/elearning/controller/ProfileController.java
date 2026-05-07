package com.example.elearning.controller;

import com.example.elearning.model.Course;
import com.example.elearning.service.EnrollmentService;
import com.example.elearning.service.UserService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ProfileController
{
    private final EnrollmentService enrollmentService;
    private final SessionUser sessionUser;

    // dependency injection
    public ProfileController(EnrollmentService enrollmentService, SessionUser sessionUser)
    {
        this.enrollmentService = enrollmentService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/profile")
    public String showProfilePage()
    {
        return "profile";
    }

    @GetMapping("/profile/my-courses")
    public String showMyCourses(Model model)
    {
        // use session ID to get all courses this user is enrolled in
        List<Course> courses = enrollmentService.getEnrolledCourses(sessionUser.getUserId());
        model.addAttribute("courses", courses);
        return "my-courses";
    }
}