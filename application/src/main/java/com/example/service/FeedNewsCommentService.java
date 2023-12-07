package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.CommentEntity;
import com.example.model.entity.FeedNewsComment;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedNewsCommentService {
    private final CommentRepository commentRepository;
    private final FeedNewsService feedNewsService;
    private final UserService userService;

    public Page<CommentEntity> getCommentsByFeedNewsId(long feedNewsId, Pageable pageable) {
        long total = commentRepository.countByFeedNewsId(feedNewsId);
        List<FeedNewsComment> comments = commentRepository.findAllByFeedNewsId(
                feedNewsId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        comments = comments.stream().map(this::setAnonymIfIsAnonymousComment).collect(Collectors.toList());

        return new PageImpl<>(comments, pageable, total);
    }

    public CommentEntity create(CommentEntity comment) {
        if (!feedNewsService.isFeedNewsExists(comment.getFeedNewsId())) {
            throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");
        }

        if (!userService.isUserExists(comment.getAuthorId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        comment.setCreatedDate(LocalDateTime.now());

        return commentRepository.save(comment);
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

    private CommentEntity setAnonymIfIsAnonymousComment(CommentEntity comment) {
        if (comment.isAnonymous()) {
            comment.setAuthorId(0L);
        }

        return comment;
    }
}
