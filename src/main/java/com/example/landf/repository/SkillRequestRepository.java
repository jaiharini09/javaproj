package com.example.landf.repository;

import com.example.landf.model.SkillRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRequestRepository extends JpaRepository<SkillRequest, Long> {
    
    List<SkillRequest> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);
    
    List<SkillRequest> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);
    
    List<SkillRequest> findByRequesterIdOrRecipientIdOrderByCreatedAtDesc(Long requesterId, Long recipientId);
}
