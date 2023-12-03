package com.example.dto.mapper;


import com.example.dto.authentication.request.AuthenticateRequestDto;
import com.example.dto.authentication.request.RegisterRequestDto;
import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.authentication.response.VerificationResponseDto;
import com.example.dto.user.response.UserResponseDto;
import com.example.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(RegisterRequestDto registerRequestDto);

    User mapToUser(AuthenticateRequestDto authenticateRequestDto);

    UserResponseDto mapToDto(User user);

    List<UserResponseDto> mapToDtoList(List<User> users);

    AuthenticationResponseDto mapToAuth(User user);

    VerificationResponseDto mapToVerify(User user);
}
