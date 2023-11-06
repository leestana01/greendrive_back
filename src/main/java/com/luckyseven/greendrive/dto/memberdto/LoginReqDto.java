package com.luckyseven.greendrive.dto.memberdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
    private String userId;
    private String password;
}
