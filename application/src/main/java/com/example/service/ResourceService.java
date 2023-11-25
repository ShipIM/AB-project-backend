package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Resource;
import com.example.model.entity.Subject;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final SubjectService subjectService;

    public Page<Resource> getResourcesBySubjectAndResourceType(long subjectId,
                                                               ResourceType resourceType,
                                                               PageRequest pageRequest) {
        Sort sort = Sort.by(Sort.Order.desc("name"));

        return resourceRepository.findAllBySubjectIdAndResourceType(
                subjectId,
                resourceType,
                pageRequest.withSort(sort)
        );
    }

    public Resource getResourceById(long id) {
        return resourceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ресурса с таким идентификатором не существует"));
    }

    public Resource createResource(Resource resource) {
        Subject subject = subjectService.getSubjectById(resource.getSubjectId());

        resource.setCreatedDate(LocalDateTime.now());

        return resourceRepository.save(resource);
    }
}
