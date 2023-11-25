package com.example.model.enumeration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
