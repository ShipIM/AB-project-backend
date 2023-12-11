package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.CommentAudit;
import com.example.model.entity.CommentEntity;
import com.example.repository.CommentAuditRepository;
import com.example.repository.CommentRepository;
import com.example.service.CommentAuditService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CommentAuditServiceTests extends BaseTestClass {

    @Autowired
    private CommentAuditService commentAuditService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentAuditRepository commentAuditRepository;

    @Test
    public void createCommentAudit() {
        CommentEntity comment = commentRepository.findById(setup())
                .orElseThrow(() -> new EntityNotFoundException("Ошибка при инициализации теста"));
        var commentAudit = new CommentAudit(null, 1L, comment.getLastModifiedDate(), "text");

        var savedAudit = commentAuditService.createAudit(commentAudit);

        Assertions.assertEquals(commentAudit, savedAudit);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "0, 2",
            "1, 3"
    })
    public void getChangesHistoryReturnsCorrectPage(int pageNumber, int pageSize) {
        CommentEntity comment = commentRepository.findById(setup())
                .orElseThrow(() -> new EntityNotFoundException("Ошибка при инициализации теста"));

        List<CommentAudit> savedAudits = saveAuditsList(comment);

        int pageStart = pageNumber * pageSize, pageEnd = pageStart + pageSize;

        Page<CommentAudit> retrievedPage = commentAuditService
                .getChangeHistory(comment.getId(), PageRequest.of(pageNumber, pageSize));

        Assertions.assertAll("Должен возвращать правильную страницу аудитов",
                () -> Assertions.assertEquals(
                        IntStream.range(0, savedAudits.size())
                                .filter(i -> i >= pageStart && i < pageEnd && i < savedAudits.size())
                                .mapToObj(savedAudits::get)
                                .toList().size(),
                        retrievedPage.getContent().size()
                ),
                () -> Assertions.assertEquals(
                        Math.ceil((float) savedAudits.size() / pageSize),
                        retrievedPage.getTotalPages()
                ),
                () -> Assertions.assertEquals(
                        savedAudits.size(),
                        retrievedPage.getTotalElements()
                ));
    }

    @Test
    public void getChangesHistoryReturnsCorrectAudits() {
        CommentEntity comment = commentRepository.findById(setup())
                .orElseThrow(() -> new EntityNotFoundException("Ошибка при инициализации теста"));

        List<CommentAudit> savedAudits = saveAuditsList(comment);

        Page<CommentAudit> retrievedPage = commentAuditService
                .getChangeHistory(comment.getId(), PageRequest.of(0, 3));

        Assertions.assertAll("Должна содержать те же объекты, что были сохранены",
                () -> Assertions.assertEquals(savedAudits, retrievedPage.getContent()));
    }

    private List<CommentAudit> saveAuditsList(CommentEntity comment) {
        Iterable<CommentAudit> iterableAudits = commentAuditRepository.saveAll(
                List.of(
                        new CommentAudit(null, comment.getId(), comment.getLastModifiedDate(), "first"),
                        new CommentAudit(null, comment.getId(), comment.getLastModifiedDate(), "second"),
                        new CommentAudit(null, comment.getId(), comment.getLastModifiedDate(), "third")
                )
        );
        List<CommentAudit> savedAudits = new ArrayList<>();
        iterableAudits.forEach(savedAudits::add);

        return savedAudits;
    }

    private long setup() {
        var defaultComment = new CommentEntity();
        defaultComment.setText("text");
        defaultComment.setAuthorId(1L);

        return commentRepository.save(defaultComment).getId();
    }

    @AfterEach
    private void cleanup() {
        commentRepository.deleteAll();
        commentAuditRepository.deleteAll();
    }
}
