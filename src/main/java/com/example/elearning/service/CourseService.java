package com.example.elearning.service;

import com.example.elearning.model.Course;
import com.example.elearning.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

public List<Course> getAllCourses(){
        return courseRepository.findAll();
}

public Course getCourseById(Long id){
    Optional<Course> course= courseRepository.findById(id);
    return course.orElse(null);
}
//admin use//
public Course addCourse(Course course){
        return courseRepository.save(course);
}

public Course upadateCourse(Course course){
        return courseRepository.save(course);
}
public void deleteCourse(Long id){
        courseRepository.deleteById(id);
}
public List<Course> getCoursesByCategory(String category){
        return courseRepository.findByCategory(category);
}

}


