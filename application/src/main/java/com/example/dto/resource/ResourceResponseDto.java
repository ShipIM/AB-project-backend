package com.example.dto.resource;

import com.example.dto.content.ContentResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ResourceResponseDto extends ResourceViewResponseDto{

    private List<ContentResponseDto> contents;

}
