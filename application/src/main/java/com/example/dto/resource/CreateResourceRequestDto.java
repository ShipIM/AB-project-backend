package com.example.dto.resource;

import com.example.constraint.ResourceTypeConstraint;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateResourceRequestDto {

    @NotBlank
    @Size(max = 30, message = "Название ресурса не должно превышать 30 символов")
    private String name;

    @Size(max = 30, message = "Имя автора не должно превышать 30 символов")
    private String author;

    @JsonAlias(value = {"resourceType", "resource_type"})
    @ResourceTypeConstraint
    private String resourceType;

    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор предмета должен быть положительным числом типа long")
    private String subject;
}
