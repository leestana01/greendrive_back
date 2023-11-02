package com.luckyseven.greendrive.Domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Space {

    @Id
    @GeneratedValue
    private long id; // 상황에 따라 GeneratedValue가 아닌 '주차장 관리번호'로 관리
    // 프론트에는 간략한 정보만 제공하고, 상세 정보는 공공데이터 포털에서 해당 번호로 조회시키게 하면 됨

    private String address; // 주소
    private String weekdayTime; // 주중 운영시간
    private String saturdayTime; // 토요일 운영시간
    private String holidayTime; // 공휴일 운영시간

    private long price; // 가격

    private String isPublic; // 공영인가?

}
