package com.example.dto.mapper;

import com.example.dto.authentication.request.UserRequestDto;
import com.example.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserRequestDto userRequestDto);
}
