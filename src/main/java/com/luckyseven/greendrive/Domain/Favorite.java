package com.luckyseven.greendrive.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luckyseven.greendrive.dto.FavoriteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private final User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private final Space space;

    public FavoriteDto toDto(){
        FavoriteDto dto = new FavoriteDto();
        dto.setSpaceId(this.space.getId());
        dto.setUserId(this.user.getUserId());
        return dto;
    }

}
