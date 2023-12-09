package com.example.dto.feedNews.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedNewsResponseDto {
    private long id;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private String name;

    private String author;

    @JsonProperty("author_id")
    private Long authorId;

    private String text;
}
