package com.example.repository;

import com.example.model.entity.Comment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select * from comment_jn " +
            "where resource_id = :resource " +
            "order by created_date limit :page_size offset :page_number * :page_size")
    List<Comment> findAllByResourceId(@Param("resource") long resourceId,
                                      @Param("page_size") long pageSize,
                                      @Param("page_number") long pageNumber);

    @Query("select count(*) from comment_jn where resource_id = :resource")
    long countAllByResourceId(@Param("resource") long resourceId);

    @Modifying
    @Query("delete from comment_jn " +
            "where created_date + INTERVAL '2 year' < CURRENT_DATE")
    void deleteTwoYearOld();
}
