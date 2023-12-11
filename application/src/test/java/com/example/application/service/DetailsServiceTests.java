package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.enumeration.Role;
import com.example.service.DetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class DetailsServiceTests extends BaseTestClass {

    @Autowired
    private DetailsService detailsService;

    @Test
    public void loadAdminDetails() {
        final String username = "admin@gmail.com";
        UserDetails retrievedDetails = detailsService.loadUserByUsername(username);

        Assertions.assertAll("Должен вернуть сохранённые данные",
                () -> Assertions.assertEquals(retrievedDetails.getUsername(), username),
                () -> Assertions.assertEquals(retrievedDetails.getAuthorities(), List.of(Role.ADMIN)),
                () -> Assertions.assertTrue(retrievedDetails.isEnabled()),
                () -> Assertions.assertTrue(retrievedDetails.isAccountNonExpired()),
                () -> Assertions.assertTrue(retrievedDetails.isCredentialsNonExpired()),
                () -> Assertions.assertTrue(retrievedDetails.isAccountNonLocked())
        );
    }

    @Test
    public void loadNonExistingAccountDetails() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> detailsService.loadUserByUsername("email"));
    }
}
