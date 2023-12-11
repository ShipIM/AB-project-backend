package com.example.service;

import com.example.model.entity.CommentAudit;
import com.example.repository.CommentAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentAuditService {

    private final CommentAuditRepository commentAuditRepository;

    public CommentAudit createAudit(CommentAudit commentAudit) {
        return commentAuditRepository.save(commentAudit);
    }

    public Page<CommentAudit> getChangeHistory(long commentId, Pageable pageable) {
        long total = commentAuditRepository.countAllByCommentId(commentId);
        List<CommentAudit> comments = commentAuditRepository.getChangeHistory(
                commentId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(comments, pageable, total);
    }
}
