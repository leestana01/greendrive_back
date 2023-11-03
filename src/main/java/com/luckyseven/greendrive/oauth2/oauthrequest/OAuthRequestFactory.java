package com.luckyseven.greendrive.oauth2.oauthrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;


@RequiredArgsConstructor
@Component
public class OAuthRequestFactory {

    private final KakaoReq kakaoReq;
    public OAuth2Request getRequest(String code, String provider){
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", kakaoReq.getAuthorization_code()); //카카오 공식문서 기준 authorization_code 로 고정
        map.add("client_id", kakaoReq.getKakaoClientId()); // 카카오 Dev 앱 REST API 키
        map.add("redirect_uri", kakaoReq.getKakaoRedirect()); // 카카오 Dev redirect uri
        map.add("code", code); // 프론트에서 인가 코드 요청시 받은 인가 코드값
        map.add("client_secret", kakaoReq.getClient_secrete()); // 카카오 Dev 카카오 로그인 Client Secret


        return new OAuth2Request("https://kauth.kakao.com/oauth/token",map);
    }

    public String getProfileUrl(String provider) {
        return "https://kapi.kakao.com/v2/user/me";
    }

}
