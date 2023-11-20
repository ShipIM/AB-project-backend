package com.example.dto.mapper;

import com.example.dto.content.request.CreateContentRequestDto;
import com.example.dto.content.response.ContentResponseDto;
import com.example.model.entity.Content;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    ContentResponseDto mapToContentDto(Content content);

    Content mapToContent(CreateContentRequestDto dto);

    List<ContentResponseDto> mapContentListToDtoList(List<Content> contents);
}
