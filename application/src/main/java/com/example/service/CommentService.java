package com.example.service;

import com.example.dto.comment.response.ResponseComment;
import com.example.dto.mapper.CommentMapper;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Comment;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceService resourceService;
    private final UserService userService;

    public Page<Comment> getCommentsByResource(long resourceId, Pageable pageable) {
        long total = commentRepository.countAllByResourceId(resourceId);
        List<Comment> comments = commentRepository.findAllByResourceId(
                resourceId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        comments = comments.stream().map(this::setAnonymIfIsAnonymousComment).collect(Collectors.toList());

        return new PageImpl<>(comments, pageable, total);
    }

    public Comment create(Comment comment) {
        if (!resourceService.isResourceExists(comment.getResourceId())) {
            throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");
        }

        if (!userService.isUserExists(comment.getAuthorId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        comment.setCreatedDate(LocalDateTime.now());

        return commentRepository.save(comment);
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

    private Comment setAnonymIfIsAnonymousComment(Comment comment) {
        if (comment.isAnonymous()) {
            comment.setAuthorId(0L);
        }

        return comment;
    }
}
