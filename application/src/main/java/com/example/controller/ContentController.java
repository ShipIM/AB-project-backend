package com.example.controller;

import com.example.dto.content.ContentResponseDto;
import com.example.dto.mapper.ContentMapper;
import com.example.model.entity.Content;
import com.example.model.entity.QContent;
import com.example.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "contents", description = "Контроллер для работы с файлами и содержимым ресурсов")
@RequiredArgsConstructor
@Validated
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;

    @GetMapping("/resources/{id}/contents")
    @Operation(description = "Получить содержимое ресурса по id")
    public List<ContentResponseDto> getResourceContents(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String id) {
        List<Content> contents = contentService.getContent(
                QContent.content.resource.id.eq(Long.parseLong(id))
        );

        return contentMapper.mapContentListToDtoList(contents);
    }

    @PostMapping("/resources/{id}/contents")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание файлов")
    public List<ContentResponseDto> createContent(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String id,
            @RequestParam("files")
            MultipartFile[] files) throws IOException {
        List<Content> contents = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            Content content = contentMapper.mapToContent(multipartFile);
            contents.add(content);
        }

        contents = contentService.createContent(contents, Long.parseLong(id));

        return contentMapper.mapContentListToDtoList(contents);
    }
}
