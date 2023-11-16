package com.example.model.entity;

import com.example.model.enumeration.ResourceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource_jn")
@SequenceGenerator(name = "resource_seq", sequenceName = "resource_jn_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class Resource {

    @Id
    @GeneratedValue(generator = "subject_seq")
    private Long id;

    private String name;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private String author;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Subject subject;
}
