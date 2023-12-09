package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.*;
import com.example.model.enumeration.ResourceType;
import com.example.repository.*;
import com.example.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public class CommentServiceTests extends BaseTestClass {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private FeedNewsRepository feedNewsRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    private static long resourceId;

    private static long feedNewsId;

    private CommentEntity defaultComment;

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
        commentRepository.deleteAll();
        commentRepository.deleteAllFeedComments();
        commentRepository.deleteAllResourceComments();

        defaultComment = new CommentEntity();
        defaultComment.setAuthorId(1L);
        defaultComment.setText("text");
        defaultComment.setAnonymous(false);
        setup();
    }

    @Test
    public void createOnlyOneAutoDealerTest() {
        commentService.createOrUpdate(defaultComment);

        var subjectsCount = subjectRepository.count();

        Assertions.assertEquals(1, subjectsCount);
    }

    @Test
    public void createSetCreateDateTest() {
        var comment = commentService.createOrUpdate(defaultComment);

        Assertions.assertNotNull(comment.getCreatedDate());
    }


    @Test
    public void createCorrectAutoDealerTest() {
        var createSubject = commentService.createOrUpdate(defaultComment);
        var repositorySubject = commentService.getById(createSubject.getId());

        defaultComment.setId(createSubject.getId());

        Assertions.assertEquals(defaultComment, repositorySubject);
        Assertions.assertEquals(defaultComment, createSubject);
    }

    @Test
    public void createIncorrectAuthorIdTest() {
        defaultComment.setAuthorId(2L);
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.createOrUpdate(defaultComment));
    }

    @Test
    public void createResourceCommentSuccessTest() {
        var createSubject = commentService.createResourceComment(defaultComment, resourceId);

        Assertions.assertEquals(defaultComment, createSubject);
        Assertions.assertEquals(1, commentRepository.count());
        Assertions.assertEquals(1, commentRepository.countResourceComments());
        Assertions.assertEquals(0, commentRepository.countFeedNewsComments());
    }

    @Test
    public void createResourceCommentExceptionTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.createResourceComment(defaultComment, 1L));

        Assertions.assertEquals(0, commentRepository.count());
        Assertions.assertEquals(0, commentRepository.countResourceComments());
        Assertions.assertEquals(0, commentRepository.countFeedNewsComments());
    }

    @Test
    public void createFeedCommentSuccessTest() {
        var createSubject = commentService.createFeedNewsComment(defaultComment, feedNewsId);

        Assertions.assertEquals(defaultComment, createSubject);
        Assertions.assertEquals(1, commentRepository.count());
        Assertions.assertEquals(0, commentRepository.countResourceComments());
        Assertions.assertEquals(1, commentRepository.countFeedNewsComments());
    }

    @Test
    public void createFeedCommentExceptionTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.createFeedNewsComment(defaultComment, 1L));

        Assertions.assertEquals(0, commentRepository.count());
        Assertions.assertEquals(0, commentRepository.countResourceComments());
        Assertions.assertEquals(0, commentRepository.countFeedNewsComments());
    }

    @Test
    public void createThreadCommentSuccessTest() {
        var cid = commentService.createFeedNewsComment(defaultComment, feedNewsId).getId();
        defaultComment.setId(null);
        var createSubject = commentService.createThreadComment(defaultComment, cid);

        Assertions.assertEquals(defaultComment, createSubject);
        Assertions.assertEquals(2, commentRepository.count());
        Assertions.assertEquals(0, commentRepository.countResourceComments());
        Assertions.assertEquals(2, commentRepository.countFeedNewsComments());
    }

    @Test
    public void createThreadCommentExceptionTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.createThreadComment(defaultComment, 1L));

        Assertions.assertEquals(0, commentRepository.count());
        Assertions.assertEquals(0, commentRepository.countResourceComments());
        Assertions.assertEquals(0, commentRepository.countFeedNewsComments());
    }

    @Test
    public void getExistIdTest() {
        var id = commentService.createOrUpdate(defaultComment).getId();

        var subject = commentService.getById(id);

        Assertions.assertNotNull(subject);
    }

    @Test
    public void getNotExistIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.getById(1));
    }

    @Test
    public void getCommentByResourceIdSuccessTest() {
        commentService.createResourceComment(defaultComment, resourceId);
        var pageRequest = PageRequest.of(0, 1);

        var subjects = commentService.getCommentsByResource(resourceId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentsByResourceAnonymCommentTest() {
        defaultComment.setAnonymous(true);
        commentService.createResourceComment(defaultComment, resourceId);
        var pageRequest = PageRequest.of(0, 1);
        defaultComment.setAuthorId(0L);

        var subjects = commentService.getCommentsByResource(resourceId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentByResourceIdEmptyPageTest() {
        commentService.createResourceComment(defaultComment, resourceId);
        var pageRequest = PageRequest.of(1, 1);

        var subjects = commentService.getCommentsByResource(resourceId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(0, subjects.getContent().size());
    }

    @Test
    public void getCommentByResourceIdCorrectOrderTest() {
        commentService.createResourceComment(defaultComment, resourceId);
        defaultComment.setId(null);
        commentService.createResourceComment(defaultComment, resourceId);
        var pageRequest = PageRequest.of(0, 2);

        var subjects = commentService.getCommentsByResource(resourceId, pageRequest);

        Assertions.assertEquals(2, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        for (int i = 0; i < subjects.getSize() - 1; i++) {
            Assertions.assertEquals(-1, subjects.getContent().get(i).getCreatedDate().compareTo(subjects.getContent().get(i + 1).getCreatedDate()));
        }
    }

    @Test
    public void getCommentByFeedNewsIdSuccessTest() {
        commentService.createFeedNewsComment(defaultComment, feedNewsId);
        var pageRequest = PageRequest.of(0, 1);

        var subjects = commentService.getCommentsByFeedNewsId(feedNewsId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentByFeedNewsIdAnonymCommentTest() {
        defaultComment.setAnonymous(true);
        commentService.createFeedNewsComment(defaultComment, feedNewsId);
        var pageRequest = PageRequest.of(0, 1);
        defaultComment.setAuthorId(0L);

        var subjects = commentService.getCommentsByFeedNewsId(feedNewsId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentByFeedNewsIdEmptyPageTest() {
        commentService.createFeedNewsComment(defaultComment, feedNewsId);
        var pageRequest = PageRequest.of(1, 1);

        var subjects = commentService.getCommentsByFeedNewsId(feedNewsId, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(0, subjects.getContent().size());
    }

    @Test
    public void getCommentByFeedNewsIdCorrectOrderTest() {
        commentService.createFeedNewsComment(defaultComment, feedNewsId);
        defaultComment.setId(null);
        commentService.createFeedNewsComment(defaultComment, feedNewsId);
        var pageRequest = PageRequest.of(0, 2);

        var subjects = commentService.getCommentsByFeedNewsId(feedNewsId, pageRequest);

        Assertions.assertEquals(2, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        for (int i = 0; i < subjects.getSize() - 1; i++) {
            Assertions.assertEquals(-1, subjects.getContent().get(i).getCreatedDate().compareTo(subjects.getContent().get(i + 1).getCreatedDate()));
        }
    }

    @Test
    public void getCommentByThreadParentIdSuccessTest() {
        var cid = commentService.createOrUpdate(defaultComment).getId();
        defaultComment.setId(null);
        commentService.createThreadComment(defaultComment, cid);
        var pageRequest = PageRequest.of(0, 1);

        var subjects = commentService.getCommentsByCommentId(cid, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentByThreadParentIdAnonymCommentTest() {
        var cid = commentService.createOrUpdate(defaultComment).getId();
        defaultComment.setId(null);
        defaultComment.setAnonymous(true);
        commentService.createThreadComment(defaultComment, cid);
        var pageRequest = PageRequest.of(0, 1);
        defaultComment.setAuthorId(0L);

        var subjects = commentService.getCommentsByCommentId(cid, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultComment, subjects.getContent().get(0));
    }

    @Test
    public void getCommentByThreadParentIdEmptyPageTest() {
        var cid = commentService.createOrUpdate(defaultComment).getId();
        defaultComment.setId(null);
        commentService.createThreadComment(defaultComment, cid);
        var pageRequest = PageRequest.of(1, 1);

        var subjects = commentService.getCommentsByCommentId(cid, pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(0, subjects.getContent().size());
    }

    @Test
    public void getCommentByThreadParentIdCorrectOrderTest() {
        var cid = commentService.createOrUpdate(defaultComment).getId();
        defaultComment.setId(null);
        commentService.createThreadComment(defaultComment, cid);
        defaultComment.setId(null);
        commentService.createThreadComment(defaultComment, cid);
        var pageRequest = PageRequest.of(0, 2);

        var subjects = commentService.getCommentsByCommentId(cid, pageRequest);

        Assertions.assertEquals(2, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        for (int i = 0; i < subjects.getSize() - 1; i++) {
            Assertions.assertEquals(-1, subjects.getContent().get(i).getCreatedDate().compareTo(subjects.getContent().get(i + 1).getCreatedDate()));
        }
    }

    @Test
    public void isExistTrueTest() {
        var id = commentService.createOrUpdate(defaultComment).getId();

        var isExist = commentService.isCommentExists(id);

        Assertions.assertTrue(isExist);
    }

    @Test
    public void isExistFalseTest() {
        var isExist = commentService.isCommentExists(1);

        Assertions.assertFalse(isExist);
    }

    @Test
    public void deleteOldNotDeleteCommentTest() {
        defaultComment.setCreatedDate(LocalDateTime.now().minusYears(1));
        var id = commentRepository.save(defaultComment).getId();

        Assertions.assertNotNull(commentRepository.findById(id));
        commentService.deleteOld();
        Assertions.assertDoesNotThrow(() -> commentRepository.findById(id));
    }

    @Test
    public void deleteOldCommentTest() {
        defaultComment.setCreatedDate(LocalDateTime.now().minusYears(2).minusDays(1));
        var id = commentRepository.save(defaultComment).getId();

        Assertions.assertNotNull(commentRepository.findById(id));
        commentService.deleteOld();
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.getById(id));
    }

    @Test
    public void deleteCommentTest() {
        var id = commentService.createOrUpdate(defaultComment).getId();

        Assertions.assertNotNull(commentRepository.findById(id));
        Assertions.assertDoesNotThrow(() -> commentService.delete(id));
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.getById(id));
    }

    @Test
    public void deleteDontExistCommentTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.delete(1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.getById(1L));
    }
}