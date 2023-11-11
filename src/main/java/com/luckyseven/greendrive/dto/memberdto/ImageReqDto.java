package com.luckyseven.greendrive.dto.memberdto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageReqDto {

    private String userId;
    private MultipartFile image;

}