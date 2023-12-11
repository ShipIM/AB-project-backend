package com.example.controller;

import com.example.dto.authentication.request.AuthenticateRequestDto;
import com.example.dto.authentication.request.RegisterRequestDto;
import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.authentication.response.VerificationResponseDto;
import com.example.dto.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth", description = "Контроллер для регистрации и авторизации")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public AuthenticationResponseDto register(@RequestBody @Valid RegisterRequestDto request) {
        User user = userMapper.mapToUser(request);

        user = service.register(user);

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto authDto = userMapper.mapToAuth(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

    @PostMapping("/authentication")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticateRequestDto request) {
        User user = userMapper.mapToUser(request);

        user = service.authenticate(user);

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto authDto = userMapper.mapToAuth(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/verification")
    public VerificationResponseDto verify() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = service.verify(userDetails.getUsername());

        return userMapper.mapToVerify(user);
    }
}
