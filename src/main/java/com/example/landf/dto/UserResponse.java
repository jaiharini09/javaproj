package com.example.landf.dto;

import com.example.landf.model.User;
import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String college;
    private String department;
    private String yearOfStudy;
    private String bio;
    private String avatarUrl;
    private String contactPhone;
    private LocalDateTime createdAt;

    public UserResponse() {
    }

    public UserResponse(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.college = user.getCollege();
            this.department = user.getDepartment();
            this.yearOfStudy = user.getYearOfStudy();
            this.bio = user.getBio();
            this.avatarUrl = user.getAvatarUrl();
            this.contactPhone = user.getContactPhone();
            this.createdAt = user.getCreatedAt();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
