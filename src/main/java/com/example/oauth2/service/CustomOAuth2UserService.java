package com.example.oauth2.service;

import com.example.oauth2.dto.*;
import com.example.oauth2.entity.UserEntity;
import com.example.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService { // OAuth2UserService 의 구현체

    private final UserRepository userRepository;

    // 사용자 정보 파라미터로 가져오기
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 인증 제공자가 누구인지
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponseImpl(oAuth2User.getAttributes());
            log.info("네이버 사용자 정보 = {}", oAuth2User.getAttributes());
            NaverResponseImpl naverResponse = (NaverResponseImpl) oAuth2Response;

        } else if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponseImpl(oAuth2User.getAttributes(), oAuth2User.getAttributes().get("id").toString());

            KakaoResponseImpl kakaoResponse = (KakaoResponseImpl) oAuth2Response;
        } else if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponseImpl(oAuth2User.getAttributes());
        } else {
            return null;
        }


        String userName = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        UserEntity existData = userRepository.findByUserName(userName);
        String role = "ROLE_USER";

        if (existData == null) { // 첫 로그인

            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole(role);

            userRepository.save(userEntity);

        } else { // 존재하는 회원인 경우

            role = existData.getRole();

            existData.setUserName(oAuth2Response.getName());
            existData.setEmail(oAuth2Response.getEmail());
            userRepository.save(existData);
        }


        return new CustomOAuth2User(oAuth2Response, role);
    }

}
