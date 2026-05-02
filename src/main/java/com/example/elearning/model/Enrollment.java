package com.example.elearning.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    @ManyToOne
    @JoinColumn(name="Course_Id")
    private Course course;

    private LocalDateTime enrolledAt;

    @ManyToOne
    @JoinColumn(name = "User_Id")
private User user;

    public Enrollment(){}

    public Long getEnrollmentId(){
        return enrollmentId;
    }
    public void setEnrollmentId(Long enrollmentId){
        this.enrollmentId=enrollmentId;
    }

    public Course getCourse(){return course;}
    public void setCourse(Course course){this.course=course;}

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

    public LocalDateTime getEnrolledAt(){
        return enrolledAt;
    }
    public void setEnrolledAt(LocalDateTime enrolledAt){
        this.enrolledAt=enrolledAt;
    }
}
