package com.example.elearning.service;

import com.example.elearning.model.Course;
import com.example.elearning.model.Enrollment;
import com.example.elearning.model.User;
import com.example.elearning.model.Wallet;
import com.example.elearning.repository.CourseRepository;
import com.example.elearning.repository.EnrollmentRepository;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.repository.WalletRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final WalletRepository walletRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public PurchaseService(EnrollmentRepository enrollmentRepository, WalletRepository walletRepository, CourseRepository courseRepository,UserRepository userRepository,TransactionService transactionService) {
        this.enrollmentRepository = enrollmentRepository;
        this.walletRepository = walletRepository;
        this.courseRepository = courseRepository;
        this.userRepository=userRepository;
        this.transactionService=transactionService;
    }

    public void purchaseCourse(Long userId, Long courseId){
        if (userId == null) {
            throw new IllegalArgumentException("user id cannot be null");
        }
//check enrollment
        if (enrollmentRepository.existsByUser_IdAndCourse_Id(userId, courseId)) {
            throw new IllegalArgumentException("user already enrolled in this course");
        }
    //get course price
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new IllegalArgumentException("course can't be found");
        }
        double price = course.getPrice();
        //get wallet balance

        Wallet wallet=walletRepository.findByUser_Id(userId);
        if (wallet == null) {
            throw new IllegalArgumentException("wallet not found for user");
        }
//check balance
        if (wallet.getBalance() < price) {
            throw new IllegalArgumentException("insufficient balance");
        }

//subtract price from wallet
            wallet.setBalance(wallet.getBalance() - price);
            walletRepository.save(wallet);

//create enrollment

        Enrollment enrollment=new Enrollment();
        User user = userRepository.findById(userId).orElse(null);
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollmentRepository.save(enrollment);

        transactionService.saveTransaction(userId, "PURCHASE", -price, "Purchased: " + course.getTitle());

    }
}

