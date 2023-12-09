package com.example.dto.commentaudit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentAuditResponseDto {

    @JsonProperty("last_modified_date")
    private LocalDateTime lastModifiedDate;

    private String text;

}
