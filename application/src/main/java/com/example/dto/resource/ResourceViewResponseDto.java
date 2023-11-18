package com.example.dto.resource;

import com.example.model.enumeration.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceViewResponseDto {

    private long id;
    private LocalDateTime createdDate;
    private String name;
    private String author;
    private ResourceType resourceType;

}
