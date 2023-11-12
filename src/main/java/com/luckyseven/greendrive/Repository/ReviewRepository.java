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
    List<Review> findBySpaceId(@Param("spaceId") String spaceId);

    @Query("SELECT r FROM Review r WHERE r.user.userId =:userId")
    List<Review> findByUserId(@Param("userId") String userId);

    @Query("SELECT r FROM Review r WHERE r.user.userId =:userId AND r.space.id = :spaceId")
    List<Review> findByUserIdAndSpaceId(@Param("userId") String userId, @Param("spaceId") String spaceId);

    @Transactional
    @Modifying
    void deleteByUserIdAndSpaceId(long userId, String spaceId);

    @Query("SELECT r FROM Review r WHERE r.space.id =:spaceId")
    List<Review> findAllBySpaceId(@Param("spaceId") String spaceId);


}