package com.example.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "_aud")
public class CommentAudit {

    @Id
    private Long id;

    @Column(value = "comment_id")
    private Long commentId;

    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    private String text;
}
