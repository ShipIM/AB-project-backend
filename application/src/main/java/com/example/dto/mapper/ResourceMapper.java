package com.example.dto.mapper;

import com.example.dto.resource.request.CreateResourceRequestDto;
import com.example.dto.resource.response.ResourceResponseDto;
import com.example.model.entity.ResourceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceResponseDto mapToResourceDto(ResourceEntity resource);

    ResourceEntity mapToResource(CreateResourceRequestDto dto);

    List<ResourceResponseDto> mapResourceListToDtoList(List<ResourceEntity> resources);

}
