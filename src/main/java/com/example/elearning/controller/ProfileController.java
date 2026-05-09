package com.example.elearning.controller;

import com.example.elearning.service.EnrollmentService;
import com.example.elearning.session.SessionUser;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

    private final EnrollmentService enrollmentService;
    private final SessionUser sessionUser;

    public ProfileController(EnrollmentService enrollmentService, SessionUser sessionUser) {
        this.enrollmentService = enrollmentService;
        this.sessionUser = sessionUser;
    }
}