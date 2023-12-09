package com.example.dto.feedNews.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedNewsCreateDto {
    @NotBlank
    @Size(max = 30, message = "Название новости не должно превышать 30 символов")
    private String name;

    @NotBlank(message = "Идентификатор автора не должен быть пустой строкой")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Идентификатор автора должен быть положительным числом типа long")
    @JsonProperty("author_id")
    private String authorId;

    @NotBlank
    @Size(max = 500, message = "Текст новости не должен превышать 500 символов")
    private String text;
}
