package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Service.OAuth2Service;
import com.luckyseven.greendrive.dto.LoginResDto;
import com.luckyseven.greendrive.oauth2.oauthuserinfo.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    @GetMapping("/login/oauth2/callback/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {

        String kakaoAccessToken = oAuth2Service.getAccessToken(code,"kakao").getAccess_token();

        try {

            ProfileDto profileDto = oAuth2Service.getProfile(kakaoAccessToken, "kakao");


            LoginResDto loginResDto=oAuth2Service.findUser(profileDto,"kakao");

            //TODO: response 설정 해야 됨
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponse.builder().result(loginResDto).build());


        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()));
        }
    }
}
