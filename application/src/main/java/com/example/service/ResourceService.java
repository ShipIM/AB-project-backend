package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Content;
import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ResourceRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final ContentService contentService;
    private final SubjectService subjectService;
    private final UserRepository userRepository;

    public Page<Resource> getResourcesBySubjectAndResourceType(long subjectId,
                                                               ResourceType resourceType,
                                                               Pageable pageable) {
        String sort = "name";
        long total = resourceRepository.countAllBySubjectIdAndResourceType(subjectId, resourceType);
        List<Resource> resources = resourceRepository.findAllBySubjectIdAndResourceType(
                subjectId,
                resourceType,
                sort,
                pageable.getPageSize(),
                pageable.getPageNumber()
        );

        return new PageImpl<>(resources, pageable, total);
    }

    public Resource getResourceById(long id) {
        return resourceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ресурса с таким идентификатором не существует"));
    }

    @Transactional
    public Resource createResource(Resource resource, List<Content> contents) {
        if (!subjectService.isSubjectExists(resource.getSubjectId())) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        resource.setAuthor(userRepository.findByEmail(resource.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"))
                .getUsername());
        resource.setCreatedDate(LocalDateTime.now());

        resource = resourceRepository.save(resource);
        contentService.createContent(contents, resource.getId());

        return resource;
    }

    public boolean isResourceExists(long id) {
        return resourceRepository.existsById(id);
    }
}
