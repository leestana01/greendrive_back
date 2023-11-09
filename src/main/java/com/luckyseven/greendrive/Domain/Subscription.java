package com.luckyseven.greendrive.Domain;

import com.luckyseven.greendrive.dto.SubscriptionDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue
    public long id;

    @ManyToOne(fetch = FetchType.LAZY)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    public Space space;

    public LocalDateTime startDate;
    public LocalDateTime expireDate;

    public SubscriptionDto toDto(){
        SubscriptionDto dto = new SubscriptionDto();

        dto.setUserId(this.user.getUserId());
        dto.setSpaceId(this.space.getId());
        dto.setStartDate(this.getStartDate());
        dto.setExpireDate(this.getExpireDate());

        return dto;
    }
}
