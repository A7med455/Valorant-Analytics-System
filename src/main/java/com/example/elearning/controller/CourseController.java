package com.example.elearning.controller;

import org.springframework.ui.Model;
import com.example.elearning.model.Course;
import com.example.elearning.service.CourseService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final SessionUser sessionUser;

    public CourseController(CourseService courseService, SessionUser sessionUser){
        this.courseService=courseService;
        this.sessionUser=sessionUser;
    }

    @GetMapping("/courses")
    public String ShowAllCourses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("sessionUser",sessionUser);
        return "courses";
    }
    @GetMapping("/courses/{id}")
    public String ShowCourseDetail(@PathVariable Long id, Model model){
        Course course = courseService.getCourseById(id);
        if(course==null){
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        model.addAttribute("sessionUser", sessionUser);
        return "course-detail";
    }
    @GetMapping("/roadmap")
    public String showRoadmap(Model model) {
        List<Course> allCourses = courseService.getAllCourses();
        Map<String, List<Course>> coursesByCategory = new HashMap<>();
        for (Course course : allCourses) {
            String category = course.getCategory();
            if (!coursesByCategory.containsKey(category)) {
                coursesByCategory.put(category, new ArrayList<>());
            }
            coursesByCategory.get(category).add(course);
        }
        model.addAttribute("coursesByCategory", coursesByCategory);
        model.addAttribute("sessionUser", sessionUser);
        return "roadmap";
    }



}
