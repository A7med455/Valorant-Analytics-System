package com.example.elearning.service;

import com.example.elearning.model.Course;
import com.example.elearning.model.Enrollment;
import com.example.elearning.model.User;
import com.example.elearning.repository.CourseRepository;
import com.example.elearning.repository.EnrollmentRepository;
import com.example.elearning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService
{
    /*
       missing:

     */

    // repositories needed to query DB

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    //Constructor injection
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository
    ,UserRepository userRepository)
    {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
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
        if(userId == null)
        {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        // get all enrollment records for this student
        List<Enrollment> enrollments = enrollmentRepository.findByUser_Id(userId);
        // this list will hold the actual course objects
        List<Course> courses = new ArrayList<>();

        for(Enrollment e:enrollments)
        {
            // get object of course from each enrollment record
            Course course = e.getCourse();
            if(course != null)
            {
                courses.add(course);
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
        if(userId == null || courseId == null)
        {
            throw new IllegalArgumentException("User ID and Course ID cannot be null");
        }
        //if UserId and CourseId matches the record in DB return true else false
        return enrollmentRepository.existsByUser_IdAndCourse_Id(userId, courseId);
    }

    /*
        Enroll a student in a course after a successful purchase
        could be used after payment is confirmed in purchaseService
     */

    public void enroll(Long userId, Long courseId)
    {
        if(userId == null || courseId == null)
        {
            throw new IllegalArgumentException("User ID and Course ID cannot be null");
        }
        //check on duplicates so user don't enroll twice in same course
        if(isEnrolled(userId, courseId))
        {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }
        //create a new enrollment record using this class
        //orElse check if value exists return it else return default assigned value(null)
        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
        {
            throw new IllegalArgumentException("User not found");
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null)
        {
            throw new IllegalArgumentException("Course not found");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        //we use setter from enrollment and record the moment this course was enrolled in
        enrollment.setEnrolledAt(LocalDateTime.now());
        //save data/record to DB
        enrollmentRepository.save(enrollment);
    }

}
