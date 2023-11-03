package com.luckyseven.greendrive.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Space {

    @Id
    private String id; // 상황에 따라 GeneratedValue가 아닌 '주차장관리번호'로 관리
    // 프론트에는 간략한 정보만 제공하고, 상세 정보는 공공데이터 포털에서 해당 번호로 조회시키게 하면 됨

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

    @OneToMany(mappedBy = "space")
    private List<Review> reviewList; // 리뷰 목록
}
