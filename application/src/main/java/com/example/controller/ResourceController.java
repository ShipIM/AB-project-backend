package com.example.controller;

import com.example.dto.mapper.ResourceMapper;
import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.model.entity.QResource;
import com.example.model.entity.Resource;
import com.example.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "resources", description = "Controller for working with resources")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    @GetMapping("/resources/{resource}")
    public ResourceResponseDto getResource(
            @PathVariable
            @Pattern(regexp = "^\\d{1,19}$", message = "Resource id must be long positive number")
            String resource) {
        Resource res = resourceService.getResourceById(Long.parseLong(resource));

        return resourceMapper.mapToResourceDto(res);
    }

    @PostMapping("/resources")
    public ResourceResponseDto createResource(
            @RequestBody
            @Valid
            CreateResourceRequestDto resourceDto) {
        Resource resource = resourceMapper.mapToResource(resourceDto);

        resource = resourceService.createResource(resource);

        return resourceMapper.mapToResourceDto(resource);
    }

    @GetMapping("/subjects/{subject}/resources")
    public List<ResourceViewResponseDto> getResources(
            @PathVariable
            @Pattern(regexp = "^\\d{1,19}$", message = "Subject id must be long positive number")
            String subject) {
        List<Resource> resources = resourceService.getResources(
                QResource.resource.subject.id.eq(Long.parseLong(subject))
        );

        return resourceMapper.mapResourceListToViewDtoList(resources);
    }
}
