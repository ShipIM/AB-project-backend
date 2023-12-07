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
@Table(name = "feed_news_comment")
public class FeedNewsComment {

    @Column(value = "comment_id")
    private Long commentId;

    @Column(value = "feed_news_id")
    private Long feedNewsId;

    @Column(value = "parent_id")
    private Long parentId;
}
