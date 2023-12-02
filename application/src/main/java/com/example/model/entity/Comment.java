package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_jn")
public class Comment {

    @Id
    private Long id;

    private String author;

    @Column(value = "created_date")
    private LocalDateTime createdDate;

    private String text;

    @Column(value = "resource_id")
    private Long resourceId;

    @Column(value = "is_anonymous")
    private Long isAnonymous;
}
