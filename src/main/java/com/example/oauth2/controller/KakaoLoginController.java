package com.example.oauth2.controller;

import com.example.oauth2.dto.KakaoUserInfoDto;
import com.example.oauth2.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/callback")
    public String callback(HttpServletRequest request, Model model) throws Exception {
        KakaoUserInfoDto kakaoUserInfo = kakaoLoginService.getAccessToken(request.getParameter("code"));

        model.addAttribute("userInfo", kakaoUserInfo);

        return "profile";
    }
}
