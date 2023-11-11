package com.luckyseven.greendrive.Repository;

import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Domain.Subscription;
import com.luckyseven.greendrive.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.user = :user and s.expireDate > CURRENT_TIMESTAMP")
    List<Subscription> findActiveSubscriptionsByUser(User user);

    @Query("select count(s) > 0 from Subscription s where s.user = :user and s.space = :space and s.expireDate > CURRENT_TIMESTAMP")
    boolean existsActiveSubscriptionByUserAndSpace(User user, Space space);

}
