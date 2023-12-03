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

    private String telephone;

    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор пользователя должен быть положительным числом типа long")
    private String course;

    private String university;

    @JsonAlias(value = {"resourceType", "resource_type", "type"})
    @ResourceTypeConstraint(message = "Неизвестный тип ресурса", canNull = true)
    private String resourceType;
}
