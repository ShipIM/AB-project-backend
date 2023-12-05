package com.example.service;

import com.example.dto.authentication.response.AuthenticationResponseDto;
import com.example.dto.authentication.response.VerificationResponseDto;
import com.example.dto.mapper.UserMapper;
import com.example.exception.EntityNotFoundException;
import com.example.exception.InactiveAccountException;
import com.example.model.entity.User;
import com.example.model.entity.UserPersonalInfoEntity;
import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserPersonalInfoService userPersonalInfoService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Transactional
    public AuthenticationResponseDto register(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);

        user = userRepository.save(user);
        userPersonalInfoService.createEmpty(user.getId());

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto authDto = userMapper.mapToAuth(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

    public AuthenticationResponseDto authenticate(User user) {
        var retrievedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));
        if (!retrievedUser.isEnabled()) {
            throw new InactiveAccountException("Аккаунт заблокирован администратором");
        }

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
