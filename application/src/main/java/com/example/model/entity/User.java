package com.example.model.entity;

import com.example.model.enumeration.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_jn")
public class User {

    @Id
    private Long id;

    private String email;

    private String password;

    private Role role;
}