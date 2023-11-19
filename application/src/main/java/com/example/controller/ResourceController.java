package com.example.controller;

import com.example.dto.mapper.ResourceMapper;
import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.model.entity.QResource;
import com.example.model.entity.Resource;
import com.example.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "resources", description = "Контроллер для работы с ресурсами")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    @GetMapping("/resources/{resource}")
    @Operation(description = "Получить существующий ресурс по идентификатору")
    public ResourceResponseDto getResource(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resource) {
        Resource res = resourceService.getResourceById(Long.parseLong(resource));

        return resourceMapper.mapToResourceDto(res);
    }

    @PostMapping("/resources")
    @Operation(description = "Создать новый ресурс")
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceResponseDto createResource(
            @RequestBody
            @Valid
            CreateResourceRequestDto resourceDto) {
        Resource resource = resourceMapper.mapToResource(resourceDto);

        resource = resourceService.createResource(resource);

        return resourceMapper.mapToResourceDto(resource);
    }

    @GetMapping("/subjects/{subject}/resources")
    @Operation(description = "Получить все существующие ресурсы")
    public List<ResourceViewResponseDto> getResources(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String subject) {
        List<Resource> resources = resourceService.getResources(
                QResource.resource.subject.id.eq(Long.parseLong(subject))
        );

        return resourceMapper.mapResourceListToViewDtoList(resources);
    }
}
