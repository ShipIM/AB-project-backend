package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_jn")
public class CommentEntity {

    @Id
    private Long id;

    @Column(value = "author_id")
    private Long authorId;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    private String text;

    @Column(value = "is_anonymous")
    private boolean isAnonymous;
}
