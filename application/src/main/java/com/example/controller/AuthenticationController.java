package com.example.controller;

import com.example.dto.authentication.request.AuthenticateRequestDto;
import com.example.dto.authentication.request.RegisterRequestDto;
import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody @Valid RegisterRequestDto request) {
        User user = userMapper.mapToUser(request);

        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthenticateRequestDto request) {
        User user = userMapper.mapToUser(request);

        return ResponseEntity.ok(service.authenticate(user));
    }

}
