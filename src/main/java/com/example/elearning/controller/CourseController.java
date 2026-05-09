package com.example.elearning.controller;

import com.example.elearning.model.Lesson;
import com.example.elearning.service.EnrollmentService;
import com.example.elearning.service.LessonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;

    public CourseController(CourseService courseService, SessionUser sessionUser, EnrollmentService enrollmentService,
                            LessonService lessonService){
        this.courseService=courseService;
        this.enrollmentService=enrollmentService;
        this.sessionUser=sessionUser;
        this.lessonService=lessonService;
    }

    @GetMapping("/courses")
    public String ShowAllCourses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("sessionUser",sessionUser);
        return "courses";
    }
    @GetMapping("/courses/{id}")
    public String showCourseDetail(@PathVariable Long id, Model model){
        Course course = courseService.getCourseById(id);
        if(course==null){
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        model.addAttribute("sessionUser", sessionUser);

        // Only show lessons if user is logged in AND enrolled
        if (sessionUser.isLoggedIn() && enrollmentService.isEnrolled(sessionUser.getUserId(), id))
        {
            model.addAttribute("lessons", lessonService.getLessonsByCourse(id));
        }

        return "course-detail";
    }
    @GetMapping("/my-courses")
    public String showMyCourses(Model model){
        if(!sessionUser.isLoggedIn()){
            return "redirect:/login";
        }
    try {
        List<Course> myCourses = enrollmentService.getEnrolledCourses(sessionUser.getUserId());
        model.addAttribute("myCourses", myCourses);
    } catch(IllegalArgumentException e){
        model.addAttribute("error",e.getMessage());
    }
        model.addAttribute("sessionUser",sessionUser);
        return "my-courses";
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
    @GetMapping("/lesson/{lessonId}/video")
    public ResponseEntity<byte[]> streamVideo(@PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson == null || lesson.getVideoData() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        String contentType = lesson.getVideoType() != null ? lesson.getVideoType() : "video/mp4";
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("inline", lesson.getVideoName());

        return ResponseEntity.ok().headers(headers).body(lesson.getVideoData());
    }


}
