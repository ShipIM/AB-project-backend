package com.example.dto.feedNews.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedNewsCreateDto {
    private long id;

    private String name;

    @JsonProperty("author_id")
    private Long authorId;

    private String text;
}
