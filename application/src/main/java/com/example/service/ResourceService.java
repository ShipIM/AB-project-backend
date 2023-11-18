package com.example.service;

import com.example.model.entity.Resource;
import com.example.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    public Resource getResourceById(long id) {
        return resourceRepository.getReferenceById(id);
    }

    public Resource createResource(Resource resource) {
        resource.getContents().forEach(content -> content.setResource(resource));

        return resourceRepository.save(resource);
    }
}
