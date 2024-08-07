package com.example.oauth2.dto;

import java.util.Map;

public class GoogleResponseImpl implements OAuth2Response{

    private final Map<String, Object> attribute;

    public GoogleResponseImpl(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
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
}
