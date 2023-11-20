package com.example.service;

import com.example.model.entity.Comment;
import com.example.repository.CommentRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ResourceService resourceService;

    public List<Comment> findAll(Predicate predicate) {
        var sort = Sort.by(Sort.Order.asc("creation_date"));
        List<Comment> result = new ArrayList<>();
        commentRepository.findAll(predicate, sort).forEach(result::add);
        return result;
    }

    public Comment create(Comment comment) {
        comment.setResource(resourceService.getResourceById(comment.getResource().getId()));

        return commentRepository.save(comment);
    }
}
