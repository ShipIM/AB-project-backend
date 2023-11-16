package com.example.model.entity;

import com.example.model.enumeration.DegreeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_jn")
@SequenceGenerator(name = "course_seq", sequenceName = "course_jn_seq", allocationSize = 1)
public class Course {

    @Id
    @GeneratedValue(generator = "course_seq")
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private DegreeType degree;
}
