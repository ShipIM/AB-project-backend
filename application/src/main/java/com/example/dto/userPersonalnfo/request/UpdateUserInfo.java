package com.example.dto.userPersonalnfo.request;

import com.example.constraint.ResourceTypeConstraint;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfo {
    @JsonProperty("real_name")
    private String realName;

    private String gender;

    private String city;

    @Pattern(regexp = "^[78]\\d{10}$|^$",
            message = "Телефонный номер должен быть формата: (7/8)-nnn-nnn-nn-nn")
    private String telephone;

    @Pattern(regexp = "^(?!0+$)\\d{1,19}$|^$",
            message = "Идентификатор курса должен быть положительным числом типа long")
    private String course;

    private String university;
}
