package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Domain.Subscription;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.Repository.SubscriptionRepository;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.SubscriptionDto;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    public final SubscriptionRepository subscriptionRepository;
    public final UserRepository userRepository;
    public final SpaceRepository spaceRepository;

    public SubscriptionDto addSubscription(SubscriptionDto dto) {
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId()));
        Space space = spaceRepository.findById(dto.getSpaceId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getSpaceId()));

        if (dto.getStartDate() == null | dto.getExpireDate() == null){
            throw new EntityNotFoundException(dto.getStartDate() + " 또는 " + dto.getSpaceId());
        }
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSpace(space);
        subscription.setStartDate(dto.getStartDate());
        subscription.setExpireDate(dto.getExpireDate());

        subscriptionRepository.save(subscription);

        return subscription.toDto();
    }

    public List<SubscriptionDto> getActiveSubscriptionsByUser(String userId) {
        return subscriptionRepository.findActiveSubscriptionsByUserId(userId).stream().map(Subscription::toDto).collect(Collectors.toList());
    }

}
