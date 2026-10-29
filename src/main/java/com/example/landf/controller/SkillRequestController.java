package com.example.landf.controller;

import com.example.landf.dto.SkillProposalRequest;
import com.example.landf.model.SkillRequest;
import com.example.landf.service.SkillRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class SkillRequestController {

    @Autowired
    private SkillRequestService skillRequestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@Valid @RequestBody SkillProposalRequest req) {
        try {
            SkillRequest created = skillRequestService.createRequest(req);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/incoming")
    public List<SkillRequest> getIncoming(@RequestParam Long userId) {
        return skillRequestService.getIncomingRequests(userId);
    }

    @GetMapping("/outgoing")
    public List<SkillRequest> getOutgoing(@RequestParam Long userId) {
        return skillRequestService.getOutgoingRequests(userId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long userId) {
        try {
            SkillRequest updated = skillRequestService.updateStatus(id, status, userId);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
