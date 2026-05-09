package com.example.elearning.controller;

import com.example.elearning.model.Course;
import com.example.elearning.service.CourseService;
import com.example.elearning.service.UserService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final UserService userService;
    private final SessionUser sessionUser;

    public AdminController(CourseService courseService, UserService userService, SessionUser sessionUser) {
        this.courseService = courseService;
        this.userService = userService;
        this.sessionUser = sessionUser;
    }

    // Dashboard: show all courses and users
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // تأكد أن المستخدم أدمن
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        // Fetch all courses and users from services and add to model
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/dashboard.html";
    }

    // Show add course form
    @GetMapping("/add-course")
    public String showAddCourseForm(Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }

        model.addAttribute("course", new Course());
        return "admin/add-course";
    }

    // Handle add course
// Handle add course
    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute Course course, Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        try {
            course.setCreatedBy(userService.findById(sessionUser.getUserId()));
            courseService.updateCourse(course);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("course", course);
            return "admin/add-course";
        }
        return "redirect:/admin/dashboard.html";
    }

    // Show edit form
    @GetMapping("/edit-course/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return "redirect:/admin/dashboard?error=notfound";
        }
        model.addAttribute("course", course);
        return "admin/edit-course";
    }

    // Handle edit
    @PostMapping("/edit-course/{id}")
    public String editCourse(@PathVariable Long id, @ModelAttribute Course course, Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        try {
            course.setId(id);
            course.setCreatedBy(userService.findById(sessionUser.getUserId()));
            courseService.updateCourse(course);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("course", course);
            return "admin/edit-course";
        }
        return "redirect:/admin/dashboard.html";
    }

    // Delete course
    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (!sessionUser.isAdmin()) return "redirect:/home";
        try {
            courseService.deleteCourse(id);
        } catch (Exception e) {
            return "redirect:/admin/dashboard.html";
        }
        return "redirect:/admin/dashboard.html";
    }
}