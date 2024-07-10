package com.example.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class KakaoAccessResultDto {
    private String message;
    private Object result;

    public KakaoAccessResultDto(String message, Object result) {
        this.message = message;
        this.result  = result;
    }
}
