package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "content_jn")
public class Content {

    @Id
    private Long id;

    private String filename;

    private byte[] bytes;

    @Column(value = "content_type")
    private String contentType;

    private long size;

    @Column(value = "resource_id")
    private Long resourceId;

}
