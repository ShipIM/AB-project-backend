package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Comment;
import com.example.repository.CommentRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceService resourceService;
    private final UserRepository userRepository;

    public Page<Comment> getCommentsByResource(long resourceId, Pageable pageable) {
        String sort = "created_date";
        long total = commentRepository.countAllByResourceId(resourceId);
        List<Comment> comments = commentRepository.findAllByResourceId(
                resourceId,
                sort,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(comments, pageable, total);
    }

    public Comment create(Comment comment) {
        if (!resourceService.isResourceExists(comment.getResourceId()))
            throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");

        comment.setAuthor(userRepository.findByEmail(comment.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким email не существует"))
                .getUsername());
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
}
