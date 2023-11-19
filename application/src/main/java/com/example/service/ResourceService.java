package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.entity.Resource;
import com.example.repository.ResourceRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public Resource getResourceById(long id) {
        return resourceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ресурса с таким идентификатором не существует"));
    }

    public List<Resource> getResources(Predicate predicate) {
        List<Resource> result = new ArrayList<>();
        resourceRepository.findAll(predicate).forEach(result::add);

        return result;
    }

    public Resource createResource(Resource resource) {
        resource.getContents().forEach(content -> content.setResource(resource));

        return resourceRepository.save(resource);
    }
}
