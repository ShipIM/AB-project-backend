package com.example.controller;

import com.example.dto.mapper.ResourceMapper;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "resources", description = "Controller for working with resources")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private ResourceService resourceService;
    private ResourceMapper resourceMapper;

    @GetMapping("/resources/{resource}")
    public ResourceResponseDto getResource(@PathVariable String resource) {

    }

    @PostMapping("/resources")
    public ResourceResponseDto createResource() {

    }

    @GetMapping("/subjects/{subject}/resources")
    public ResourceViewResponseDto getResources(@PathVariable String subject) {

    }
}
