package com.example.elearning.service;

import com.example.elearning.model.Enrollment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    Returns list of courses the student is enrolled in
     Student opens "My Courses" page
     we need to show him his courses
     Step 1: ask EnrollmentRepository (give me all enrollments for this student)
     Step 2: from each enrollment, get the courseId
     Step 3: ask CourseRepository (give me the course with this id)
     Step 4: add the course if it still exists in DB (Optional handles this)
     Step 5: collect all courses in a list and return it
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
            // findById returns Optional<course> to avoid crash if course did not exist
            Optional<Course> result = courseRepository.findById(courseId);
            // isPresent() checks if the course still exists in DB before adding it
            if(result.isPresent())
            {
                //get() gives us the Course
                courses.add(result.get());
            }
        }
        return courses;
    }

    /*
    Returns true if student already purchased this course
    Student clicks "Buy" on a course
    before buying we check if he already owns it or not
    ask EnrollmentRepository does a record exist with this userId AND courseId?
    existsByUserIdAndCourseId returns true/false directly, no null check needed
  */
    public boolean isEnrolled(Long userId, Long courseId) {
        //if UserId and CourseId match with record in DB return true else false
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    /*
        Enroll a student in a course after a successful purchase
        could be used after payment is confirmed in purchaseService
     */

    public void enroll(Long userId, Long courseId)
    {
        //create a new enrollment record using this class
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        //we use setter from enrollment and record the moment this course was enrolled in
        enrollment.setEnrolledAt(LocalDateTime.now());
        //save data/record to DB
        enrollmentRepository.save(enrollment);
    }

}
