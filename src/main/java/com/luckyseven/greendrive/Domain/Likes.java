package com.luckyseven.greendrive.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luckyseven.greendrive.dto.FavoriteDto;
import com.luckyseven.greendrive.dto.LikesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Likes {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public LikesDto toDto(){
        LikesDto dto = new LikesDto();
        dto.setUserId(this.user.getUserId());
        dto.setReviewId(this.review.getId());
        return dto;
    }

}
