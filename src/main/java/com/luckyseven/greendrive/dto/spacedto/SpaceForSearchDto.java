package com.luckyseven.greendrive.dto.spacedto;

import lombok.Data;

@Data
// 검색 결과를 위한 Dto
public class SpaceForSearchDto {

    private String id;

    private String parkName; // 주차장명
    private String address; // 소재지지번주소
    private Integer type; // 주차장구분 (공영 : 0, 민영 : 1, 가짜(파트너): 2)
}
