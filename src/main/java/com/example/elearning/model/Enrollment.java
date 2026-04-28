package com.example.elearning.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;
    private long courseId;
    private LocalDateTime enrolledAt;

    @ManyToOne
    @JoinColumn(name = "User_Id")
private User user;

    public Enrollment(){}

    public Enrollment(long Id,long courseId,LocalDateTime enrolledAt){
        this.Id=Id;
        this.courseId=courseId;
        this.enrolledAt=enrolledAt;
    }

    public long getId(){
        return Id;
    }
    public void setId(Long userId){
        this.Id=Id;
    }

    public long getCourseId(){
        return courseId;
    }
    public void setCourseId(long courseId){
        this.courseId=courseId;
    }

    public LocalDateTime getEnrolledAt(){
        return enrolledAt;
    }
    public void setEnrolledAt(LocalDateTime enrolledAt){
        this.enrolledAt=enrolledAt;
    }
}
