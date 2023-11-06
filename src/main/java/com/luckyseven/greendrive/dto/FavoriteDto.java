package com.luckyseven.greendrive.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDto {
    private long id;
    private String spaceId;
}
