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
@Table(name = "content_jn")
@SequenceGenerator(name = "content_seq", sequenceName = "content_jn_seq", allocationSize = 1)
public class Content {

    @Id
    @GeneratedValue(generator = "content_seq")
    private Long id;

    private String filename;

    private byte[] bytes;

    @Column(name = "content_type")
    private String contentType;

    private long size;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Resource resource;
}
