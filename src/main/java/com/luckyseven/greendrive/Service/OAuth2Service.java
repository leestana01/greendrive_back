package com.luckyseven.greendrive.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.memberdto.LoginResDto;
import com.luckyseven.greendrive.oauth2.oauthuserinfo.KakaoProfile;
import com.luckyseven.greendrive.oauth2.oauthuserinfo.ProfileDto;
import lombok.RequiredArgsConstructor;
import com.luckyseven.greendrive.oauth2.oauthrequest.OAuth2Request;
import com.luckyseven.greendrive.oauth2.oauthrequest.OAuthRequestFactory;
import com.luckyseven.greendrive.oauth2.oauthtoken.OAuth2AccessToken;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuthRequestFactory oAuthRequestFactory;
    private final UserRepository userRepository;
    @Transactional
    public OAuth2AccessToken getAccessToken(String code, String provider) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // token 주소와, 토큰 주소로 보낼 http request를 갖고있는 oAuth2Request
        OAuth2Request oAuth2Request=oAuthRequestFactory.getRequest(code,provider);

        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(oAuth2Request.getMap(), headers);


        // 카카오로 Access token 받아오기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> tokenResponse = rt.postForEntity(oAuth2Request.getUrl(), tokenRequest, String.class);


        // JSON Parsing (-> KakaoTokenDto)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OAuth2AccessToken oAuth2AccessToken = null;
        try {
            oAuth2AccessToken = objectMapper.readValue(tokenResponse.getBody(), OAuth2AccessToken.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oAuth2AccessToken;
    }

    @Transactional
    public ProfileDto getProfile(String accessToken, String provider) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        RestTemplate rt = new RestTemplate();

        //카카오 프로필 정보 받아오기
        ResponseEntity<String> response = rt.postForEntity(profileUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return extractProfile(response, provider);
        }
        else{
            throw new Exception("해당 access token에 대한 유저 정보를 불러올 수 없습니다.");
        }
    }


    private ProfileDto extractProfile(ResponseEntity<String> response, String provider) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoProfile kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);

        return new ProfileDto(kakaoProfile.getId(),kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getName(),kakaoProfile.getKakao_account().getPhone_number());
    }

    @Transactional
    public LoginResDto findUser(ProfileDto profileDto, String provider){

        try {
            User user = userRepository.findByUserId(profileDto.getEmail())
                    .orElseThrow(()->new Exception("회원가입 진행"));

            return LoginResDto.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .build();

        }
        catch(Exception e){
            Random rand = new Random();
            int tmp = rand.nextInt(900000)+100000;
            User user = User.builder()
                    .userId(profileDto.getEmail())
                    .userPassword(Integer.toString(tmp))
                    .carType("무")
                    .name(profileDto.getName())
                    .phoneNo(profileDto.getPhone_number())
                    .build();

            userRepository.save(user);

            return LoginResDto.builder()
                    .id(user.getId())
                    .userId(profileDto.getEmail())
                    .build();
        }

    }
}
