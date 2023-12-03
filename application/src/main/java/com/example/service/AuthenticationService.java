package com.example.service;

import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.authentication.response.VerificationResponseDto;
import com.example.dto.mapper.UserMapper;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.model.enumeration.Role;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponseDto register(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto authDto = userMapper.mapToAuth(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

    public AuthenticationResponseDto authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        var retrievedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));

        var jwtToken = jwtService.generateToken(retrievedUser);

        AuthenticationResponseDto authDto = userMapper.mapToAuth(retrievedUser);
        authDto.setToken(jwtToken);

        return authDto;
    }

    public VerificationResponseDto verify(String email) {
        var retrievedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));

        return userMapper.mapToVerify(retrievedUser);
    }
}
