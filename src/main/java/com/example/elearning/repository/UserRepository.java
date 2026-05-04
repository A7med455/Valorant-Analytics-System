package com.example.elearning.repository;

import com.example.elearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    List<User> findByRole(String role);
    List<User> findByStatusTrue();

    @Query("SELECT u FROM User u WHERE LOWER(u.fname)" +
            "LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.lname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByName(@Param("keyword") String keyword);
}