package com.example.oauth2.dto;

import java.util.Map;

public class KakaoResponseImpl implements OAuth2Response {

    private final Map<String, Object> attribute;
    private final String id;

    public KakaoResponseImpl(Map<String, Object> attribute, String id) {
        this.attribute = (Map<String, Object>) attribute.get("properties");
        this.id = id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        this.attribute.put("id", id);
        return this.attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return attribute.get("nickname").toString();
    }

    @Override
    public Map<String, Object> getAttribute() {
        return this.attribute;
    }
}
