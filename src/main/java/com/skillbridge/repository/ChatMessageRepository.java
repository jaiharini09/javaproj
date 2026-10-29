package com.skillbridge.repository;

import com.skillbridge.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.sender.id = :u1 AND m.recipient.id = :u2) OR " +
           "(m.sender.id = :u2 AND m.recipient.id = :u1) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findConversation(@Param("u1") Long u1, @Param("u2") Long u2);

    List<ChatMessage> findByRecipientIdAndIsReadFalse(Long recipientId);

    long countByRecipientIdAndIsReadFalse(Long recipientId);

    @Query("SELECT DISTINCT m.sender.id FROM ChatMessage m WHERE m.recipient.id = :userId UNION SELECT DISTINCT m.recipient.id FROM ChatMessage m WHERE m.sender.id = :userId")
    List<Long> findDistinctPartnerIds(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM ChatMessage m WHERE m.sender.id = :userId OR m.recipient.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
