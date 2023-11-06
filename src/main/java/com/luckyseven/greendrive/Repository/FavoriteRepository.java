package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT f FROM Favorite f WHERE f.user.userId =:userId")
    List<Favorite> findByUserId(@Param("userId") String userId);
}
