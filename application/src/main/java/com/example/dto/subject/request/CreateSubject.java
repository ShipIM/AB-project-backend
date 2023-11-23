package com.example.dto.subject.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubject {
    @Size(min = 1, message = "Название предмета должно состоять хотя бы из одного символа")
    @Size(max = 30, message = "Название предмета не может быть длиннее 30 символов")
    @NotBlank(message = "Название предмета не может состоять только из пробельных символов")
    private String name;

    @JsonProperty("course_id")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор курса должен быть положительным числом типа long")
    private String courseId;
}
