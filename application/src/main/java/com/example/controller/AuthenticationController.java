package com.example.controller;

import com.example.dto.authentication.request.UserAuthRequestDto;
import com.example.dto.authentication.request.UserCreateRequestDto;
import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.authentication.response.VerificationResponseDto;
import com.example.dto.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public AuthenticationResponseDto register(@RequestBody @Valid UserCreateRequestDto request) {
        User user = userMapper.mapToUser(request);

        return service.register(user);
    }

    @PostMapping("/authentication")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid UserAuthRequestDto request) {
        User user = userMapper.mapToUser(request);

        return service.authenticate(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/verification")
    public VerificationResponseDto verify() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return service.verify(userDetails.getUsername());
    }
}
