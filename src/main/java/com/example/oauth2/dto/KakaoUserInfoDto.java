package com.example.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Getter
@Data
@Builder
public class KakaoUserInfoDto {

   private long id;
   private String nickname;
   private String profileImageUrl;
}
