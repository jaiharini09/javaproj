package com.skillbridge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatMessageRequest {

    @NotNull(message = "Sender ID is required")
    private Long senderId;

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    private Long requestId;

    @NotBlank(message = "Message content cannot be blank")
    private String content;

    public ChatMessageRequest() {
    }

    public ChatMessageRequest(Long senderId, Long recipientId, String content, Long requestId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.requestId = requestId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
