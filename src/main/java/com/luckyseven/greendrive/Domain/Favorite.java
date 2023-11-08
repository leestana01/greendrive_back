package com.luckyseven.greendrive.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luckyseven.greendrive.dto.FavoriteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Favorite {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;

    public FavoriteDto toDto(){
        FavoriteDto dto = new FavoriteDto();
        dto.setSpaceId(this.space.getId());
        dto.setUserId(this.user.getUserId());
        return dto;
    }

}
