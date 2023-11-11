package com.luckyseven.greendrive.dto.memberdto;

import com.luckyseven.greendrive.Domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InfoResDto {
    private Long id;
    private String userId;
    private String name;
    private byte[] profileImage;
    private String carType;
    private Integer isJudged;
}
