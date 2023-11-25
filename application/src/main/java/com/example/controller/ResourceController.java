package com.example.controller;

import com.example.constraint.ResourceTypeConstraint;
import com.example.dto.mapper.ResourceMapper;
import com.example.dto.page.request.PagingDto;
import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import com.example.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "resources", description = "Контроллер для работы с ресурсами")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    @GetMapping("/resources/{id}")
    @Operation(description = "Получить существующий ресурс по идентификатору")
    public ResourceResponseDto getResource(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String id) {
        Resource res = resourceService.getResourceById(Long.parseLong(id));

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

    @GetMapping("/subjects/{id}/resources")
    @Operation(description = "Получить все существующие ресурсы")
    public Page<ResourceResponseDto> getResources(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String id,
            @RequestParam
            @ResourceTypeConstraint(message = "Неизвестный тип ресурса")
            @NotBlank(message = "Необходимо указать тип ресурса")
            String resourceType,
            PagingDto pagingDto) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(pagingDto.getPageNumber()),
                Integer.parseInt(pagingDto.getPageSize())
        );

        Page<Resource> resources = resourceService.getResourcesBySubjectAndResourceType(
                Long.parseLong(id),
                ResourceType.valueOf(resourceType),
                pageRequest
        );

        return resources.map(resourceMapper::mapToResourceDto);
    }
}
