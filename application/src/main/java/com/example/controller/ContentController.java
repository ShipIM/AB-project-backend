package com.example.controller;

import com.example.dto.content.response.FeedContentResponseDto;
import com.example.dto.content.response.ResourceContentResponseDto;
import com.example.dto.mapper.ContentMapper;
import com.example.model.entity.FeedNewsContent;
import com.example.model.entity.ResourceContentEntity;
import com.example.service.FeedNewsContentService;
import com.example.service.ResourceContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "contents", description = "Контроллер для работы с файлами")
@RequiredArgsConstructor
@Validated
public class ContentController {
    private final ResourceContentService resourceContentService;
    private final FeedNewsContentService feedNewsContentService;
    private final ContentMapper contentMapper;

    @GetMapping("/resources/{resourceId}/contents")
    @Operation(description = "Получить содержимое ресурса по id")
    public List<ResourceContentResponseDto> getResourceContents(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resourceId) {
        List<ResourceContentEntity> contents = resourceContentService.getContentsByResource(Long.parseLong(resourceId));

        return contentMapper.mapToResourceContentDtoList(contents);
    }

    @GetMapping("/feed/{feedNewsId}/contents")
    @Operation(description = "Получить содержимое новости по id")
    public List<FeedContentResponseDto> getFeedNewsContents(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор новости должен быть положительным числом типа long")
            String feedNewsId) {
        List<FeedNewsContent> contents = feedNewsContentService.getContentsByFeedNewsId(Long.parseLong(feedNewsId));

        return contentMapper.mapToFeedNewsContentDtoList(contents);
    }
}
