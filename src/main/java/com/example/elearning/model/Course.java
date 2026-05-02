package com.example.elearning.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "course")
    List<Enrollment> enrollments=new ArrayList<>();
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public Double getPrice(){
        return price;
    }
    public void setPrice(Double price){
        this.price=price;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public User getCreatedBy(){
        return createdBy;
    }
    public void setCreatedBy(User createdBy){
        this.createdBy=createdBy;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
