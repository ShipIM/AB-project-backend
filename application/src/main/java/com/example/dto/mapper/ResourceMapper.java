package com.example.dto.mapper;

import com.example.dto.mapper.decorator.ResourceMapperDecorator;
import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.model.entity.Resource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ContentMapper.class})
@DecoratedWith(ResourceMapperDecorator.class)
public interface ResourceMapper {

    ResourceResponseDto mapToResourceDto(Resource resource);

    @Mapping(ignore = true, target = "subject")
    Resource mapToResource(CreateResourceRequestDto dto);

    List<ResourceResponseDto> mapResourceListToDtoList(List<Resource> resources);

    @Named(value = "viewMapping")
    ResourceViewResponseDto mapToResourceViewDto(Resource resource);

    @IterableMapping(qualifiedByName = "viewMapping")
    List<ResourceViewResponseDto> mapResourceListToViewDtoList(List<Resource> resources);

}
