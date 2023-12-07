package com.example.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseComment {
    private Long id;

    private String author;

    @JsonProperty("author_id")
    private String authorId;

    private String text;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;
}
