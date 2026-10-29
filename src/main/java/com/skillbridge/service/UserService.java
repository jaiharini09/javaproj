package com.skillbridge.service;

import com.skillbridge.dto.AuthRequest;
import com.skillbridge.dto.RegisterRequest;
import com.skillbridge.dto.UserProfileResponse;
import com.skillbridge.dto.UserResponse;
import com.skillbridge.model.SkillListing;
import com.skillbridge.model.User;
import com.skillbridge.repository.ChatMessageRepository;
import com.skillbridge.repository.SkillListingRepository;
import com.skillbridge.repository.SkillRequestRepository;
import com.skillbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillListingRepository skillListingRepository;

    @Autowired
    private SkillRequestRepository skillRequestRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    /**
     * Student Registration (Profile Creation)
     */
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("An account with email " + req.getEmail() + " already exists.");
        }
        User user = new User(
                req.getName().trim(),
                req.getEmail().trim().toLowerCase(),
                req.getPassword(),
                (req.getCollege() != null && !req.getCollege().isBlank()) ? req.getCollege().trim() : "Easwari Engineering College",
                (req.getDepartment() != null && !req.getDepartment().isBlank()) ? req.getDepartment().trim() : "Computer Science",
                (req.getYearOfStudy() != null && !req.getYearOfStudy().isBlank()) ? req.getYearOfStudy().trim() : "3rd Year",
                (req.getBio() != null && !req.getBio().isBlank()) ? req.getBio().trim() : "Excited to share and learn new skills on SkillBridge!"
        );
        if (req.getContactPhone() != null) user.setContactPhone(req.getContactPhone().trim());
        if (req.getAvatarUrl() != null && !req.getAvatarUrl().isBlank()) {
            user.setAvatarUrl(req.getAvatarUrl().trim());
        } else {
            user.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        }

        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    /**
     * Student Authentication / Login
     */
    public UserResponse login(AuthRequest req) {
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail().trim().toLowerCase());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        return new UserResponse(user);
    }

    /**
     * View basic student profile
     */
    public UserResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return new UserResponse(user);
    }

    /**
     * View complete student profile including offered skills and learning interests
     * Fulfills abstract: "Each profile displays relevant information, including the
     * student's name, biography, offered skills, and learning interests."
     */
    public UserProfileResponse getFullProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        List<SkillListing> offeredSkills = skillListingRepository.findByUserIdAndSkillType(id, "TEACH");
        List<SkillListing> learningInterests = skillListingRepository.findByUserIdAndSkillType(id, "LEARN");
        long completedCount = skillRequestRepository.countCompletedForUser(id);

        return new UserProfileResponse(user, offeredSkills, learningInterests, completedCount);
    }

    /**
     * Update profile information with access control check
     * Fulfills abstract: "supports creating, viewing, updating, and deleting profile information with appropriate access restrictions."
     */
    public UserResponse updateProfile(Long id, Long requestingUserId, User updateData) {
        if (requestingUserId == null || !requestingUserId.equals(id)) {
            throw new SecurityException("Access Denied: You can only update your own profile.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (updateData.getName() != null && !updateData.getName().isBlank()) {
            user.setName(updateData.getName().trim());
        }
        if (updateData.getBio() != null) {
            user.setBio(updateData.getBio().trim());
        }
        if (updateData.getCollege() != null) {
            user.setCollege(updateData.getCollege().trim());
        }
        if (updateData.getDepartment() != null) {
            user.setDepartment(updateData.getDepartment().trim());
        }
        if (updateData.getYearOfStudy() != null) {
            user.setYearOfStudy(updateData.getYearOfStudy().trim());
        }
        if (updateData.getAvatarUrl() != null) {
            user.setAvatarUrl(updateData.getAvatarUrl().trim());
        }
        if (updateData.getContactPhone() != null) {
            user.setContactPhone(updateData.getContactPhone().trim());
        }

        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    /**
     * Delete specific profile field (e.g. bio, phone, avatar) with access control check
     */
    public UserResponse clearProfileField(Long id, Long requestingUserId, String fieldName) {
        if (requestingUserId == null || !requestingUserId.equals(id)) {
            throw new SecurityException("Access Denied: You can only modify your own profile.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if ("bio".equalsIgnoreCase(fieldName)) {
            user.setBio("");
        } else if ("phone".equalsIgnoreCase(fieldName) || "contactPhone".equalsIgnoreCase(fieldName)) {
            user.setContactPhone("");
        } else if ("avatar".equalsIgnoreCase(fieldName) || "avatarUrl".equalsIgnoreCase(fieldName)) {
            user.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        }

        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    /**
     * Delete profile / account with access control check and transactional cleanup
     * Fulfills abstract: "supports creating, viewing, updating, and deleting profile information with appropriate access restrictions."
     */
    @Transactional
    public void deleteProfile(Long id, Long requestingUserId) {
        if (requestingUserId == null || !requestingUserId.equals(id)) {
            throw new SecurityException("Access Denied: You cannot delete another student's account.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        // Cascade cleanup: messages, exchange requests, and skill listings
        chatMessageRepository.deleteByUserId(id);
        skillRequestRepository.deleteByUserId(id);
        skillListingRepository.deleteByUserId(id);
        userRepository.delete(user);
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }
}
