package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.ContentEntity;
import com.example.model.entity.FeedNewsEntity;
import com.example.repository.FeedNewsRepository;
import com.example.service.FeedNewsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import java.util.ArrayList;

public class FeedNewsServiceTests extends BaseTestClass {
    @Autowired
    private FeedNewsService feedNewsService;

    @Autowired
    private FeedNewsRepository feedNewsRepository;

    private FeedNewsEntity defaultFeedNews;

    private ArrayList<ContentEntity> emptyContent = new ArrayList<>();

    @BeforeEach
    @AfterEach
    void init() {
        feedNewsRepository.deleteAll();

        defaultFeedNews = new FeedNewsEntity();
        defaultFeedNews.setName("name");
        defaultFeedNews.setText("text");
        defaultFeedNews.setAuthorId(1L);
    }

    @Test
    public void createOnlyOneFeedNewsTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);

        var feedNewsCount = feedNewsRepository.count();

        Assertions.assertEquals(1, feedNewsCount);
    }

    @Test
    public void createCorrectFeedNewsTest() {
        var createFeedNews = feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var repositoryFeedNews = feedNewsService.getById(createFeedNews.getId());

        defaultFeedNews.setId(createFeedNews.getId());

        Assertions.assertEquals(defaultFeedNews, repositoryFeedNews);
        Assertions.assertEquals(defaultFeedNews, repositoryFeedNews);
    }

    @Test
    public void createSetCreateDateTest() {
        var createFeedNews = feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var repositoryFeedNews = feedNewsService.getById(createFeedNews.getId());

        defaultFeedNews.setId(createFeedNews.getId());

        Assertions.assertNotNull(repositoryFeedNews.getCreatedDate());
    }

    @Test
    public void createIncorrectAuthorIdTest() {
        defaultFeedNews.setAuthorId(-1L);
        Assertions.assertThrows(EntityNotFoundException.class, () -> feedNewsService.createOrUpdate(defaultFeedNews, emptyContent));
    }

    @Test
    public void createWithBagInContent_DropTransactionTest() {
        var wrongContent = new ArrayList<ContentEntity>();
        wrongContent.add(new ContentEntity(-1L, "name", new byte[]{}, "type", 0L));
        Assertions.assertThrows(DbActionExecutionException.class, () -> feedNewsService.createOrUpdate(defaultFeedNews, wrongContent));
        Assertions.assertEquals(0, feedNewsRepository.count());
    }

    @Test
    public void updateCorrectTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var newName = defaultFeedNews.getName() + "diff";
        defaultFeedNews.setName(newName);

        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);

        Assertions.assertEquals(newName, feedNewsService.getById(defaultFeedNews.getId()).getName());
    }

    @Test
    public void uniqConstraintCloneCreateFailTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        defaultFeedNews.setId(null);
        Assertions.assertThrows(DbActionExecutionException.class, () -> feedNewsService.createOrUpdate(defaultFeedNews, emptyContent));
    }

    @Test
    public void uniqCreateTestDifferentNamesTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);

        var diffNameFeedNews = new FeedNewsEntity(defaultFeedNews);
        diffNameFeedNews.setId(null);
        diffNameFeedNews.setName(diffNameFeedNews.getName() + "2");

        Assertions.assertDoesNotThrow(() -> feedNewsService.createOrUpdate(defaultFeedNews, emptyContent));
    }

    @Test
    public void getExistIdTest() {
        var id = feedNewsService.createOrUpdate(defaultFeedNews, emptyContent).getId();

        var feedNews = feedNewsService.getById(id);

        Assertions.assertNotNull(feedNews);
    }

    @Test
    public void getNotExistIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> feedNewsService.getById(1));
    }

    @Test
    public void isExistTrueTest() {
        var id = feedNewsService.createOrUpdate(defaultFeedNews, emptyContent).getId();

        var isExist = feedNewsService.isFeedNewsExists(id);

        Assertions.assertTrue(isExist);
    }

    @Test
    public void isExistFalseTest() {
        var isExist = feedNewsService.isFeedNewsExists(1);

        Assertions.assertFalse(isExist);
    }

    @Test
    public void getFeedNewsPageTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var pageRequest = PageRequest.of(0, 1);

        var feedNews = feedNewsService.getFeedNewsPage(pageRequest);

        Assertions.assertEquals(1, feedNews.getTotalElements());
        Assertions.assertEquals(1, feedNews.getTotalPages());
        Assertions.assertEquals(defaultFeedNews, feedNews.getContent().get(0));
    }

    @Test
    public void getFeedNewsEmptyPageTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var pageRequest = PageRequest.of(1, 1);

        var feedNews = feedNewsService.getFeedNewsPage(pageRequest);

        Assertions.assertEquals(1, feedNews.getTotalElements());
        Assertions.assertEquals(1, feedNews.getTotalPages());
        Assertions.assertEquals(0, feedNews.getContent().size());
    }

    @Test
    public void getFeedNewsPageCorrectOrderTest() {
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        defaultFeedNews.setId(null);
        defaultFeedNews.setName(defaultFeedNews.getName() + "2");
        feedNewsService.createOrUpdate(defaultFeedNews, emptyContent);
        var pageRequest = PageRequest.of(0, 2);

        var feedNews = feedNewsService.getFeedNewsPage(pageRequest);

        Assertions.assertEquals(2, feedNews.getTotalElements());
        Assertions.assertEquals(1, feedNews.getTotalPages());
        for (int i = 0; i < feedNews.getSize() - 1; i++) {
            Assertions.assertEquals(1, feedNews.getContent().get(i).getName().compareTo(feedNews.getContent().get(i + 1).getName()));
        }
    }

    @Test
    public void deleteFeedNewsTest() {
        var id = feedNewsService.createOrUpdate(defaultFeedNews, emptyContent).getId();

        Assertions.assertNotNull(feedNewsService.getById(id));
        Assertions.assertDoesNotThrow(() -> feedNewsService.delete(id));
        Assertions.assertThrows(EntityNotFoundException.class, () -> feedNewsService.getById(id));
    }

    @Test
    public void deleteDontExistFeedNewsTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> feedNewsService.delete(1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> feedNewsService.getById(1));
    }
}
