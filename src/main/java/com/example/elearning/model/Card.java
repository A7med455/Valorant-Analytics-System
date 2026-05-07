package com.example.elearning.model;

import jakarta.persistence.*;

@Entity // tells JPA this is a DB table called card
@Table(name="card")
public class Card
{
    @Id //auto increment id (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long cardId;

    //Each user can have one card
    @OneToOne
    @JoinColumn(name="user_id",unique = true)
    private User user;

    private String cardHolderName;
    //store the last 4 digits only for security purposes
    private String lastFourDigits;
    private int expiryMonth;
    private int expiryYear;
    private String cvv;
    private Double balance;

    //Required by JPA to create objects
    public Card()
    {}

    public Long getCardId()
    {
        return cardId;
    }

    public void setCardId(Long cardId)
    {
        this.cardId = cardId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName)
    {
        this.cardHolderName = cardHolderName;
    }

    public String getLastFourDigits()
    {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits)
    {
        this.lastFourDigits = lastFourDigits;
    }

    public int getExpiryMonth()
    {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth)
    {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear()
    {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear)
    {
        this.expiryYear = expiryYear;
    }

    public String getCvv()
    {
        return cvv;
    }

    public void setCvv(String cvv)
    {
        this.cvv = cvv;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }


}
