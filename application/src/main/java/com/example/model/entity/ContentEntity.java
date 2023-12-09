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
@Table(name = "content_jn")
public class ContentEntity {

    @Id
    private Long id;

    private String filename;

    private byte[] bytes;

    @Column(value = "content_type")
    private String contentType;

    private long size;

    public ContentEntity(ContentEntity entity){
        this.id = entity.id;
        this.filename = entity.filename;
        this.bytes = entity.bytes;
        this.contentType = entity.contentType;
        this.size = entity.size;
    }

}
