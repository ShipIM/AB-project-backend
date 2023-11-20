package com.example.dto.mapper.decorator;

import com.example.dto.mapper.ResourceMapper;
import com.example.dto.resource.CreateResourceRequestDto;
import com.example.dto.resource.ResourceResponseDto;
import com.example.dto.resource.ResourceViewResponseDto;
import com.example.model.entity.Resource;
import com.example.service.SubjectService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class ResourceMapperDecorator implements ResourceMapper {

    @Autowired
    @Qualifier("delegate")
    private ResourceMapper delegate;

    @Autowired
    private SubjectService subjectService;

    @Override
    public ResourceResponseDto mapToResourceDto(Resource resource) {
        return delegate.mapToResourceDto(resource);
    }

    @Override
    public Resource mapToResource(CreateResourceRequestDto dto) {
        Resource resource = delegate.mapToResource(dto);
        resource.setSubject(
                subjectService.getSubjectById(Long.parseLong(dto.getSubject()))
        );

        return resource;
    }

    @Override
    public List<ResourceResponseDto> mapResourceListToDtoList(List<Resource> resources) {
        return delegate.mapResourceListToDtoList(resources);
    }

    @Override
    public ResourceViewResponseDto mapToResourceViewDto(Resource resource) {
        return delegate.mapToResourceViewDto(resource);
    }

    @Override
    public List<ResourceViewResponseDto> mapResourceListToViewDtoList(List<Resource> resources) {
        return delegate.mapResourceListToViewDtoList(resources);
    }
}
