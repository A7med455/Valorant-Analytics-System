package com.example.elearning.model;
import jakarta.persistence.*;
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private long userId;
    private  double balance;
    public long getId(){
            return id;}
    public void setId(long id){
        this.id=id;
    }
    public long getUserId(){
        return userId;}
    public void setUserId(long userId){
        this.userId=userId;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        this.balance=balance;
    }
}




