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
@Table(name = "feed_jn")
public class FeedNews {
    @Id
    private Long id;

    private String name;

    private String text;

    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "author_id")
    private Long authorId;
}
