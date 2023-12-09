package com.example.dto.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {
    @NotBlank(message = "Идентификатор автора не должен быть пустой строкой")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор автора должен быть положительным числом типа long")
    @JsonProperty("author_id")
    private String authorId;

    @NotBlank(message = "Текст комментария не может состоять только из пробельных символов")
    @Size(min = 1, message = "Текст комментария должен содержать хотя бы один символ")
    @Size(max = 255, message = "Текст комментария не может быть длиннее 255 символов")
    private String text;

    @NotBlank(message = "Идентификатор источника не должен быть пустой строкой")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор источника должен быть положительным числом типа long")
    @JsonProperty("source_id")
    private String sourceId;

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;
}
