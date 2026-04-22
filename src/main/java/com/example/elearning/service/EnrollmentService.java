package com.example.elearning.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EnrollmentService
{
    /*
       missing:
       import classes used here
     */

    // repositories needed to query DB
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    //Constructor injection
    public EnrollmentService(EnrollmentRepository enrollmentRepository
            ,CourseRepository courseRepository)
    {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    /*
    return list of courses the student is enrolled in
    * Student opens "My Courses" page
    → we need to show him his courses
    → Step 1: ask EnrollmentRepository "give me all enrollments for this student"
    → Step 2: from each enrollment we get the courseId
    → Step 3: ask CourseRepository "give me the course with this id"
    → Step 4: collect all courses in a list and return it
    */
    public List<Course> getEnrolledCourses(Long userId)
    {
        // get all enrollment records for this student
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        // this list will hold the actual course objects
        List<Course> courses = new ArrayList<>();

        for(Enrollment e:enrollments)
        {
            // get the courseId from each enrollment record
            Long courseId = e.getCourseId();
            // fetch(retrieve) the course object from DB using that id
            Course course = courseRepository.getById(courseId);
            // only add if course still exists in DB
            if(course != null)
            {
                courses.add(course);
            }
        }
        return courses;
    }

    /*
    returns true if student already purchased this course
    * Student clicks "Buy" on a course
    → before buying we check if he already owns it
    → ask EnrollmentRepository "does a record exist with this userId AND courseId?"
    → if enrollment != null → he already owns it → return true
    → if enrollment == null → he doesn't own it → return false
    */
    public boolean isEnrolled(Long userId, Long courseId)
    {
        // try to find an enrollment record matching both userId and courseId
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId,courseId);
        if(enrollment != null)
        {
            return true;
        }
        // no record found means student is not enrolled
        return false;
    }

}
