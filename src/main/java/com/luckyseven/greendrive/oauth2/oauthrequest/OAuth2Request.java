package com.luckyseven.greendrive.oauth2.oauthrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class OAuth2Request {
    private String url;
    private LinkedMultiValueMap<String, String> map;
}