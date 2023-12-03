package com.example.dto.userPersonalnfo.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserInfo {
    private Long id;

    @JsonProperty("real_name")
    private String realName;

    private String gender;

    private String city;

    private String telephone;

    private Long course;

    private String university;

    @JsonProperty("avatar_bytes")
    private byte[] avatarBytes;

    @JsonProperty("avatar_content_type")
    private String avatarContentType;
}
