package com.example.dto.mapper;

import com.example.dto.feedNews.request.FeedNewsCreateDto;
import com.example.dto.feedNews.response.FeedNewsResponseDto;
import com.example.model.entity.FeedNews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedNewsMapper {
    FeedNewsResponseDto mapToResponseDto(FeedNews feedNews);

    FeedNews mapToEntity(FeedNewsCreateDto createDto);
}
