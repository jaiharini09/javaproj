package com.skillbridge.repository;

import com.skillbridge.model.SkillRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRequestRepository extends JpaRepository<SkillRequest, Long> {

    List<SkillRequest> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);

    List<SkillRequest> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);

    List<SkillRequest> findByRequesterIdOrRecipientIdOrderByCreatedAtDesc(Long requesterId, Long recipientId);

    @Query("SELECT COUNT(r) FROM SkillRequest r WHERE (r.requester.id = :userId OR r.recipient.id = :userId) AND r.status = 'COMPLETED'")
    long countCompletedForUser(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM SkillRequest r WHERE r.requester.id = :userId OR r.recipient.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM SkillRequest r WHERE r.requestedSkill.id = :skillId")
    void deleteBySkillId(@Param("skillId") Long skillId);
}
