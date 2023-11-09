package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.user.userId = :userId and s.expireDate > CURRENT_TIMESTAMP")
    List<Subscription> findActiveSubscriptionsByUserId(String userId);
}
