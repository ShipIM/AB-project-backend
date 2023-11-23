package com.example.dto.mapper;

import com.example.dto.content.response.ContentResponseDto;
import com.example.model.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    ContentResponseDto mapToContentDto(Content content);

    @Mapping(target = "filename", expression = "java(file.getName())")
    Content mapToContent(MultipartFile file) throws IOException;

    List<ContentResponseDto> mapContentListToDtoList(List<Content> contents);
}
