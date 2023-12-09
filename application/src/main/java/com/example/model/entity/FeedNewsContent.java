package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_news_content")
public class FeedNewsContent {

    @Column(value = "content_id")
    private Long contentId;

    @Column(value = "feed_news_id")
    private Long feedNewsId;

}
