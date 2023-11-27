package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ResourceRepository;
import com.example.repository.UserRepository;
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
    private final UserRepository userRepository;

    public Page<Resource> getResourcesBySubjectAndResourceType(long subjectId,
                                                               ResourceType resourceType,
                                                               PageRequest pageRequest) {
        Sort sort = Sort.by(Sort.Order.asc("name"));

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
        if (!subjectService.isSubjectExists(resource.getSubjectId()))
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");

        resource.setAuthor(userRepository.findByEmail(resource.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"))
                .getUsername());
        resource.setCreatedDate(LocalDateTime.now());

        return resourceRepository.save(resource);
    }

    public boolean isResourceExists(long id) {
        return resourceRepository.existsById(id);
    }
}
