package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.exception.IllegalAccessException;
import com.example.model.entity.CommentAudit;
import com.example.model.entity.CommentEntity;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceService resourceService;
    private final FeedNewsService feedNewsService;
    private final UserService userService;
    private final CommentAuditService commentAuditService;

    public CommentEntity getById(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Комментария с таким идентификатором не существует"));
    }

    public Page<CommentEntity> getCommentsByResource(long resourceId, Pageable pageable) {
        long total = commentRepository.countAllByResourceId(resourceId);
        List<CommentEntity> comments = commentRepository.findAllByResourceId(
                resourceId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        comments = comments.stream().map(this::setAnonymIfIsAnonymousComment).collect(Collectors.toList());

        return new PageImpl<>(comments, pageable, total);
    }

    public Page<CommentEntity> getCommentsByFeedNewsId(long feedNewsId, Pageable pageable) {
        long total = commentRepository.countAllByFeedNewsId(feedNewsId);
        List<CommentEntity> comments = commentRepository.findAllByFeedNewsId(
                feedNewsId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        comments = comments.stream().map(this::setAnonymIfIsAnonymousComment).collect(Collectors.toList());

        return new PageImpl<>(comments, pageable, total);
    }

    public Page<CommentEntity> getCommentsByCommentId(long commentId, PageRequest pageable) {
        long total = commentRepository.countAllByCommentId(commentId);
        List<CommentEntity> comments = commentRepository.findAllByCommentId(
                commentId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        comments = comments.stream().map(this::setAnonymIfIsAnonymousComment).collect(Collectors.toList());

        return new PageImpl<>(comments, pageable, total);
    }

    @Transactional
    public CommentEntity createOrUpdate(CommentEntity comment) {
        if (!userService.isUserExists(comment.getAuthorId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        CommentEntity commentEntity = commentRepository.save(comment);
        commentAuditService.createAudit(new CommentAudit(
                null,
                commentEntity.getId(),
                commentEntity.getLastModifiedDate(),
                commentEntity.getText()
        ));

        return commentEntity;
    }

    @Transactional
    public CommentEntity createResourceComment(CommentEntity comment, Long resourceId) {
        if (!resourceService.isResourceExists(resourceId)) {
            throw new EntityNotFoundException("Новости с таким идентификатором не существует");
        }

        var createdComment = createOrUpdate(comment);
        commentRepository.createResourceComment(resourceId, createdComment.getId());

        return createdComment;
    }

    @Transactional
    public CommentEntity createFeedNewsComment(CommentEntity comment, Long feedNewsId) {
        if (!feedNewsService.isFeedNewsExists(feedNewsId)) {
            throw new EntityNotFoundException("Новости с таким идентификатором не существует");
        }

        var createdComment = createOrUpdate(comment);
        commentRepository.createFeedNewsComment(feedNewsId, createdComment.getId());

        return createdComment;
    }

    @Transactional
    public CommentEntity createThreadComment(CommentEntity comment, long parentCommentId) {
        if (!isCommentExists(parentCommentId)) {
            throw new EntityNotFoundException("Комментария с таким идентификатором не существует");
        }

        var createdComment = createOrUpdate(comment);
        commentRepository.createThreadComment(parentCommentId, createdComment.getId());

        return createdComment;
    }

    public void deleteOld() {
        commentRepository.deleteOld();
    }

    public boolean isCommentExists(long id) {
        return commentRepository.existsById(id);
    }

    public void delete(long id) {
        if (isCommentExists(id)) {
            commentRepository.deleteById(id);
            return;
        }

        throw new EntityNotFoundException("Комментария с таким идентификатором не существует");
    }

    public void editComment(CommentEntity comment) {
        CommentEntity retrievedComment = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Комментария с таким идентификатором не существует"));

        if (!retrievedComment.getAuthorId().equals(comment.getAuthorId())) {
            throw new IllegalAccessException("Редактирование комментария другого пользователя невозможно");
        }

        retrievedComment.setText(comment.getText());

        createOrUpdate(retrievedComment);
    }

    private CommentEntity setAnonymIfIsAnonymousComment(CommentEntity comment) {
        if (comment.isAnonymous()) {
            comment.setAuthorId(0L);
        }

        return comment;
    }
}
