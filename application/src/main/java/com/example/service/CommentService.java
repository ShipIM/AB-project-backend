package com.example.service;

import com.example.model.entity.Comment;
import com.example.model.entity.Resource;
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
        Resource resource = resourceService.getResourceById(comment.getResourceId());

        comment.setCreatedDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }
}
