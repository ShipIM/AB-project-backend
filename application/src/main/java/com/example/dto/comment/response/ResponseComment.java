package com.example.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseComment {
    private String author;

    private String text;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @Pattern( )
    @JsonProperty("is_anonymous")
    private String isAnonymous;
}
