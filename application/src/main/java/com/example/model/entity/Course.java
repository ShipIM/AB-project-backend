package com.example.model.entity;

import com.example.model.enumeration.DegreeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_ref")
@SequenceGenerator(name = "course_seq", sequenceName = "course_ref_seq", allocationSize = 1)
public class Course {

    @Id
    @GeneratedValue(generator = "course_seq")
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "degree_type")
    private DegreeType degreeType;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;
}
