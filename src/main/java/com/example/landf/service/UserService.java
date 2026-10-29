package com.example.landf.service;

import com.example.landf.dto.AuthRequest;
import com.example.landf.dto.RegisterRequest;
import com.example.landf.dto.UserResponse;
import com.example.landf.model.User;
import com.example.landf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("An account with email " + req.getEmail() + " already exists.");
        }
        User user = new User(
                req.getName(),
                req.getEmail(),
                req.getPassword(),
                req.getCollege() != null ? req.getCollege() : "Easwari Engineering College",
                req.getDepartment() != null ? req.getDepartment() : "Computer Science",
                req.getYearOfStudy() != null ? req.getYearOfStudy() : "3rd Year",
                req.getBio() != null ? req.getBio() : "Excited to share and learn new skills!"
        );
        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    public UserResponse login(AuthRequest req) {
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        return new UserResponse(user);
    }

    public UserResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return new UserResponse(user);
    }

    public UserResponse updateProfile(Long id, User updateData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        if (updateData.getName() != null && !updateData.getName().isBlank()) {
            user.setName(updateData.getName());
        }
        if (updateData.getBio() != null) {
            user.setBio(updateData.getBio());
        }
        if (updateData.getCollege() != null) {
            user.setCollege(updateData.getCollege());
        }
        if (updateData.getDepartment() != null) {
            user.setDepartment(updateData.getDepartment());
        }
        if (updateData.getYearOfStudy() != null) {
            user.setYearOfStudy(updateData.getYearOfStudy());
        }
        if (updateData.getAvatarUrl() != null) {
            user.setAvatarUrl(updateData.getAvatarUrl());
        }
        if (updateData.getContactPhone() != null) {
            user.setContactPhone(updateData.getContactPhone());
        }
        User saved = userRepository.save(user);
        return new UserResponse(saved);
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }
}
