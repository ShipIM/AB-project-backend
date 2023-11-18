package com.example.dto.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentResponseDto {

    private String filename;
    private byte[] bytes;
    private String contentType;
    private long size;

}
