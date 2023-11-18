package com.example.dto.subject.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubject {
    @Size(min=1, message = "Название предмета должно состоять хотя бы из одного символа")
    @Size(max=30, message = "Название предмета не может быть длиннее 30 символов")
    @NotBlank(message = "Название предмета не может состоять только из пробельных символов")
    private String name;

    @JsonProperty("course_id")
    @Min(value = 1, message = "Id курса не может быть меньше 1")
    @NotNull(message = "Id курса не может быть null")
    private Long courseId;
}
