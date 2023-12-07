package com.example.repository;

import com.example.model.entity.CommentEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    @Query("select * from comment_jn cj " +
            "JOIN resource_comment rc on rc.comment_id = cj.id " +
            "where rc.resource_id = :resource " +
            "order by cj.created_date limit :page_size offset :page_number * :page_size")
    List<CommentEntity> findAllByResourceId(@Param("resource") long resourceId,
                                            @Param("page_size") long pageSize,
                                            @Param("page_number") long pageNumber);

    @Query("select * from comment_jn cj " +
            "JOIN feed_news_comment fc on fc.comment_id = cj.id " +
            "where fc.feed_news_id = :feed_news_id " +
            "order by cj.created_date limit :page_size offset :page_number * :page_size")
    List<CommentEntity> findAllByFeedNewsId(@Param("feed_news_id") long feedNewsId,
                                            @Param("page_size") long pageSize,
                                            @Param("page_number") long pageNumber);

    @Query("select count(*) from comment_jn JOIN resource_comment rc on rc.comment_id = cj.id where rc.resource_id = :resource")
    long countAllByResourceId(@Param("resource") long resourceId);

    @Query("select count(*) from comment_jn JOIN feed_news_comment fc on fc.comment_id = cj.id where fc.feed_news_id = :feed_news_id")
    long countAllByFeedNewsId(@Param("feed_news_id") long feedNewsId);

    @Modifying
    @Query("delete from comment_jn " +
            "where created_date + INTERVAL '2 year' < CURRENT_DATE")
    void deleteOld();

    @Modifying
    @Query("INSERT INTO resource_comment (resource_id, comment_id) VALUES (:resource_id, :comment_id)")
    void createResourceComment(@Param("resource_id") long resourceId, @Param("comment_id") long commentId);

    @Modifying
    @Query("INSERT INTO feed_news_comment (feed_news_id, comment_id) VALUES (:feed_news_id, :comment_id)")
    void createFeedNewsComment(@Param("feed_news_id") long feedNewsId, @Param("comment_id") long commentId);
}
