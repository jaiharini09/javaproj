package com.skillbridge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SkillListingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Skill title is required")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    /**
     * 'TEACH' (offered skill) or 'LEARN' (learning interest)
     */
    @NotBlank(message = "Skill type (TEACH or LEARN) is required")
    private String skillType;

    private String proficiencyLevel;
    private String description;
    private String availability;

    public SkillListingRequest() {
    }

    public SkillListingRequest(Long userId, String title, String category, String skillType, String proficiencyLevel, String description, String availability) {
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.skillType = skillType;
        this.proficiencyLevel = proficiencyLevel;
        this.description = description;
        this.availability = availability;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
