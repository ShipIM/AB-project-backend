package com.example.dto.authentication.response;

import com.example.model.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponseDto {

    private Long id;
    private String token;
    private String login;
    private Role role;

}
