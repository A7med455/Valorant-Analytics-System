package com.example.elearning.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;

@Component
@SessionScope
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    // Fields
    private Long userId;
    private String name;
    private String role;

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Helper Methods
    public void clear() {
        this.userId = null;
        this.name = null;
        this.role = null;
    }

    public boolean isLoggedIn() {
        return userId != null;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
}