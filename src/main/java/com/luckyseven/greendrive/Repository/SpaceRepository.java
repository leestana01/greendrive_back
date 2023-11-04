package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    @Query("SELECT s FROM Space s WHERE s.id =:spaceId")
    Space findBySpaceId(String spaceId);
    @Query("SELECT r.user , r.space FROM Review r WHERE r.user.id =:userId AND r.space.id = :spaceId")
    List<Review> findAllReviewsByUserIdAndSpaceId(long userId, String spaceId);
}