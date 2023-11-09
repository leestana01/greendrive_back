package com.luckyseven.greendrive.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {

    private String userId;
    private String spaceId;
    public LocalDateTime startDate;
    public LocalDateTime expireDate;

}