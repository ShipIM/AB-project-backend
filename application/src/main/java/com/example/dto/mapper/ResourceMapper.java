package com.example.dto.mapper;

import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.model.entity.Resource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceResponseDto mapToResourceDto(Resource resource);

    Resource mapToResource(CreateResourceRequestDto dto);

    List<ResourceResponseDto> mapResourceListToDtoList(List<Resource> resources);

}
