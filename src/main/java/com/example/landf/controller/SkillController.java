package com.example.landf.controller;

import com.example.landf.dto.SkillListingRequest;
import com.example.landf.model.SkillListing;
import com.example.landf.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<SkillListing> getSkills(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String skillType) {
        return skillService.searchSkills(query, category, skillType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(skillService.getSkillById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<SkillListing> getSkillsByUser(@PathVariable Long userId) {
        return skillService.getSkillsByUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> createSkill(@Valid @RequestBody SkillListingRequest req) {
        try {
            SkillListing created = skillService.createSkill(req);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id, @RequestParam Long userId) {
        try {
            skillService.deleteSkill(id, userId);
            return ResponseEntity.ok(Map.of("message", "Skill deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
