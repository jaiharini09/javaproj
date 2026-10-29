package com.skillbridge.service;

import com.skillbridge.dto.SkillListingRequest;
import com.skillbridge.model.SkillListing;
import com.skillbridge.model.User;
import com.skillbridge.repository.SkillListingRepository;
import com.skillbridge.repository.SkillRequestRepository;
import com.skillbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillListingRepository skillListingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRequestRepository skillRequestRepository;

    /**
     * Search and filter skills by keyword, category, skillType (TEACH vs LEARN), and proficiency level
     * Fulfills abstract: "skill-based search, and filtering facilities to help users find suitable learning partners."
     */
    public List<SkillListing> searchSkills(String query, String category, String skillType, String proficiencyLevel) {
        String cleanQuery = (query != null && !query.trim().isEmpty()) ? query.trim() : null;
        String cleanCategory = (category != null && !category.equalsIgnoreCase("ALL") && !category.trim().isEmpty()) ? category.trim() : null;
        String cleanSkillType = (skillType != null && !skillType.equalsIgnoreCase("ALL") && !skillType.trim().isEmpty()) ? skillType.trim().toUpperCase() : null;
        String cleanProficiency = (proficiencyLevel != null && !proficiencyLevel.equalsIgnoreCase("ALL") && !proficiencyLevel.trim().isEmpty()) ? proficiencyLevel.trim().toUpperCase() : null;

        return skillListingRepository.searchSkills(cleanQuery, cleanCategory, cleanSkillType, cleanProficiency);
    }

    public SkillListing getSkillById(Long id) {
        return skillListingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill listing not found with ID: " + id));
    }

    public List<SkillListing> getSkillsByUser(Long userId) {
        return skillListingRepository.findByUserId(userId);
    }

    public List<SkillListing> getSkillsByUserAndType(Long userId, String skillType) {
        return skillListingRepository.findByUserIdAndSkillType(userId, skillType.toUpperCase());
    }

    /**
     * Create a new skill listing (TEACH or LEARN)
     */
    public SkillListing createSkill(SkillListingRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + req.getUserId()));

        SkillListing listing = new SkillListing(
                user,
                req.getTitle().trim(),
                req.getCategory().trim(),
                req.getSkillType().trim().toUpperCase(),
                (req.getProficiencyLevel() != null && !req.getProficiencyLevel().isBlank()) ? req.getProficiencyLevel().trim().toUpperCase() : "INTERMEDIATE",
                req.getDescription() != null ? req.getDescription().trim() : "",
                (req.getAvailability() != null && !req.getAvailability().isBlank()) ? req.getAvailability().trim() : "Flexible"
        );
        return skillListingRepository.save(listing);
    }

    /**
     * Update an existing skill listing with access control check
     */
    public SkillListing updateSkill(Long skillId, Long requestingUserId, SkillListingRequest req) {
        SkillListing listing = getSkillById(skillId);
        if (requestingUserId == null || !listing.getUser().getId().equals(requestingUserId)) {
            throw new SecurityException("Access Denied: You cannot modify another student's skill listing.");
        }

        if (req.getTitle() != null && !req.getTitle().isBlank()) {
            listing.setTitle(req.getTitle().trim());
        }
        if (req.getCategory() != null && !req.getCategory().isBlank()) {
            listing.setCategory(req.getCategory().trim());
        }
        if (req.getSkillType() != null && !req.getSkillType().isBlank()) {
            listing.setSkillType(req.getSkillType().trim().toUpperCase());
        }
        if (req.getProficiencyLevel() != null && !req.getProficiencyLevel().isBlank()) {
            listing.setProficiencyLevel(req.getProficiencyLevel().trim().toUpperCase());
        }
        if (req.getDescription() != null) {
            listing.setDescription(req.getDescription().trim());
        }
        if (req.getAvailability() != null) {
            listing.setAvailability(req.getAvailability().trim());
        }
        listing.setUpdatedAt(LocalDateTime.now());

        return skillListingRepository.save(listing);
    }

    /**
     * Delete skill listing with access control check
     */
    @Transactional
    public void deleteSkill(Long skillId, Long requestingUserId) {
        SkillListing listing = getSkillById(skillId);
        if (requestingUserId == null || !listing.getUser().getId().equals(requestingUserId)) {
            throw new SecurityException("Access Denied: You cannot delete another student's skill listing.");
        }
        skillRequestRepository.deleteBySkillId(skillId);
        skillListingRepository.delete(listing);
    }
}
