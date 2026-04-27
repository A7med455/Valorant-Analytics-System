package com.example.elearning.model;
import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long Id;
private String email;
private String password;
private String fname;
private String lname;
private String role;
private Boolean status;

//a user can enroll in many courses
    @OneToMany(mappedBy = "User")
    private List<Enrollment> enrollment=new ArrayList<>();
//a user can have many courses
@OneToMany(mappedBy = "User")
private List <Course> courses=new ArrayList<>();

//each user have one wallet
    @OneToOne(mappedBy = "User")

public User(){
}

public User(Long Id,String email,String password, String fname, String lname,String role,Boolean status){
    this.Id=Id;
    this.email=email;
    this.password=password;
    this.fname=fname;
    this.lname=lname;
    this.role=role;
    this.status=status;
}

public String getEmail(){
    return email;
}
public void setEmail(String email){
     this.email=email;
}
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public Long getId(){
        return Id;
    }
    public void setId(Long Id){
        this.Id=Id;
    }
    public String getFname(){
        return fname;
    }
    public void setFname(String fname){
        this.fname=fname;
    }
    public String getLname(){
        return lname;
    }
    public void setLname(String lname){
        this.lname=lname;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status=status;
    }
}
