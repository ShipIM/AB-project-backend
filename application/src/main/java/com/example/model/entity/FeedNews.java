package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_news_jn")
public class FeedNews {
    @Id
    private Long id;

    private String name;

    private String text;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "author_id")
    private Long authorId;
}
