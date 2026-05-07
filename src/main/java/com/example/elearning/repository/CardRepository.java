package com.example.elearning.repository;

import com.example.elearning.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>
{
    Card findByUser_Id(Long userId);
}