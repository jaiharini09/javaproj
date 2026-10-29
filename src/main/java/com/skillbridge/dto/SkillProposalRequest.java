package com.skillbridge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SkillProposalRequest {

    @NotNull(message = "Requester ID is required")
    private Long requesterId;

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    @NotNull(message = "Requested skill ID is required")
    private Long requestedSkillId;

    @NotBlank(message = "Please describe what skill you can offer in return")
    private String offeredSkillTitle;

    @NotBlank(message = "Proposal message is required")
    private String message;

    public SkillProposalRequest() {
    }

    public SkillProposalRequest(Long requesterId, Long recipientId, Long requestedSkillId, String offeredSkillTitle, String message) {
        this.requesterId = requesterId;
        this.recipientId = recipientId;
        this.requestedSkillId = requestedSkillId;
        this.offeredSkillTitle = offeredSkillTitle;
        this.message = message;
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
