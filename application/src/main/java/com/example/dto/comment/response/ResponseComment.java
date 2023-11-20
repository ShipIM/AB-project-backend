package com.example.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Long ResourceId;
}
