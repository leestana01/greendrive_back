package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {
    @Query("SELECT s FROM Space s WHERE " +
            "s.parkName LIKE %:keyword% OR s.address LIKE %:keyword% " +
            "OR (s.type = :type AND :type IS NOT NULL)")
    List<Space> findByKeywordOrType(@Param("keyword") String keyword, @Param("type") Integer type);

}