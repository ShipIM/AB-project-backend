package com.example.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "subject_jn")
public class Subject {
    @Id
    private Long id;

    private String name;

    @Column(value = "course_id")
    private Long courseId;

    public Subject(Subject subject)
    {
        this.id = subject.id;
        this.name = subject.name;
        this.courseId = subject.courseId;
    }

}
