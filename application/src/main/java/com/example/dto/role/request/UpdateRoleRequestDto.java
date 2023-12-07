package com.example.dto.role.request;

import com.example.constraint.RoleConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequestDto {
    @RoleConstraint(message = "Неизвестная роль")
    private String role;
}
