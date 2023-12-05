package com.example.dto.resource.request;

import com.example.constraint.ResourceTypeConstraint;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResourceRequestDto {

    @NotBlank
    @Size(max = 30, message = "Название ресурса не должно превышать 30 символов")
    private String name;

    @NotBlank(message = "Идентификатор автора не должен быть пустой строкой")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор автора должен быть положительным числом типа long")
    @JsonProperty("author_id")
    private String authorId;

    @JsonAlias(value = {"resourceType", "resource_type", "type"})
    @ResourceTypeConstraint(message = "Неизвестный тип ресурса")
    private String resourceType;

    @JsonAlias(value = {"subject", "subjectId", "subject_id"})
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор предмета должен быть положительным числом типа long")
    private String subjectId;
}
