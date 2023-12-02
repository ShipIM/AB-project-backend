package com.example.controller;


import com.example.dto.mapper.UserInfoMapper;
import com.example.dto.userPersonalnfo.request.UpdateUserInfo;
import com.example.dto.userPersonalnfo.response.ResponseUserInfo;
import com.example.service.UserPersonalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "user personal info", description = "Контроллер для работы с персональными данными пользователя")
@RequiredArgsConstructor
@Validated
public class UserPersonalInfoController {
    private final UserPersonalInfoService userPersonInfoService;
    private final UserInfoMapper userInfoMapper;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}/info")
    @Operation(description = "Получить персональные данные пользователя по идентификатору")
    public ResponseUserInfo getUser(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId) {
        var userInfo = userPersonInfoService.getUserInfo(Long.parseLong(userId));

        return userInfoMapper.mapToResponseUserInfo(userInfo);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user/{userId}/info")
    @Operation(description = "Обновить персональные данные пользователя по идентификатору")
    public ResponseUserInfo updateUser(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId,
            @Valid UpdateUserInfo userInfoDto) {
        var userInfo = userInfoMapper.mapToUserInfoEntity(userInfoDto);
        userInfo.setId(Long.parseLong(userId));

        var updateUserInfo = userPersonInfoService.updateUserInfo(userInfo);

        return userInfoMapper.mapToResponseUserInfo(updateUserInfo);
    }
}
