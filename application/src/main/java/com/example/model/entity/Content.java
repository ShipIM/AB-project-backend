package com.example.model.entity;

import com.example.model.enumeration.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content_jn")
public class Content {

    @Id
    @Generated
    private Long id;

    private String filename;

    private byte[] bytes;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    private long size;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resource resource;
}
