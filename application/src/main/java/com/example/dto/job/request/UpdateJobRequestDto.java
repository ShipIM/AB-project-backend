package com.example.dto.job.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobRequestDto {
    @NotBlank(message = "Время исполнения не должно быть пустым")
    @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
            message = "Время исполнения должно быть положительным числом типа long")
    private String time;
}
