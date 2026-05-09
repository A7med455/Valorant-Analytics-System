package com.example.elearning.controller;

import com.example.elearning.model.Course;
import com.example.elearning.model.Enrollment;
import com.example.elearning.model.Lesson;
import com.example.elearning.repository.EnrollmentRepository;
import com.example.elearning.repository.LessonRepository;
import com.example.elearning.service.CourseService;
import com.example.elearning.service.LessonService;
import com.example.elearning.service.UserService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final UserService userService;
    private final SessionUser sessionUser;
    private final LessonService lessonService;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AdminController(CourseService courseService, UserService userService,
                           SessionUser sessionUser, LessonService lessonService,
                           LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository) {
        this.courseService = courseService;
        this.userService = userService;
        this.sessionUser = sessionUser;
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // Dashboard: show all courses and users
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("sessionUser", sessionUser);
        return "admin/dashboard";
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
        return "redirect:/admin/dashboard";
    }

    // Show edit form
    @GetMapping("/edit-course/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return "redirect:/admin/dashboard";
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
        return "redirect:/admin/dashboard";
    }

    // Delete course
    // Delete course
    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (!sessionUser.isAdmin()) return "redirect:/home";
        try {
            // Delete enrollments for this course
            List<Enrollment> enrollments = enrollmentRepository.findByCourse_Id(id);
            enrollmentRepository.deleteAll(enrollments);

            // Delete lessons for this course
            List<Lesson> lessons = lessonService.getLessonsByCourse(id);
            lessonRepository.deleteAll(lessons);

            // Delete the course
            courseService.deleteCourse(id);
        } catch (Exception e) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/dashboard";
    }

    // Show add lesson form
    @GetMapping("/add-lesson/{courseId}")
    public String showAddLessonForm(@PathVariable Long courseId, Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("course", course);
        model.addAttribute("sessionUser", sessionUser);
        return "admin/add-lesson";
    }

    // Handle add lesson
    @PostMapping("/add-lesson/{courseId}")
    public String addLesson(@PathVariable Long courseId,
                            @RequestParam String title,
                            @RequestParam(required = false) String videoUrl,
                            @RequestParam(required = false) MultipartFile videoFile,
                            Model model) {
        if (!sessionUser.isAdmin()) {
            return "redirect:/home";
        }
        try {
            if (videoUrl != null && !videoUrl.trim().isEmpty()) {
                lessonService.addLessonByUrl(courseId, title, videoUrl);
            } else if (videoFile != null && !videoFile.isEmpty()) {
                lessonService.addLessonByFile(courseId, title, videoFile);
            } else {
                model.addAttribute("error", "Please provide a video URL or upload a file");
                model.addAttribute("course", courseService.getCourseById(courseId));
                model.addAttribute("sessionUser", sessionUser);
                return "admin/add-lesson";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("course", courseService.getCourseById(courseId));
            model.addAttribute("sessionUser", sessionUser);
            return "admin/add-lesson";
        }
        return "redirect:/admin/dashboard";
    }
}