package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_aud")
public class CommentAudit {

    @Id
    private Long id;

    @Column(value = "comment_id")
    private Long commentId;

    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    private String text;

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object) || object.getClass() != this.getClass()) {
            return false;
        }

        CommentAudit commentAudit = (CommentAudit) object;

        return commentId.equals(commentAudit.getCommentId())
                && lastModifiedDate.equals(commentAudit.getLastModifiedDate())
                && text.equals(commentAudit.getText());
    }
}
