package com.example.landf.controller;

import com.example.landf.dto.ChatMessageRequest;
import com.example.landf.model.ChatMessage;
import com.example.landf.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody ChatMessageRequest req) {
        try {
            ChatMessage msg = chatService.sendMessage(req);
            return ResponseEntity.ok(msg);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/conversation")
    public List<ChatMessage> getConversation(@RequestParam Long user1, @RequestParam Long user2) {
        return chatService.getConversation(user1, user2);
    }

    @GetMapping("/unread-count")
    public Map<String, Long> getUnreadCount(@RequestParam Long userId) {
        return Map.of("unreadCount", chatService.getUnreadCount(userId));
    }

    @GetMapping("/threads")
    public List<Map<String, Object>> getRecentThreads(@RequestParam Long userId) {
        return chatService.getRecentConversations(userId);
    }
}
