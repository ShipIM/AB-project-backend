package com.example.controller;


import com.example.dto.mapper.UserInfoMapper;
import com.example.dto.userPersonalnfo.request.UpdateUserInfo;
import com.example.dto.userPersonalnfo.response.ResponseUserInfo;
import com.example.service.UserPersonalInfoService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Objects;

@RestController
@Tag(name = "user personal info", description = "Контроллер для работы с персональными данными пользователя")
@RequiredArgsConstructor
@Validated
public class UserPersonalInfoController {
    private final UserPersonalInfoService userPersonInfoService;
    private final UserService userService;
    private final UserInfoMapper userInfoMapper;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}/info")
    @Operation(description = "Получить персональные данные пользователя по идентификатору")
    public ResponseUserInfo getUser(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId) {
        var id = Long.parseLong(userId);
        var userInfo = userPersonInfoService.getUserInfo(id);
        var user = userService.getById(id);

        var responseUserInfo = userInfoMapper.mapToResponseUserInfo(userInfo);;
        responseUserInfo.setEmail(user.getEmail());
        responseUserInfo.setLogin(user.getLogin());

        return responseUserInfo;
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user/{userId}/info/avatar")
    @ResponseStatus(HttpStatus.CREATED)
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
            MultipartFile avatar) throws AccessDeniedException {
        var id = Long.parseLong(userId);
        var user = userService.getById(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userDetails.getUsername(), user.getUsername())) {
            throw new AccessDeniedException("У вас недостаточно прав для редактирования данных этого пользователя");
        }

        var userInfo = userInfoMapper.mapToUserInfoEntity(userInfoDto);
        userInfo.setId(id);

        try {
            byte[] bytes = avatar.getBytes();
            if (bytes != null) {
                userInfo.setAvatarBytes(Arrays.copyOf(bytes, bytes.length));
                userInfo.setAvatarContentType(avatar.getContentType());
            }
        } catch (IOException ignored) {
        }


        var updateUserInfo = userPersonInfoService.updateUserInfo(userInfo);

        var responseUserInfo = userInfoMapper.mapToResponseUserInfo(updateUserInfo);;
        responseUserInfo.setEmail(user.getEmail());
        responseUserInfo.setLogin(user.getLogin());

        return responseUserInfo;
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user/{userId}/info")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Обновить персональные данные пользователя (кроме аватара) по идентификатору")
    public ResponseUserInfo updateUserWithoutAvatar(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор пользователя должен быть положительным числом типа long")
            String userId,
            @RequestPart(value = "userInfo")
            @Valid
            UpdateUserInfo userInfoDto) throws AccessDeniedException {
        var id = Long.parseLong(userId);
        var user = userService.getById(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userDetails.getUsername(), user.getUsername())) {
            throw new AccessDeniedException("У вас недостаточно прав для редактирования данных этого пользователя");
        }

        var userInfo = userInfoMapper.mapToUserInfoEntity(userInfoDto);
        userInfo.setId(id);

        var updateUserInfo = userPersonInfoService.updateUserInfoWithoutAvatar(userInfo);

        var responseUserInfo = userInfoMapper.mapToResponseUserInfo(updateUserInfo);;
        responseUserInfo.setEmail(user.getEmail());
        responseUserInfo.setLogin(user.getLogin());

        return responseUserInfo;
    }
}
