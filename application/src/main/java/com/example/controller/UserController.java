package com.example.controller;

import com.example.dto.mapper.UserMapper;
import com.example.dto.page.request.PagingDto;
import com.example.dto.status.request.UpdateStatusRequestDto;
import com.example.dto.user.response.UserResponseDto;
import com.example.model.entity.User;
import com.example.model.enumeration.Status;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Получить всех существующих пользователей")
    @GetMapping
    public Page<UserResponseDto> getUsers(@Valid PagingDto pagingDto) {
        Page<User> users = userService.getAllUsers(pagingDto.formPageRequest());

        return users.map(userMapper::mapToDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Изменить статус пользователя по id")
    @PatchMapping("/{userId}")
    public void updateUserStatus(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId,
            @RequestBody @Valid UpdateStatusRequestDto dto) {
        userService.setUserStatus(Long.parseLong(userId), Status.valueOf(dto.getStatus()));
    }
}
