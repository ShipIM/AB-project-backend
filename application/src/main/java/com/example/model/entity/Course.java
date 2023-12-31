package com.example.model.entity;

import com.example.model.enumeration.DegreeType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "course_ref")
public class Course {

    @Id
    private Long id;

    private String name;

    @Column(value = "degree_type")
    private DegreeType degreeType;

}
