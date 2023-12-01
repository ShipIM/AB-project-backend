package com.example.controller;

import com.example.dto.mapper.UserMapper;
import com.example.dto.page.request.PagingDto;
import com.example.dto.user.response.UserResponseDto;
import com.example.model.entity.User;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Контроллер для работы с пользователями")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(description = "Получить всех существующих пользователей")
    @GetMapping
    public Page<UserResponseDto> getUsers(@Valid PagingDto pagingDto) {
        Page<User> users = userService.getAllUsers(pagingDto.formPageRequest());

        return users.map(userMapper::mapToDto);
    }

    @Operation(description = "Удалить существующего пользователя по id")
    @DeleteMapping("/{userId}")
    public void deleteUser(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId) {
        userService.setUserInactive(Long.parseLong(userId));
    }
}
