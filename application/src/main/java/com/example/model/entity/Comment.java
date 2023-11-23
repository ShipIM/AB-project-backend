package com.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_jn")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @Generated
    private Long id;

    private String author;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resource resource;
}
