package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.exception.InactiveAccountException;
import com.example.model.entity.User;
import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserPersonalInfoService userPersonalInfoService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User register(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);

        user = userRepository.save(user);
        userPersonalInfoService.createEmpty(user.getId());

        return user;
    }

    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        var retrievedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));
        if (!retrievedUser.isEnabled()) {
            throw new InactiveAccountException("Аккаунт заблокирован администратором");
        }

        return retrievedUser;
    }

    public User verify(String email) {
        var retrievedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"));

        return retrievedUser;
    }
}
