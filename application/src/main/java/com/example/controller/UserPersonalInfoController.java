package com.example.controller;


import com.example.dto.mapper.UserInfoMapper;
import com.example.dto.resource.request.CreateResourceRequestDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
            @RequestPart(value = "userInfo")
            @Valid
            UpdateUserInfo userInfoDto,
            @RequestPart(value = "avatar")
            MultipartFile avatar) throws IOException {
        var userInfo = userInfoMapper.mapToUserInfoEntity(userInfoDto);
        userInfo.setId(Long.parseLong(userId));

        byte[] bytes = avatar.getBytes();
        if ( bytes != null ) {
            userInfo.setAvatarBytes( Arrays.copyOf( bytes, bytes.length ) );
            userInfo.setAvatarContentType(avatar.getContentType());
        }

        var updateUserInfo = userPersonInfoService.updateUserInfo(userInfo);

        return userInfoMapper.mapToResponseUserInfo(updateUserInfo);
    }
}
