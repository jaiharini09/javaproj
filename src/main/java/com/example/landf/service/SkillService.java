package com.example.landf.service;

import com.example.landf.dto.SkillListingRequest;
import com.example.landf.model.SkillListing;
import com.example.landf.model.User;
import com.example.landf.repository.SkillListingRepository;
import com.example.landf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillListingRepository skillListingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SkillListing> searchSkills(String query, String category, String skillType) {
        String cleanQuery = (query != null && !query.trim().isEmpty()) ? query.trim() : null;
        String cleanCategory = (category != null && !category.equalsIgnoreCase("ALL") && !category.trim().isEmpty()) ? category.trim() : null;
        String cleanSkillType = (skillType != null && !skillType.equalsIgnoreCase("ALL") && !skillType.trim().isEmpty()) ? skillType.trim() : null;

        return skillListingRepository.searchSkills(cleanQuery, cleanCategory, cleanSkillType);
    }

    public SkillListing getSkillById(Long id) {
        return skillListingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill listing not found with ID: " + id));
    }

    public List<SkillListing> getSkillsByUser(Long userId) {
        return skillListingRepository.findByUserId(userId);
    }

    public SkillListing createSkill(SkillListingRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + req.getUserId()));

        SkillListing listing = new SkillListing(
                user,
                req.getTitle(),
                req.getCategory(),
                req.getSkillType().toUpperCase(),
                req.getProficiencyLevel() != null ? req.getProficiencyLevel().toUpperCase() : "INTERMEDIATE",
                req.getDescription(),
                req.getAvailability() != null ? req.getAvailability() : "Flexible"
        );
        return skillListingRepository.save(listing);
    }

    public void deleteSkill(Long skillId, Long requestingUserId) {
        SkillListing listing = getSkillById(skillId);
        if (!listing.getUser().getId().equals(requestingUserId)) {
            throw new IllegalArgumentException("Unauthorized: you cannot delete another student's skill listing.");
        }
        skillListingRepository.delete(listing);
    }
}
