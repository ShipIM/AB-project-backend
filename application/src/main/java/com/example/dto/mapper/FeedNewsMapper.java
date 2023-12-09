package com.example.dto.mapper;

import com.example.dto.feedNews.request.FeedNewsCreateDto;
import com.example.dto.feedNews.response.FeedNewsResponseDto;
import com.example.model.entity.FeedNewsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedNewsMapper {
    FeedNewsResponseDto mapToResponseDto(FeedNewsEntity feedNews);

    FeedNewsEntity mapToEntity(FeedNewsCreateDto createDto);
}
