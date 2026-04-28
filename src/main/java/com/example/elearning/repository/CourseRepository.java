package com.example.elearning.repository;

import com.example.elearning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
List<Course> findByCategory(String category);

//this line is for searching courses if we have a search bar?//
List<Course> findByTitleContaining(String keyword);
}
