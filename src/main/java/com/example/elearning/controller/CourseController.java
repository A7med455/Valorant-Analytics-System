package com.example.elearning.controller;

import ch.qos.logback.core.model.Model;
import com.example.elearning.model.Course;
import com.example.elearning.service.CourseService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        Course course = courseService.getCourseById(id).orElseThrow(()-> new IllegalArgumentException("Course not found"+id));
        model.addAttribute("course", course);
        model.addAttribute("sessionUser", sessionUser);
        return "Course-detail";
    }
    @GetMapping("/roadmap")
    public String ShowRoadmap(Model model){
        List<Course> allCourses=CourseService.getAllCourses();

        Map<String,List<Course>> coursesByCategory = allCourses.Stream().Collect(Collectors.groupingBy(Course::getCategory));
        model.addAttribute("coursesByCategory",coursesByCategory);
        model.addAttribute("sessionUser", sessionUser);
        return "roadmap";
    }






}
