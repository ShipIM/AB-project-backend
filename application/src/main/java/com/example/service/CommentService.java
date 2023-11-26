package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Comment;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceService resourceService;

    public Page<Comment> getCommentsByResource(long resourceId, PageRequest pageRequest) {
        var sort = Sort.by(Sort.Order.asc("createdDate"));

        return commentRepository.findAllByResourceId(resourceId, pageRequest.withSort(sort));
    }

    public Comment create(Comment comment) {
        if (!resourceService.isResourceExists(comment.getResourceId()))
            throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");

        comment.setCreatedDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public boolean isCommentExists(long id) {
        return commentRepository.existsById(id);
    }
}
