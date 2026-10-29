package com.skillbridge.controller;

import com.skillbridge.dto.SkillListingRequest;
import com.skillbridge.model.SkillListing;
import com.skillbridge.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    /**
     * Search and filter skills
     * Fulfills abstract: "skill-based search, and filtering facilities to help users find suitable learning partners."
     */
    @GetMapping
    public List<SkillListing> getSkills(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String skillType,
            @RequestParam(required = false) String proficiencyLevel) {
        return skillService.searchSkills(query, category, skillType, proficiencyLevel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(skillService.getSkillById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public List<SkillListing> getSkillsByUser(@PathVariable Long userId) {
        return skillService.getSkillsByUser(userId);
    }

    /**
     * Create new skill listing (TEACH or LEARN)
     */
    @PostMapping
    public ResponseEntity<?> createSkill(@Valid @RequestBody SkillListingRequest req) {
        try {
            SkillListing created = skillService.createSkill(req);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update skill listing with access control check
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody SkillListingRequest req) {
        try {
            SkillListing updated = skillService.updateSkill(id, userId, req);
            return ResponseEntity.ok(updated);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete skill listing with access control check
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id, @RequestParam Long userId) {
        try {
            skillService.deleteSkill(id, userId);
            return ResponseEntity.ok(Map.of("message", "Skill listing deleted successfully"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
