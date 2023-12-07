package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.ResourceContentEntity;
import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ResourceRepository;
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
    private final ResourceContentService contentService;
    private final SubjectService subjectService;
    private final UserService userService;

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
    public Resource createResource(Resource resource, List<ResourceContentEntity> contents) {
        if (!subjectService.isSubjectExists(resource.getSubjectId())) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        if (!userService.isUserExists(resource.getAuthorId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        resource.setCreatedDate(LocalDateTime.now());

        resource = resourceRepository.save(resource);
        contentService.createContent(contents, resource.getId());

        return resource;
    }

    public boolean isResourceExists(long id) {
        return resourceRepository.existsById(id);
    }

    public void delete(long id) {
        if (isResourceExists(id)) {
            resourceRepository.deleteById(id);
            return;
        }

        throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");
    }
}
