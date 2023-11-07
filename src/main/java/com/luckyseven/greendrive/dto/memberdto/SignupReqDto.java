package com.luckyseven.greendrive.dto.memberdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupReqDto {
    private String name;
    private String userId;
    private String password;
    private String carType;
    private String phoneNo;
}
