package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.ResourceEntity;
import com.example.model.entity.Subject;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ResourceRepository;
import com.example.repository.SubjectRepository;
import com.example.service.ResourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ResourceServiceTests extends BaseTestClass {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @ParameterizedTest
    @CsvSource({
            "0, 1, SUMMARY",
            "0, 2, EXAM",
            "1, 2, TEST"
    })
    public void getResourcesBySubjectAndResourceTypeReturnsCorrectPageAndContents(int pageNumber, int pageSize,
                                                                       ResourceType type) {
        long subject = createSubject();
        List<ResourceEntity> savedResources = createResourceList(subject);
        List<ResourceEntity> filteredResources = savedResources.stream()
                .filter(resource -> resource.getResourceType().equals(type))
                .sorted(Comparator.comparing(ResourceEntity::getName))
                .toList();

        int pageStart = pageNumber * pageSize, pageEnd = pageStart + pageSize;

        Page<ResourceEntity> retrievedPage = resourceService.getResourcesBySubjectAndResourceType(
                subject,
                type,
                PageRequest.of(pageNumber, pageSize)
        );

        Assertions.assertAll("Должен возвращать правильную страницу аудитов",
                () -> Assertions.assertEquals(
                        IntStream.range(0, filteredResources.size())
                                .filter(i -> i >= pageStart && i < pageEnd && i < filteredResources.size())
                                .mapToObj(filteredResources::get)
                                .toList().size(),
                        retrievedPage.getContent().size()
                ),
                () -> Assertions.assertEquals(
                        Math.ceil((float) filteredResources.size() / pageSize),
                        retrievedPage.getTotalPages()
                ),
                () -> Assertions.assertEquals(
                        filteredResources.size(),
                        retrievedPage.getTotalElements()
                ),
                () -> Assertions.assertEquals(
                        IntStream.range(0, filteredResources.size())
                                .filter(i -> i >= pageStart && i < pageEnd && i < filteredResources.size())
                                .mapToObj(filteredResources::get)
                                .toList(),
                        retrievedPage.getContent())
        );
    }

    @Test
    public void createResource() {
        ResourceEntity resource = createResource(1L, createSubject());

        ResourceEntity retrievedResource = resourceService.createResource(resource, new ArrayList<>());

        Assertions.assertAll("Должен создать корректный ресурс",
                () -> Assertions.assertTrue(resourceRepository.existsById(retrievedResource.getId())),
                () -> Assertions.assertEquals(resource, retrievedResource)
        );
    }

    @Test
    public void createResourceWithNonExistingSubject() {
        ResourceEntity resource = createResource(1L, -1);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> resourceService.createResource(resource, new ArrayList<>()));
    }

    @Test
    public void createResourceWithNonExistingAuthor() {
        ResourceEntity resource = createResource(-1, createSubject());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> resourceService.createResource(resource, new ArrayList<>()));
    }

    @Test
    public void deleteExistingRepository() {
        ResourceEntity resource = createResource(1L, createSubject());
        resource = resourceRepository.save(resource);

        resourceService.delete(resource.getId());

        Assertions.assertFalse(resourceRepository.existsById(resource.getId()));
    }

    @Test
    public void deleteNonExistingRepository() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> resourceService.delete(-1));
    }

    @Test
    public void getExistingResourceById() {
        ResourceEntity resource = createResource(1L, createSubject());
        resource = resourceRepository.save(resource);

        ResourceEntity retrievedResource = resourceService.getResourceById(resource.getId());

        Assertions.assertEquals(resource, retrievedResource);
    }

    @Test
    public void getNonExistingResourceById() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> resourceService.getResourceById(-1));
    }

    @Test
    public void isResourceExists() {
        ResourceEntity resource = createResource(1L, createSubject());
        resource = resourceRepository.save(resource);

        Assertions.assertTrue(resourceService.isResourceExists(resource.getId()));
    }

    @Test
    public void isResourceExistsOnNonExistingResource() {
        Assertions.assertFalse(resourceService.isResourceExists(-1));
    }

    private List<ResourceEntity> createResourceList(long subject) {
        List<ResourceEntity> resources = List.of(
                new ResourceEntity(null, "first", null, 1L, ResourceType.TEST, subject),
                new ResourceEntity(null, "second", null, 1L, ResourceType.EXAM, subject),
                new ResourceEntity(null, "third", null, 1L, ResourceType.EXAM, subject),
                new ResourceEntity(null, "fourth", null, 1L, ResourceType.TEST, subject),
                new ResourceEntity(null, "fifth", null, 1L, ResourceType.TEST, subject),
                new ResourceEntity(null, "sixth", null, 1L, ResourceType.SUMMARY, subject)
        );

        Iterable<ResourceEntity> iterableResources = resourceRepository.saveAll(resources);
        List<ResourceEntity> savedResources = new ArrayList<>();
        iterableResources.forEach(savedResources::add);

        return savedResources;
    }

    private ResourceEntity createResource(long user, long subject) {
        return new ResourceEntity(
                null,
                "resource",
                null,
                user,
                ResourceType.TEST,
                subject
        );
    }

    private long createSubject() {
        Subject subject = new Subject(null, "subject", 1L);

        return subjectRepository.save(subject).getId();
    }

    @AfterEach
    private void cleanup() {
        resourceRepository.deleteAll();
        subjectRepository.deleteAll();
    }
}
