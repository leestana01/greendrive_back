package com.luckyseven.greendrive.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
@ToString // 디버그용
// 카카오맵 마커 표시를 위한 전체 반환용 DTO
public class SpaceForMarkersDto {

    private String id;

    private String parkName; // 주차장명
    private Integer type; // 주차장구분 (공영 : 0, 민영 : 1, 가짜(파트너): 2)

    private double latitude; // 위도
    private double longitude; // 경도
}