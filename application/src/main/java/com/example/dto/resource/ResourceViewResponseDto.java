package com.example.dto.resource;

import com.example.model.enumeration.ResourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceViewResponseDto {

    private long id;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private String name;

    private String author;

    @JsonProperty("resource_type")
    private ResourceType resourceType;

}
