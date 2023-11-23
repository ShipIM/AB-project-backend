package com.example.dto.content.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentResponseDto {

    private String filename;

    private byte[] bytes;

    @JsonProperty("content_type")
    private String contentType;

    private long size;

}
