package com.skillbridge.service;

import com.skillbridge.dto.ChatMessageRequest;
import com.skillbridge.dto.UserResponse;
import com.skillbridge.model.ChatMessage;
import com.skillbridge.model.User;
import com.skillbridge.repository.ChatMessageRepository;
import com.skillbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Send direct message between peer students
     * Fulfills abstract: "communicate through an integrated messaging feature."
     */
    public ChatMessage sendMessage(ChatMessageRequest req) {
        if (req.getSenderId().equals(req.getRecipientId())) {
            throw new IllegalArgumentException("You cannot send a message to yourself.");
        }

        User sender = userRepository.findById(req.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found with ID: " + req.getSenderId()));

        User recipient = userRepository.findById(req.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found with ID: " + req.getRecipientId()));

        ChatMessage msg = new ChatMessage(sender, recipient, req.getContent().trim(), req.getRequestId());
        return chatMessageRepository.save(msg);
    }

    /**
     * Retrieve full conversation history between two students and mark recipient messages as read
     */
    public List<ChatMessage> getConversation(Long user1Id, Long user2Id) {
        List<ChatMessage> conversation = chatMessageRepository.findConversation(user1Id, user2Id);

        for (ChatMessage msg : conversation) {
            if (msg.getRecipient().getId().equals(user1Id) && !msg.isRead()) {
                msg.setRead(true);
                chatMessageRepository.save(msg);
            }
        }
        return conversation;
    }

    public long getUnreadCount(Long userId) {
        return chatMessageRepository.countByRecipientIdAndIsReadFalse(userId);
    }

    /**
     * Retrieve active conversation threads for a student
     */
    public List<Map<String, Object>> getRecentConversations(Long userId) {
        List<Long> partnerIds = chatMessageRepository.findDistinctPartnerIds(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Long pid : partnerIds) {
            Optional<User> partnerOpt = userRepository.findById(pid);
            if (partnerOpt.isPresent()) {
                List<ChatMessage> messages = chatMessageRepository.findConversation(userId, pid);
                if (!messages.isEmpty()) {
                    ChatMessage lastMsg = messages.get(messages.size() - 1);
                    long unreadCount = messages.stream()
                            .filter(m -> m.getRecipient().getId().equals(userId) && !m.isRead())
                            .count();

                    Map<String, Object> threadInfo = new HashMap<>();
                    threadInfo.put("partner", new UserResponse(partnerOpt.get()));
                    threadInfo.put("lastMessage", lastMsg.getContent());
                    threadInfo.put("lastTimestamp", lastMsg.getTimestamp());
                    threadInfo.put("unreadCount", unreadCount);
                    result.add(threadInfo);
                }
            }
        }

        result.sort((a, b) -> {
            java.time.LocalDateTime t1 = (java.time.LocalDateTime) a.get("lastTimestamp");
            java.time.LocalDateTime t2 = (java.time.LocalDateTime) b.get("lastTimestamp");
            return t2.compareTo(t1);
        });

        return result;
    }
}
