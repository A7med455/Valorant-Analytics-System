package com.example.elearning.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction")
public class Transaction
{
    @Id  //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; //Many Transactions one user

    private String type; //TopUP or Purchase
    private Double amount;
    private String description; //Ex Topped up 100EGP
    private LocalDateTime timestamp;

    public Transaction()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }




}
