package com.luckyseven.greendrive.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesDto {
    private String userId;
    private long reviewId;
}
