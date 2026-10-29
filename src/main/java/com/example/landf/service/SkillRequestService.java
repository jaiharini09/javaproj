package com.example.landf.service;

import com.example.landf.dto.SkillProposalRequest;
import com.example.landf.model.ChatMessage;
import com.example.landf.model.SkillListing;
import com.example.landf.model.SkillRequest;
import com.example.landf.model.User;
import com.example.landf.repository.ChatMessageRepository;
import com.example.landf.repository.SkillListingRepository;
import com.example.landf.repository.SkillRequestRepository;
import com.example.landf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillRequestService {

    @Autowired
    private SkillRequestRepository skillRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillListingRepository skillListingRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public SkillRequest createRequest(SkillProposalRequest req) {
        if (req.getRequesterId().equals(req.getRecipientId())) {
            throw new IllegalArgumentException("You cannot send an exchange request to yourself.");
        }

        User requester = userRepository.findById(req.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("Requester not found with ID: " + req.getRequesterId()));

        User recipient = userRepository.findById(req.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found with ID: " + req.getRecipientId()));

        SkillListing requestedSkill = skillListingRepository.findById(req.getRequestedSkillId())
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with ID: " + req.getRequestedSkillId()));

        SkillRequest exchangeRequest = new SkillRequest(
                requester,
                recipient,
                requestedSkill,
                req.getOfferedSkillTitle() != null ? req.getOfferedSkillTitle() : "Mutual Skill Swap",
                req.getMessage()
        );

        SkillRequest saved = skillRequestRepository.save(exchangeRequest);

        // Auto-create initial message in conversation if message was provided
        if (req.getMessage() != null && !req.getMessage().isBlank()) {
            String initialMessageContent = "Proposal for '" + requestedSkill.getTitle() + "': " + req.getMessage();
            ChatMessage chatMsg = new ChatMessage(requester, recipient, initialMessageContent, saved.getId());
            chatMessageRepository.save(chatMsg);
        }

        return saved;
    }

    public List<SkillRequest> getIncomingRequests(Long userId) {
        return skillRequestRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }

    public List<SkillRequest> getOutgoingRequests(Long userId) {
        return skillRequestRepository.findByRequesterIdOrderByCreatedAtDesc(userId);
    }

    public SkillRequest updateStatus(Long requestId, String status, Long userId) {
        SkillRequest req = skillRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        // Validate permissions: recipient can accept or reject; requester can mark completed or cancel
        String normalizedStatus = status.toUpperCase();
        if (!List.of("ACCEPTED", "REJECTED", "COMPLETED", "CANCELLED").contains(normalizedStatus)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        req.setStatus(normalizedStatus);
        SkillRequest updated = skillRequestRepository.save(req);

        // Send system notification chat message
        String notifyText = "Exchange Request status updated to: " + normalizedStatus;
        ChatMessage statusMsg = new ChatMessage(
                req.getRecipient().getId().equals(userId) ? req.getRecipient() : req.getRequester(),
                req.getRecipient().getId().equals(userId) ? req.getRequester() : req.getRecipient(),
                notifyText,
                req.getId()
        );
        chatMessageRepository.save(statusMsg);

        return updated;
    }
}
