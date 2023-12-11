package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.model.entity.User;
import com.example.service.JwtService;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JwtServiceTests extends BaseTestClass {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Test
    public void generateAdminJwtWithNoExtraClaims() {
        User user = userService.getById(1);

        String jwt = jwtService.generateToken(user);

        Assertions.assertAll("Должен содержать информацию, с помощью которой был сгенерирован",
                () -> Assertions.assertEquals(user.getUsername(), jwtUtils.extractEmail(jwt)),
                () -> Assertions.assertTrue(jwtUtils.isTokenValid(jwt, user))
        );
    }

    @Test
    public void generateAdminJwtWithExtraClaims() {
        Map<String, Object> claims = Map.of(
                "first", "first",
                "second", 2,
                "third", true
        );
        User user = userService.getById(1);

        String jwt = jwtService.generateToken(claims, user);
        Map<String, Object> retrievedClaims = jwtUtils.extractAllClaims(jwt);

        Assertions.assertAll("Должен содержать информацию, с помощью которой был сгенерирован",
                () -> Assertions.assertEquals(user.getUsername(), jwtUtils.extractEmail(jwt)),
                () -> Assertions.assertEquals(claims.get("first"), retrievedClaims.get("first")),
                () -> Assertions.assertEquals(claims.get("second"), retrievedClaims.get("second")),
                () -> Assertions.assertEquals(claims.get("third"), retrievedClaims.get("third")),
                () -> Assertions.assertTrue(jwtUtils.isTokenValid(jwt, user))
        );
    }
}
