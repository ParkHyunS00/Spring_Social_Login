package com.example.oauth2.controller;


import com.example.oauth2.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.url}")
    private String redirectUri;

    @GetMapping("/")
    public String login(Model model) {
        log.info("Home Controller");

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?")
                .append("client_id="+clientId)
                .append("&redirect_uri="+redirectUri)
                .append("&response_type=code");

        model.addAttribute("kakaoLogin", url.toString());

        return "index";
    }
}
