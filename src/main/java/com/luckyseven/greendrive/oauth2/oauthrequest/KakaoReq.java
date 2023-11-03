package com.luckyseven.greendrive.oauth2.oauthrequest;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoReq {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirect;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authorization_code;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String client_secrete;
}