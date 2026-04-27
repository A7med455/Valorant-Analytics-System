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
    public Long getId(){
            return id;}
    public void setId(long id){
        this.id=id;
    }
    public Long getUserId(){
        return userId;}
    public void setUserId(long userId){
        this.userId=userId;
    }
    public Double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        this.balance=balance;
    }


}




