package com.example.oauth2.service;

import com.example.oauth2.dto.KakaoUserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.JSONParser;

@Service
@Slf4j
public class KakaoLoginService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.url}")
    private String redirectUrl;

    @Value("${kakao.client.secret}")
    private String clientSecret;
    private final String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final String KAKAO_API_URI = "https://kapi.kakao.com";

    public KakaoUserInfoDto getAccessToken(String code) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id"    , clientId);
            params.add("client_secret", clientSecret);
            params.add("code"         , code);
            params.add("redirect_uri" , redirectUrl);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) jsonObject.get("access_token");
            refreshToken = (String) jsonObject.get("refresh_token");
        } catch (Exception e) {
            throw new Exception("API Call Failed!");
        }

        return getKakaoUserInfo(accessToken);
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long) jsonObj.get("id");
        String nickname = String.valueOf(profile.get("nickname"));
        String profileImageUrl = String.valueOf(profile.get("thumbnail_image_url"));

        return KakaoUserInfoDto.builder()
                .id(id)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl).build();
    }
}
