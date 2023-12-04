package com.example.controller;

import com.example.constraint.ResourceTypeConstraint;
import com.example.dto.mapper.ContentMapper;
import com.example.dto.mapper.ResourceMapper;
import com.example.dto.page.request.PagingDto;
import com.example.dto.resource.request.CreateResourceRequestDto;
import com.example.dto.resource.response.ResourceResponseDto;
import com.example.model.entity.Content;
import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import com.example.service.ResourceService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "resources", description = "Контроллер для работы с ресурсами")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;
    private final ContentMapper contentMapper;
    private final UserService userService;

    @GetMapping("/resources/{id}")
    @Operation(description = "Получить существующий ресурс по идентификатору")
    public ResourceResponseDto getResource(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String id) {
        Resource res = resourceService.getResourceById(Long.parseLong(id));
        var responseResource = resourceMapper.mapToResourceDto(res);
        responseResource.setAuthor(userService.getById(responseResource.getAuthorId()).getLogin());

        return responseResource;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/resources")
    @Operation(description = "Создать новый ресурс")
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceResponseDto createResource(
            @RequestPart(value = "resource")
            @Valid
            CreateResourceRequestDto resource,
            @RequestPart(value = "files")
            List<MultipartFile> files) {
        Resource resourceEntity = resourceMapper.mapToResource(resource);
        List<Content> contents = contentMapper.mapToContentList(files);

        resourceEntity = resourceService.createResource(resourceEntity, contents);
        var resourceResponse = resourceMapper.mapToResourceDto(resourceEntity);
        resourceResponse.setAuthor(userService.getById(resourceResponse.getAuthorId()).getLogin());

        return resourceResponse;
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
            String type,
            @Valid PagingDto pagingDto) {
        return resourceService.getResourcesBySubjectAndResourceType(
                Long.parseLong(id),
                ResourceType.valueOf(type),
                pagingDto.formPageRequest()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/resources/{resourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить ресурс по id")
    public void deleteResource(@PathVariable
                               @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                       message = "Идентификатор комментария должен быть положительным числом типа long")
                               String resourceId) {
        resourceService.delete(Long.parseLong(resourceId));
    }
}
