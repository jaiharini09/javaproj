package com.skillbridge.dto;

import com.skillbridge.model.SkillListing;
import com.skillbridge.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates full profile information as specified in the abstract:
 * "Each profile displays relevant information, including the student's name,
 * biography, offered skills, and learning interests."
 */
public class UserProfileResponse {

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

    private List<SkillListing> offeredSkills = new ArrayList<>();
    private List<SkillListing> learningInterests = new ArrayList<>();
    private long completedExchangesCount;

    public UserProfileResponse() {
    }

    public UserProfileResponse(User user, List<SkillListing> offeredSkills, List<SkillListing> learningInterests, long completedExchangesCount) {
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
        this.offeredSkills = (offeredSkills != null) ? offeredSkills : new ArrayList<>();
        this.learningInterests = (learningInterests != null) ? learningInterests : new ArrayList<>();
        this.completedExchangesCount = completedExchangesCount;
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

    public List<SkillListing> getOfferedSkills() {
        return offeredSkills;
    }

    public void setOfferedSkills(List<SkillListing> offeredSkills) {
        this.offeredSkills = offeredSkills;
    }

    public List<SkillListing> getLearningInterests() {
        return learningInterests;
    }

    public void setLearningInterests(List<SkillListing> learningInterests) {
        this.learningInterests = learningInterests;
    }

    public long getCompletedExchangesCount() {
        return completedExchangesCount;
    }

    public void setCompletedExchangesCount(long completedExchangesCount) {
        this.completedExchangesCount = completedExchangesCount;
    }
}
