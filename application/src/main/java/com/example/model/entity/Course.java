package com.example.model.entity;

import com.example.model.enumeration.DegreeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_ref")
public class Course {

    @Id
    @Generated
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "degree_type")
    private DegreeType degreeType;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;
}
