package com.example.dto.resource;

import com.example.dto.content.response.ContentResponseDto;
import com.example.model.enumeration.ResourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class ResourceResponseDto {

    private long id;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private String name;

    private String author;

    @JsonProperty("resource_type")
    private ResourceType resourceType;

    private List<ContentResponseDto> contents;

}
