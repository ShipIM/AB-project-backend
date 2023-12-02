package com.example.dto.status.request;

import com.example.constraint.UserStatusConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequestDto {
    @UserStatusConstraint(message = "Неизвестный статус пользователя")
    private String status;
}
