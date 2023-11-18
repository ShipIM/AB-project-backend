package com.example.dto.mapper;

import com.example.dto.content.ContentResponseDto;
import com.example.model.entity.Content;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    ContentResponseDto mapToContentDto(Content content);

    List<ContentResponseDto> mapContentListToDtoList(List<Content> contents);
}
