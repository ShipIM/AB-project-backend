package com.example.dto.subject.request;

import com.example.model.entity.QSubject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GetSubjectByCourse {
    public static Predicate toPredicate(long courseId) {
        var qSubject = QSubject.subject;
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(qSubject.course.id.eq(courseId));

        return ExpressionUtils.allOf(predicates);
    }
}
