package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import com.example.repository.UserPersonInfoRepository;
import com.example.repository.UserRepository;
import com.example.service.AuthenticationService;
import com.example.service.UserPersonalInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationServiceTests extends BaseTestClass {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPersonalInfoService userPersonalInfoService;

    @Autowired
    private UserPersonInfoRepository userPersonInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerCreatesNewUserAndUserInfo() {
        String password = "12345678";

        User defaultUser = new User(
                null,
                "user@gmail.com",
                password,
                "user",
                null,
                null
        );

        User user = authenticationService.register(defaultUser);

        Assertions.assertAll("Создаёт пользователя с правильными данными и информацию о пользователе",
                () -> Assertions.assertTrue(userRepository.existsById(user.getId())),
                () -> Assertions.assertEquals(defaultUser.getEmail(), user.getEmail()),
                () -> Assertions.assertEquals(defaultUser.getLogin(), user.getLogin()),
                () -> Assertions.assertTrue(passwordEncoder.matches(password, user.getPassword())),
                () -> Assertions.assertEquals(Role.USER, user.getRole()),
                () -> Assertions.assertEquals(Status.ACTIVE, user.getStatus()),
                () -> Assertions.assertTrue(userPersonalInfoService.isUserExist(user.getId()))
        );

        cleanup(user.getId());
    }

    @Test
    public void authenticateExistingUser() {
        String password = "12345678";
        User user = createUser(password, Status.ACTIVE);
        user.setPassword(password);

        User retrievedUser = authenticationService.authenticate(user);

        Assertions.assertEquals(user, retrievedUser);

        cleanup(user.getId());
    }

    @Test
    public void authenticateNonExistingUser() {
        User user = new User(
                null,
                "user@gmail.com",
                "password",
                "user",
                null,
                null
        );

        Assertions.assertThrows(InternalAuthenticationServiceException.class,
                () -> authenticationService.authenticate(user));
    }

    @Test
    public void authenticateInactiveUser() {
        String password = "12345678";
        User user = createUser(password, Status.INACTIVE);
        user.setPassword(password);

        Assertions.assertThrows(DisabledException.class,
                () -> authenticationService.authenticate(user));

        cleanup(user.getId());
    }

    @Test
    public void verifyOnExistingUser() {
        User user = createUser("12345678", Status.ACTIVE);

        User retrievedUser = authenticationService.verify(user.getEmail());

        Assertions.assertEquals(user, retrievedUser);

        cleanup(user.getId());
    }

    @Test
    public void verifyOnNonExistingUser() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> authenticationService.verify("email"));
    }

    private User createUser(String password, Status status) {
        User user = new User(
                null,
                "user@gmail.com",
                passwordEncoder.encode(password),
                "user",
                Role.USER,
                status
        );

        return userRepository.save(user);
    }

    private void cleanup(long id) {
        userRepository.deleteById(id);
        userPersonInfoRepository.deleteById(id);
    }

}
