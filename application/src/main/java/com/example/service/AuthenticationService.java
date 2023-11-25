package com.example.service;

import com.example.dto.authentication.response.AuthenticationResponseDto;
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

    public AuthenticationResponseDto register(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponseDto(jwtToken);
    }

    public AuthenticationResponseDto authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        var retrievedUser = userRepository.findByEmail(user.getEmail());

        var jwtToken = jwtService.generateToken(retrievedUser
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует")));

        return new AuthenticationResponseDto(jwtToken);
    }
}
