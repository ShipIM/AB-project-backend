package com.example.dto.mapper;

import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.model.entity.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface ResourceMapper {

    ResourceResponseDto mapToResourceDto(Resource resource);

    @Mapping(target = "subject.id", source = "subjectId")
    Resource mapToResource(CreateResourceRequestDto dto);

    List<ResourceResponseDto> mapResourceListToDtoList(List<Resource> resources);

    ResourceViewResponseDto mapToResourceViewDto(Resource resource);

    List<ResourceViewResponseDto> mapResourceListToViewDtoList(List<Resource> resources);

}
