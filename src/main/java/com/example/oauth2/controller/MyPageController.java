package com.example.oauth2.controller;

import com.example.oauth2.dto.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String profileImageUrl = (String)customOAuth2User.getAttributes().get("profile_image");
        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            profileImageUrl = "/images/default.jpg"; // 기본 이미지 경로 설정
            model.addAttribute("id", customOAuth2User.getAttributes().get("email"));
            model.addAttribute("nickname", customOAuth2User.getAttributes().get("name"));
        } else {
            model.addAttribute("id", customOAuth2User.getAttributes().get("id"));
            model.addAttribute("nickname", customOAuth2User.getAttributes().get("nickname"));
        }
        model.addAttribute("profileImageUrl", profileImageUrl);

        return "profile";
    }
}
