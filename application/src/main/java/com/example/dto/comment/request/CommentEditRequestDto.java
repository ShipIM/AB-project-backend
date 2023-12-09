package com.example.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentEditRequestDto {

    @NotBlank(message = "Текст комментария не может состоять только из пробельных символов")
    @Size(min = 1, message = "Текст комментария должен содержать хотя бы один символ")
    @Size(max = 255, message = "Текст комментария не может быть длиннее 255 символов")
    private String text;

}
