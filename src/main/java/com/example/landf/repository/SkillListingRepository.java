package com.example.landf.repository;

import com.example.landf.model.SkillListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillListingRepository extends JpaRepository<SkillListing, Long> {
    
    List<SkillListing> findBySkillType(String skillType);
    
    List<SkillListing> findByCategory(String category);
    
    List<SkillListing> findByUserId(Long userId);
    
    @Query("SELECT s FROM SkillListing s WHERE " +
           "(:query IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.user.name) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:skillType IS NULL OR s.skillType = :skillType) " +
           "ORDER BY s.createdAt DESC")
    List<SkillListing> searchSkills(@Param("query") String query, 
                                    @Param("category") String category, 
                                    @Param("skillType") String skillType);
}
