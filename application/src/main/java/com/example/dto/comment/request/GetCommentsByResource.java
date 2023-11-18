package com.example.dto.comment.request;

import com.example.model.entity.QComment;
import com.example.model.entity.QSubject;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import java.util.ArrayList;
import java.util.List;

public class GetCommentsByResource {
    public static Predicate toPredicate(long resourceId) {
        var qSubject = QComment.comment;
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(qSubject.resource.id.eq(resourceId));

        return ExpressionUtils.allOf(predicates);
    }
}
