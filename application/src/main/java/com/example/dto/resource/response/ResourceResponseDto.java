package com.example.dto.resource.response;

import com.example.model.enumeration.ResourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceResponseDto {

    private long id;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private String name;

    private String author;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("resource_type")
    private ResourceType resourceType;

}
