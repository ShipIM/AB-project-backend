package com.example.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "createdDate")
@Table(name = "feed_news_jn")
public class FeedNewsEntity {
    @Id
    private Long id;

    private String name;

    private String text;

    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "author_id")
    private Long authorId;

    public FeedNewsEntity(FeedNewsEntity feedNews)
    {
        this.id = feedNews.id;
        this.name = feedNews.name;
        this.text = feedNews.text;
        this.createdDate = feedNews.createdDate;
        this.authorId = feedNews.authorId;
    }
}
