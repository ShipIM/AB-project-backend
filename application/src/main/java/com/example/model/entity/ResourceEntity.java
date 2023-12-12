package com.example.model.entity;

import com.example.model.enumeration.ResourceType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"createdDate"})
@Table(name = "resource_jn")
public class ResourceEntity {

    @Id
    private Long id;

    private String name;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @Column(value = "author_id")
    private Long authorId;

    @Column(value = "resource_type")
    private ResourceType resourceType;

    @Column(value = "subject_id")
    private Long subjectId;

}
