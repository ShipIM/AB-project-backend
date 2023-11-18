package com.example.model.entity;

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
@Table(name = "subject_jn")
@SequenceGenerator(name = "subject_seq", sequenceName = "subject_jn_seq", allocationSize = 1)
public class Subject {

    @Id
    @GeneratedValue(generator = "subject_seq")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Course course;
}
