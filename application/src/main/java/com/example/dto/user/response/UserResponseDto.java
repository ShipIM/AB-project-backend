package com.example.dto.user.response;

import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Status status;
}
