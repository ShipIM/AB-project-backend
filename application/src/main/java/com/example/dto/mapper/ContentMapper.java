package com.example.dto.mapper;

import com.example.dto.content.response.ContentResponseDto;
import com.example.model.entity.ContentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    ContentResponseDto mapToContentDto(ContentEntity content);

    @Mapping(target = "filename", expression = "java(file.getName())")
    ContentEntity mapToContentEntity(MultipartFile file) throws IOException;

    List<ContentResponseDto> mapToContentResponseDtoList(List<ContentEntity> contents);

    List<ContentEntity> mapToContentEntityList(List<MultipartFile> files);
}
