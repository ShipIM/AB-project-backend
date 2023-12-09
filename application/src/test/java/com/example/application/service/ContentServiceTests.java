package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.ContentEntity;
import com.example.model.entity.FeedNewsEntity;
import com.example.model.entity.ResourceEntity;
import com.example.model.entity.Subject;
import com.example.model.enumeration.ResourceType;
import com.example.repository.ContentRepository;
import com.example.repository.FeedNewsRepository;
import com.example.repository.ResourceRepository;
import com.example.repository.SubjectRepository;
import com.example.service.ContentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ContentServiceTests extends BaseTestClass {
    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private FeedNewsRepository feedNewsRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    private static long resourceId;

    private static long feedNewsId;

    private ContentEntity defaultContent;

    private ArrayList<ContentEntity> defaultContentList = new ArrayList<>();


    void setup() {
        var defaultSubject = new Subject();
        defaultSubject.setCourseId(1L);
        defaultSubject.setName("name");

        long subjectId = subjectRepository.save(defaultSubject).getId();

        var defaultResource = new ResourceEntity();
        defaultResource.setResourceType(ResourceType.TEST);
        defaultResource.setName("name");
        defaultResource.setSubjectId(subjectId);
        defaultResource.setAuthorId(1L);
        defaultResource.setCreatedDate(LocalDateTime.now());

        resourceId = resourceRepository.save(defaultResource).getId();

        var defaultFeedNews = new FeedNewsEntity();
        defaultFeedNews.setText("text");
        defaultFeedNews.setName("name");
        defaultFeedNews.setAuthorId(1L);
        defaultFeedNews.setCreatedDate(LocalDateTime.now());

        feedNewsId = feedNewsRepository.save(defaultFeedNews).getId();
    }

    void cleanup() {
        subjectRepository.deleteAll();
        resourceRepository.deleteAll();
        feedNewsRepository.deleteAll();
    }

    @BeforeEach
    @AfterEach
    void init() {
        cleanup();
        defaultContentList.clear();
        contentRepository.deleteAll();
        contentRepository.deleteAllFeedContent();
        contentRepository.deleteAllResourceContent();

        defaultContent = new ContentEntity();
        defaultContent.setContentType("type");
        defaultContent.setFilename("name");
        defaultContent.setSize(5);
        defaultContent.setBytes(new byte[]{1, 2, 3, 4, 5});
        defaultContentList.add(new ContentEntity(defaultContent));
        defaultContent.setFilename("name2");
        defaultContentList.add(new ContentEntity(defaultContent));
        defaultContent.setFilename("name3");
        defaultContentList.add(new ContentEntity(defaultContent));

        setup();
    }

    @Test
    public void createContentListTest() {
        var resultList = contentService.createContentList(defaultContentList);

        for (int i = 0; i < defaultContentList.size(); i++) {
            Assertions.assertEquals(defaultContentList.get(i), resultList.get(i));
        }
    }

    @Test
    public void createContentListExceptionTest() {
        defaultContent.setId(-1L);
        defaultContentList.add(defaultContent);
        Assertions.assertThrows(DbActionExecutionException.class, () -> contentService.createContentList(defaultContentList));
        Assertions.assertEquals(0, contentRepository.count());
    }

    @Test
    public void createResourceContentListSuccessTest() {
        var resultList = contentService.createResourceContent(defaultContentList, resourceId);

        for (int i = 0; i < defaultContentList.size(); i++) {
            Assertions.assertEquals(defaultContentList.get(i), resultList.get(i));
        }

        Assertions.assertEquals(defaultContentList.size(), contentRepository.count());
        Assertions.assertEquals(defaultContentList.size(), contentRepository.countResourceContent());
        Assertions.assertEquals(0, contentRepository.countFeedNewsContent());
    }

    @Test
    public void createResourceContentListExceptionTest() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> contentService.createResourceContent(defaultContentList, 1L));

        Assertions.assertEquals(0, contentRepository.count());
        Assertions.assertEquals(0, contentRepository.countResourceContent());
        Assertions.assertEquals(0, contentRepository.countFeedNewsContent());
    }

    @Test
    public void createFeedContentListSuccessTest() {
        var resultList = contentService.createFeedNewsContent(defaultContentList, feedNewsId);

        for (int i = 0; i < defaultContentList.size(); i++) {
            Assertions.assertEquals(defaultContentList.get(i), resultList.get(i));
        }

        Assertions.assertEquals(defaultContentList.size(), contentRepository.count());
        Assertions.assertEquals(0, contentRepository.countResourceContent());
        Assertions.assertEquals(defaultContentList.size(), contentRepository.countFeedNewsContent());
    }

    @Test
    public void createFeedContentListExceptionTest() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> contentService.createFeedNewsContent(defaultContentList, 1L));

        Assertions.assertEquals(0, contentRepository.count());
        Assertions.assertEquals(0, contentRepository.countResourceContent());
        Assertions.assertEquals(0, contentRepository.countFeedNewsContent());
    }

    @Test
    public void getByResourceExitIdWithCorrectSortTest() {
        contentService.createResourceContent(defaultContentList, resourceId);

        var resultList = contentService.getContentsByResource(resourceId);

        for (int i = 0; i < defaultContentList.size(); i++) {
            Assertions.assertEquals(defaultContentList.get(i), resultList.get(i));
        }
    }

    @Test
    public void getByResourceNotExitIdTest() {
        Assertions.assertEquals(0, contentService.getContentsByResource(0L).size());
    }

    @Test
    public void getByFeedNewsExitIdWithCorrectSortTest() {
        contentService.createFeedNewsContent(defaultContentList, feedNewsId);

        var resultList = contentService.getContentsByFeedNewsId(feedNewsId);

        for (int i = 0; i < defaultContentList.size(); i++) {
            Assertions.assertEquals(defaultContentList.get(i), resultList.get(i));
        }
    }

    @Test
    public void getByFeedNewsNotExitIdTest() {
        Assertions.assertEquals(0,  contentService.getContentsByFeedNewsId(0L).size());
    }
}
