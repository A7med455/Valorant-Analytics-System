package com.example.elearning.repository;

import com.example.elearning.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>
{
    // function for an SQL query to get all enrolled students
    //SELECT * FROM enrollments WHERE user_id = ?
    List<Enrollment> findByUserId(Long userId);

    //SELECT COUNT(*) > 0 FROM enrollments WHERE user_id = ? AND course_id = ?
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

}
