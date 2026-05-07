package com.example.elearning.service;

import com.example.elearning.model.User;
import com.example.elearning.model.Wallet;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.repository.WalletRepository;
import com.example.elearning.model.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  final WalletRepository walletRepository;

    public UserService(UserRepository userRepository , WalletRepository walletRepository, WalletRepository walletRepository1) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository1;
    }

    //======================================//
    //findById
    //getAllUsers
    //userService.findByEmail(user.getEmail()
    //======================================//

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by ID (returns null if not found)
    public User findById(Long id) {
        if (id == null) {
            return null;
        }
        // JPA's findById returns Optional, so we convert to null
        return userRepository.findById(id).orElse(null);
    }

    // Find user by email (returns null if not found)
    public User findByEmail(String email) {
        if (email == null || email.trim().isEmpty())
        {
            return null;
        }
        return userRepository.findByEmail(email).orElse(null);
    }
    // Save or update user
    @Transactional
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Set default values if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("STUDENT"); // Default role
        }

        if (user.getStatus() == null) {
            user.setStatus(true); // Default status: active
        }

        // Save user first
        User savedUser = userRepository.save(user);

        // Create wallet for new user if it doesn't exist
        if (savedUser.getWallet() == null) {
            Wallet wallet = new Wallet();
            wallet.setUser(savedUser);
            wallet.setBalance(0.0);
            walletRepository.save(wallet);
            savedUser.setWallet(wallet);
        }

        return savedUser;
    }

    // Update existing user
    @Transactional
    public User update(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and User ID cannot be null");
        }

        // Check if user exists
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }

        return userRepository.save(user);
    }

    // Delete user by ID
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepository.deleteById(id);
    }

    // Check if email is already taken
    public boolean isEmailTaken(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email);
    }

    // Get all users by role
    public List<User> getUsersByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return List.of();
        }
        return userRepository.findByRole(role.toUpperCase());
    }

    // Get all students (role = STUDENT)
    public List<User> getAllStudents() {
        return userRepository.findByRole("STUDENT");
    }

    // Get all admins
    public List<User> getAllAdmins() {
        return userRepository.findByRole("ADMIN");
    }

    // Get all active users
    public List<User> getAllActiveUsers() {
        return userRepository.findByStatusTrue();
    }

    // Search users by name
    public List<User> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return userRepository.searchByName(keyword);
    }

    // Authenticate user (basic version - upgrade to BCrypt later)
    public User authenticate(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.orElse(null);
    }

    // Toggle user status (activate/deactivate)
    @Transactional
    public void toggleStatus(Long userId) {
        User user = findById(userId);
        if (user != null) {
            user.setStatus(!user.getStatus());
            userRepository.save(user);
        }
    }

    // Get total user count
    public long getUserCount() {
        return userRepository.count();
    }

    // Get student count only
    public long getStudentCount() {
        return userRepository.findByRole("STUDENT").size();
    }

    // Get admin count only
    public long getAdminCount() {
        return userRepository.findByRole("ADMIN").size();
    }
}

