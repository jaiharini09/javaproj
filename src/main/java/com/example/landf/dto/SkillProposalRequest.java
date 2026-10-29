package com.example.landf.dto;

import jakarta.validation.constraints.NotNull;

public class SkillProposalRequest {
    @NotNull
    private Long requesterId;

    @NotNull
    private Long recipientId;

    @NotNull
    private Long requestedSkillId;

    private String offeredSkillTitle;
    private String message;

    public SkillProposalRequest() {
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getRequestedSkillId() {
        return requestedSkillId;
    }

    public void setRequestedSkillId(Long requestedSkillId) {
        this.requestedSkillId = requestedSkillId;
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
}
