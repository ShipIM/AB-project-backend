//package com.example.application.service;
//
//import com.example.application.configuration.BaseTestClass;
//import com.example.exception.EntityNotFoundException;
//import com.example.model.entity.Comment;
//import com.example.repository.CommentRepository;
//import com.example.repository.ResourceRepository;
//import com.example.repository.SubjectRepository;
//import com.example.repository.UserRepository;
//import com.example.service.CommentService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//
//public class CommentServiceTests extends BaseTestClass {
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private static ResourceRepository resourceRepository;
//
//    @Autowired
//    private static SubjectRepository subjectRepository;
//
//    @Autowired
//    private static UserRepository userRepository;
//
//    private Comment defaultComment;
//
//    @BeforeAll
//    static void setup() {
//        userRepository.deleteAll();
//        subjectRepository.deleteAll();
//        resourceRepository.deleteAll();
//
//        userRepository.save();
//        subjectRepository.save();
//        resourceRepository.save();
//    }
//
//    @AfterAll
//    static void clenUp(){
//        userRepository.deleteAll();
//        subjectRepository.deleteAll();
//        resourceRepository.deleteAll();
//    }
//
//    @BeforeEach
//    @AfterEach
//    void init() {
//        commentRepository.deleteAll();
//
//        defaultComment = new Comment();
//        defaultComment.setAuthorId(1L);
//        defaultComment.setResourceId(1L);
//        defaultComment.setText("text");
//        defaultComment.setCreatedDate(LocalDateTime.now());
//    }
//
//    @Test
//    public void createOnlyOneAutoDealerTest() {
//        subjectService.create(defaultSubject);
//
//        var subjectsCount = subjectRepository.count();
//
//        Assertions.assertEquals(1, subjectsCount);
//    }
//
//    @Test
//    public void createCorrectAutoDealerTest() {
//        var createSubject = subjectService.create(defaultSubject);
//        var repositorySubject = subjectService.getById(createSubject.getId());
//
//        defaultSubject.setId(createSubject.getId());
//
//        Assertions.assertEquals(defaultSubject, repositorySubject);
//        Assertions.assertEquals(defaultSubject, createSubject);
//    }
//
//    @Test
//    public void createIncorrectCourseIdTest() {
//        defaultSubject.setCourseId(0L);
//        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.create(defaultSubject));
//    }
//
//    @Test
//    public void isExistTrueTest() {
//        var id = commentService.create(defaultComment).getId();
//
//        var isExist = commentService.isCommentExists(id);
//
//        Assertions.assertTrue(isExist);
//    }
//
//    @Test
//    public void isExistFalseTest() {
//        var isExist = commentService.isCommentExists(1);
//
//        Assertions.assertFalse(isExist);
//    }
//
//    @Test
//    public void deleteCommentTest() {
//        var id = commentService.create(defaultComment).getId();
//
//        Assertions.assertNotNull(commentRepository.findById(id));
//        Assertions.assertDoesNotThrow(() -> commentService.delete(id));
//        Assertions.assertThrows(EntityNotFoundException.class, () -> commentRepository.findById(id));
//    }
//
//    @Test
//    public void deleteDontExistCommentTest() {
//        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.delete(1));
//        Assertions.assertThrows(EntityNotFoundException.class, () -> commentRepository.findById(1L));
//    }
//}
