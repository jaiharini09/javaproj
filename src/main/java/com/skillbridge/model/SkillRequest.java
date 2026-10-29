package com.skillbridge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "skill_requests")
public class SkillRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private User requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private User recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requested_skill_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SkillListing requestedSkill;

    private String offeredSkillTitle;

    @Column(length = 1000)
    private String message;

    /**
     * PENDING, ACCEPTED, REJECTED, COMPLETED, CANCELLED
     */
    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public SkillRequest() {
    }

    public SkillRequest(User requester, User recipient, SkillListing requestedSkill, String offeredSkillTitle, String message) {
        this.requester = requester;
        this.recipient = recipient;
        this.requestedSkill = requestedSkill;
        this.offeredSkillTitle = offeredSkillTitle;
        this.message = message;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public SkillListing getRequestedSkill() {
        return requestedSkill;
    }

    public void setRequestedSkill(SkillListing requestedSkill) {
        this.requestedSkill = requestedSkill;
    }

    public String getOfferedSkillTitle() {
        return offeredSkillTitle;
    }

    public void setOfferedSkillTitle(String offeredSkillTitle) {
        this.offeredSkillTitle = offeredSkillTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
