package com.luckyseven.greendrive.dto;

import lombok.Data;
import lombok.Setter;

import java.time.LocalTime;

@Data
public class SpaceResDto {

    private String id;

    private String parkName; // 주차장명
    private String address; // 소재지지번주소
    private Integer type; // 주차장구분 (공영 : 0, 민영 : 1, 가짜(파트너): 2)

    private double latitude; // 위도
    private double longitude; // 경도

    private LocalTime weekdayStart; // 평일운영시작시각
    private LocalTime weekdayEnd; // 평일운영종료시각
    private LocalTime saturdayStart; // 토요일운영시작시각
    private LocalTime saturdayEnd; // 토요일운영종료시각
    private LocalTime holidayStart; // 공휴일운영시작시각
    private LocalTime holidayEnd; // 공휴일운영종료시각
}
