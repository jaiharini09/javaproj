package com.skillbridge.service;

import com.skillbridge.dto.SkillProposalRequest;
import com.skillbridge.model.ChatMessage;
import com.skillbridge.model.SkillListing;
import com.skillbridge.model.SkillRequest;
import com.skillbridge.model.User;
import com.skillbridge.repository.ChatMessageRepository;
import com.skillbridge.repository.SkillListingRepository;
import com.skillbridge.repository.SkillRequestRepository;
import com.skillbridge.repository.UserRepository;
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

    /**
     * Send a skill exchange proposal
     * Fulfills abstract: "Students can send skill exchange requests"
     */
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
                (req.getOfferedSkillTitle() != null && !req.getOfferedSkillTitle().isBlank()) ? req.getOfferedSkillTitle().trim() : "Mutual Skill Swap",
                req.getMessage().trim()
        );

        SkillRequest saved = skillRequestRepository.save(exchangeRequest);

        // Auto-create initial message in integrated chat
        String initialMsg = "Proposal for skill '" + requestedSkill.getTitle() + "': " + req.getMessage() + 
                            " (Offering in exchange: " + saved.getOfferedSkillTitle() + ")";
        ChatMessage chatMsg = new ChatMessage(requester, recipient, initialMsg, saved.getId());
        chatMessageRepository.save(chatMsg);

        return saved;
    }

    public List<SkillRequest> getIncomingRequests(Long userId) {
        return skillRequestRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }

    public List<SkillRequest> getOutgoingRequests(Long userId) {
        return skillRequestRepository.findByRequesterIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Accept, reject, complete, or cancel an exchange request with access control
     * Fulfills abstract: "accept or reject requests, and communicate through an integrated messaging feature"
     */
    public SkillRequest updateStatus(Long requestId, String status, Long requestingUserId) {
        SkillRequest req = skillRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        String normalizedStatus = status.trim().toUpperCase();
        if (!List.of("ACCEPTED", "REJECTED", "COMPLETED", "CANCELLED").contains(normalizedStatus)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        // Access control:
        // - ACCEPTED / REJECTED can only be performed by the recipient
        // - CANCELLED can only be performed by the requester
        // - COMPLETED can be marked by either party
        if (List.of("ACCEPTED", "REJECTED").contains(normalizedStatus)) {
            if (requestingUserId == null || !req.getRecipient().getId().equals(requestingUserId)) {
                throw new SecurityException("Access Denied: Only the recipient can accept or reject this proposal.");
            }
        } else if ("CANCELLED".equals(normalizedStatus)) {
            if (requestingUserId == null || !req.getRequester().getId().equals(requestingUserId)) {
                throw new SecurityException("Access Denied: Only the requester can cancel this proposal.");
            }
        } else if ("COMPLETED".equals(normalizedStatus)) {
            if (requestingUserId == null || (!req.getRequester().getId().equals(requestingUserId) && !req.getRecipient().getId().equals(requestingUserId))) {
                throw new SecurityException("Access Denied: You are not a participant in this exchange.");
            }
        }

        req.setStatus(normalizedStatus);
        SkillRequest updated = skillRequestRepository.save(req);

        // System message in conversation
        String notifyText = "Exchange Proposal status updated to: " + normalizedStatus;
        User sender = req.getRecipient().getId().equals(requestingUserId) ? req.getRecipient() : req.getRequester();
        User peer = req.getRecipient().getId().equals(requestingUserId) ? req.getRequester() : req.getRecipient();

        ChatMessage statusMsg = new ChatMessage(sender, peer, notifyText, req.getId());
        chatMessageRepository.save(statusMsg);

        return updated;
    }
}
