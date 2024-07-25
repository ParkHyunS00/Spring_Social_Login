package com.example.oauth2.dto;

import java.util.Map;

public class NaverResponseImpl implements OAuth2Response{

    private final Map<String, Object> attribute;

    public NaverResponseImpl(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public Map<String, Object> getAttribute() {
        return this.attribute;
    }

    public String getBirthday() {
        return attribute.get("birthday").toString();
    }

    public String getMobile() {
        return attribute.get("mobile").toString();
    }
}
