package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySpaceId(String spaceId);

    List<Review> findByUserId(long userId);

    @Query("SELECT r FROM Review r WHERE r.user.id =:userId AND r.space.id = :spaceId")
    List<Review> findReviewByUserIdAndSpaceId(long userId, String spaceId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.user.id =:userId AND r.space.id = :spaceId")
    void deleteByUserIdAndSpaceId(long userId, String spaceId);


}