package com.example.elearning.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long Id;

@Column(unique = true)
private String email;
private String password;

private String fname;
private String lname;
private String role;
private Boolean status;
private int age;

    //a user can enroll in many courses
@OneToMany(mappedBy = "user")
    private List<Enrollment> enrollment=new ArrayList<>();

//a user can have many courses
@OneToMany(mappedBy = "createdBy")
private List <Course> createdcourses=new ArrayList<>();

//each user have one wallet
@OneToOne(mappedBy = "user")
private Wallet wallet;

public User(){
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

    public String getRole(){return role;}
    public void setRole(String role){
        this.role=role;
    }

    public void setAge(int age) {this.age = age;}
    public int getAge() {return age;}

    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status=status;
    }

    public Wallet getWallet(){return wallet;}
    public void setWallet(Wallet wallet){this.wallet=wallet;}
}
