package com.example.elearning.service;

import com.example.elearning.model.Card;
import com.example.elearning.model.User;
import org.springframework.stereotype.Service;
import com.example.elearning.repository.CardRepository;
import com.example.elearning.repository.UserRepository;

import java.time.Year;

@Service
public class CardService
{
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository,UserRepository userRepository)
    {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public void validateCard(String cardNumber,int expiryMonth,
                        int expiryYear,String cvv,
                        String cardHolderName)
    {
        if(cardHolderName==null || cardHolderName.trim().isEmpty())
        {
            throw new IllegalArgumentException("Card Holder Name cannot be null or empty");
        }
        if(cardNumber==null || cardNumber.trim().isEmpty())
        {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }else
        {
            if(!cardNumber.matches("\\d{16}"))
            {
                throw new IllegalArgumentException(" Card Number must be 16 digits");
            }
        }
        if(cvv==null ||!cvv.matches("\\d{4}"))
        {
            throw new IllegalArgumentException("CVV number must be 4 digits");
        }

        if(expiryMonth<1 || expiryMonth>12)
        {
            throw new IllegalArgumentException("Expiry month must be between 1 and 12");
        }

        int currentYear = Year.now().getValue();
        if(expiryYear < currentYear)
        {
            throw new IllegalArgumentException("Expiry year must be greater than current year");
        }
        if(expiryYear > currentYear+20)
        {
            throw new IllegalArgumentException("Expiry year is too far in the future");
        }
        if(expiryYear == currentYear)
        {
            int currentMonth = java.time.LocalDate.now().getMonthValue();
            if(expiryMonth<currentMonth)
            {
                throw new IllegalArgumentException("Card is expired — expiry month is in the past");
            }
        }
    }

    public void saveCard(Long userId, Card card,String fullCardNumber)
    {
        User user=userRepository.findById(userId).orElse(null);
        if(user==null)
        {
            throw new IllegalArgumentException("User not found");
        }
        if(fullCardNumber==null || fullCardNumber.trim().isEmpty() ||
        !fullCardNumber.matches("\\d{16}"))
        {
            throw new IllegalArgumentException("Full Card Number must be 16 digits");
        }
        //save last 4 digits
        String lastFourDigit = fullCardNumber.substring(12);

        card.setUser(user);
        card.setLastFourDigits(lastFourDigit);
        cardRepository.save(card);
    }

    public Card getCardByUser(Long userId)
    {
        if(userId==null)
        {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return cardRepository.findByUser_Id(userId);
    }

}
