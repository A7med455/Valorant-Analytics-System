package com.example.elearning.controller;

import com.example.elearning.model.Course;
import com.example.elearning.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController
{
    private final CourseService courseService; // handles course business logic

    // dependency injection
    public HomeController(CourseService courseService)
    {
        this.courseService = courseService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model)
    {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "home";
    }
}