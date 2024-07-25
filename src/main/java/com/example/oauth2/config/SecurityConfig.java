package com.example.oauth2.config;

import com.example.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 활성화
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());
        http.formLogin((login) -> login.disable());
        http.httpBasic((basic) -> basic.disable());
        http.oauth2Login((oauth2) -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/mypage", true)
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))); // 소셜 로그인
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/oauth2/**", "/login/**").permitAll() // oauth2 시작 경로, login 시작 경로 아무나 접속
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 자원 검토 무시
                .anyRequest().authenticated()); // 그 외 나머지 경로는 로그인 한 사람만

        return http.build();
    }
}