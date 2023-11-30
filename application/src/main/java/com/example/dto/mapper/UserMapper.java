package com.example.dto.mapper;

import com.example.dto.authentication.request.UserAuthRequestDto;
import com.example.dto.authentication.request.UserCreateRequestDto;
import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserCreateRequestDto userCreateRequestDto);

    User mapToUser(UserAuthRequestDto userAuthRequestDto);

    AuthenticationResponseDto mapToAuth(User user);
}
