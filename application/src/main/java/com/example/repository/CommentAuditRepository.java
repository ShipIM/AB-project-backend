package com.example.repository;

import com.example.model.entity.CommentAudit;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentAuditRepository extends CrudRepository<CommentAudit, Long> {

    @Query("select * from _aud " +
            "where comment_id = :comment " +
            "order by last_modified_date limit :page_size offset :page_number * :page_size")
    List<CommentAudit> getChangeHistory(@Param("comment") long commentId,
                                        @Param("page_size") long pageSize,
                                        @Param("page_number") long pageNumber);

    @Query("select count(*) from _aud where comment_id = :comment")
    long countAllByCommentId(@Param("comment") long commentId);
}
