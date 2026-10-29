package com.skillbridge.controller;

import com.skillbridge.dto.UserProfileResponse;
import com.skillbridge.dto.UserResponse;
import com.skillbridge.model.User;
import com.skillbridge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * View basic user info
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        try {
            UserResponse user = userService.getUserProfile(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * View full profile including name, bio, offered skills, and learning interests
     * Fulfills abstract: "Each profile displays relevant information, including the
     * student's name, biography, offered skills, and learning interests."
     */
    @GetMapping("/{id}/full")
    public ResponseEntity<?> getFullProfile(@PathVariable Long id) {
        try {
            UserProfileResponse profile = userService.getFullProfile(id);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update profile information with access control verification
     * Fulfills abstract: "supports creating, viewing, updating, and deleting profile information with appropriate access restrictions."
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestParam(required = false) Long requestingUserId,
            @RequestBody User userUpdate) {
        try {
            // If requestingUserId is not in query, check if provided in update payload
            Long authUserId = (requestingUserId != null) ? requestingUserId : userUpdate.getId();
            UserResponse updated = userService.updateProfile(id, authUserId, userUpdate);
            return ResponseEntity.ok(updated);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete specific profile field (e.g. bio, phone, avatar) with access control
     */
    @DeleteMapping("/{id}/field/{fieldName}")
    public ResponseEntity<?> clearProfileField(
            @PathVariable Long id,
            @PathVariable String fieldName,
            @RequestParam Long requestingUserId) {
        try {
            UserResponse updated = userService.clearProfileField(id, requestingUserId, fieldName);
            return ResponseEntity.ok(updated);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete complete profile / account with access control verification
     * Fulfills abstract: "supports creating, viewing, updating, and deleting profile information with appropriate access restrictions."
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(
            @PathVariable Long id,
            @RequestParam Long requestingUserId) {
        try {
            userService.deleteProfile(id, requestingUserId);
            return ResponseEntity.ok(Map.of("message", "Profile and account deleted successfully"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
