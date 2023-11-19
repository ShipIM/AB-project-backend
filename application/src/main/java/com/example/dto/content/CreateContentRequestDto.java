package com.example.dto.content;

import com.example.constraint.ContentTypeConstraint;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateContentRequestDto {

    @NotBlank
    @Size(max = 30, message = "Длина названия файла не должна превышать 30 символов")
    private String filename;

    private byte[] bytes;

    @JsonAlias(value = {"contentType, content_type"})
    @ContentTypeConstraint(message = "Неизвестный тип содержимого")
    private String contentType;

    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Размер содержимого должен быть положительным числом типа long")
    private String size;

}
