package com.example.controller;

import com.example.dto.content.response.ContentResponseDto;
import com.example.dto.mapper.ContentMapper;
import com.example.model.entity.ContentEntity;
import com.example.service.ContentService;
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
    private final ContentService contentService;
    private final ContentMapper contentMapper;

    @GetMapping("/resources/{resourceId}/contents")
    @Operation(description = "Получить содержимое ресурса по id")
    public List<ContentResponseDto> getResourceContents(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resourceId) {
        List<ContentEntity> contents = contentService.getContentsByResource(Long.parseLong(resourceId));

        return contentMapper.mapToContentResponseDtoList(contents);
    }

    @GetMapping("/feed/{feedNewsId}/contents")
    @Operation(description = "Получить содержимое новости по id")
    public List<ContentResponseDto> getFeedNewsContents(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор новости должен быть положительным числом типа long")
            String feedNewsId) {
        List<ContentEntity> contents = contentService.getContentsByFeedNewsId(Long.parseLong(feedNewsId));

        return contentMapper.mapToContentResponseDtoList(contents);
    }
}
