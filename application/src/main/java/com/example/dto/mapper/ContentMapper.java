package com.example.dto.mapper;

import com.example.dto.content.response.FeedContentResponseDto;
import com.example.dto.content.response.ResourceContentResponseDto;
import com.example.model.entity.FeedNewsContent;
import com.example.model.entity.ResourceContentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    ResourceContentResponseDto mapToContentDto(ResourceContentEntity content);

    @Mapping(target = "filename", expression = "java(file.getName())")
    ResourceContentEntity mapToResourceContent(MultipartFile file) throws IOException;

    List<ResourceContentResponseDto> mapToResourceContentDtoList(List<ResourceContentEntity> contents);

    List<ResourceContentEntity> mapToResourceContentList(List<MultipartFile> files);


    FeedContentResponseDto mapToContentDto(FeedNewsContent content);

    @Mapping(target = "filename", expression = "java(file.getName())")
    FeedNewsContent mapToFeedNewsContent(MultipartFile file) throws IOException;

    List<FeedContentResponseDto> mapToFeedNewsContentDtoList(List<FeedNewsContent> contents);

    List<FeedNewsContent> mapToFeedNewsContentList(List<MultipartFile> files);
}
