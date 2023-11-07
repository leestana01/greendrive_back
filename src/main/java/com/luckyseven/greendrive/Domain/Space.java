package com.luckyseven.greendrive.Domain;

import com.luckyseven.greendrive.dto.SpaceForSearchDto;
import com.luckyseven.greendrive.dto.SpaceReqDto;
import com.luckyseven.greendrive.dto.SpaceForMarkersDto;
import com.luckyseven.greendrive.dto.SpaceResDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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
    private List<Review> reviewList = new ArrayList<>(); // 리뷰 목록

    public Space(SpaceReqDto dto) {
        this.id = dto.getId();
        this.parkName = dto.getParkName();
        this.address = dto.getAddress();
        this.type = dto.getType();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.weekdayStart = dto.getWeekdayStart();
        this.weekdayEnd = dto.getWeekdayEnd();
        this.saturdayStart = dto.getSaturdayStart();
        this.saturdayEnd = dto.getSaturdayEnd();
        this.holidayStart = dto.getHolidayStart();
        this.holidayEnd = dto.getHolidayEnd();
    }

    public SpaceResDto toDTO() {
        SpaceResDto dto = new SpaceResDto();

        dto.setId(this.id);
        dto.setParkName(this.parkName);
        dto.setAddress(this.address);
        dto.setType(this.type);
        dto.setLatitude(this.latitude);
        dto.setLongitude(this.longitude);
        dto.setWeekdayStart(this.weekdayStart);
        dto.setWeekdayEnd(this.weekdayEnd);
        dto.setSaturdayStart(this.saturdayStart);
        dto.setSaturdayEnd(this.saturdayEnd);
        dto.setHolidayStart(this.holidayStart);
        dto.setHolidayEnd(this.holidayEnd);

        return dto;
    }

    public SpaceForMarkersDto toDTOforMarkers(){
        SpaceForMarkersDto dto = new SpaceForMarkersDto();

        dto.setId(this.id);
        dto.setType(this.type);
        dto.setLatitude(this.latitude);
        dto.setLongitude(this.longitude);
        return dto;
    }

    public SpaceForSearchDto toDTOforSearch(){
        SpaceForSearchDto dto = new SpaceForSearchDto();

        dto.setId(this.id);
        dto.setType(this.type);
        dto.setAddress(this.address);
        dto.setParkName(this.parkName);
        return dto;
    }

    public Space updateFromDto(SpaceReqDto dto) {
        if (dto.getParkName() != null) {
            this.parkName = dto.getParkName();
        }
        if (dto.getAddress() != null) {
            this.address = dto.getAddress();
        }
        if (dto.getType() != null) {
            this.type = dto.getType();
        }

        if (dto.getLatitude() != 0) {
            this.latitude = dto.getLatitude();
        }
        if (dto.getLongitude() != 0) {
            this.longitude = dto.getLongitude();
        }

        if (dto.getWeekdayStart() != null) {
            this.weekdayStart = dto.getWeekdayStart();
        }
        if (dto.getWeekdayEnd() != null) {
            this.weekdayEnd = dto.getWeekdayEnd();
        }
        if (dto.getSaturdayStart() != null) {
            this.saturdayStart = dto.getSaturdayStart();
        }
        if (dto.getSaturdayEnd() != null) {
            this.saturdayEnd = dto.getSaturdayEnd();
        }
        if (dto.getHolidayStart() != null) {
            this.holidayStart = dto.getHolidayStart();
        }
        if (dto.getHolidayEnd() != null) {
            this.holidayEnd = dto.getHolidayEnd();
        }
        return this;
    }

}
