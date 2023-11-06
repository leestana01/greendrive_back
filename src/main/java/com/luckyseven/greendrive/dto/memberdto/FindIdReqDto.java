package com.luckyseven.greendrive.dto.memberdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindIdReqDto {
    private String phoneNo;
    private String name;
}
