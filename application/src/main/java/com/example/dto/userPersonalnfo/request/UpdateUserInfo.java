package com.example.dto.userPersonalnfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfo {
    @JsonProperty("real_name")
    private String realName;

    private String gender;

    private String city;

    private String telephone;

    private Long course;

    private String university;
}
