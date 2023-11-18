package com.example.dto.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateComment {
    private String author;

    @NotBlank(message = "Текст комментария не может состоять только из пробельных символов")
    @Size(min = 1, message = "Текст комментария должен содержать хотя бы один символ")
    @Size(min = 255, message = "Текст комментария не может быть длиннее 255 символов")
    private String text;

    @NotNull(message = "Id предмета не может быть null")
    @Min(value = 1, message = "Id предмета не может быть меньше 1")
    @JsonProperty("resource_id")
    private Long resourceId;
}
