package com.example.model.entity;

import lombok.*;
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
@EqualsAndHashCode(exclude = {"createdDate", "lastModifiedDate"})
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

    public CommentEntity(CommentEntity entity){
        this.id = entity.id;
        this.authorId = entity.authorId;
        this.createdDate = entity.createdDate;
        this.text = entity.text;
        this.isAnonymous = entity.isAnonymous;
    }
}
