package com.example.dto.userPersonalnfo.request;

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

    private String telephone;

    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор пользователя должен быть положительным числом типа long")
    private String course;

    private String university;
}
