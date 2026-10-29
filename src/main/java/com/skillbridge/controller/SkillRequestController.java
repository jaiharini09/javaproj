package com.skillbridge.controller;

import com.skillbridge.dto.SkillProposalRequest;
import com.skillbridge.model.SkillRequest;
import com.skillbridge.service.SkillRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /**
     * Send skill exchange proposal
     */
    @PostMapping
    public ResponseEntity<?> createRequest(@Valid @RequestBody SkillProposalRequest req) {
        try {
            SkillRequest created = skillRequestService.createRequest(req);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * View incoming exchange requests
     */
    @GetMapping("/incoming")
    public List<SkillRequest> getIncoming(@RequestParam Long userId) {
        return skillRequestService.getIncomingRequests(userId);
    }

    /**
     * View outgoing exchange requests
     */
    @GetMapping("/outgoing")
    public List<SkillRequest> getOutgoing(@RequestParam Long userId) {
        return skillRequestService.getOutgoingRequests(userId);
    }

    /**
     * Update exchange request status (ACCEPTED, REJECTED, COMPLETED, CANCELLED) with access checks
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long userId) {
        try {
            SkillRequest updated = skillRequestService.updateStatus(id, status, userId);
            return ResponseEntity.ok(updated);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
