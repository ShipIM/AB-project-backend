package com.example.repository;

import com.example.model.entity.ContentEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends CrudRepository<ContentEntity, Long>, PagingAndSortingRepository<ContentEntity, Long> {
    @Query("select * from content_jn cj JOIN resource_content rc on rc.content_id = cj.id where rc.resource_id = :resource order by filename")
    List<ContentEntity> findAllByResourceId(@Param("resource") long resourceId);

    @Query("select * from content_jn cj JOIN feed_news_content fc on fc.content_id = cj.id where fc.feed_news_id = :feed_news_id order by filename")
    List<ContentEntity> findAllByFeedNewsId(@Param("feed_news_id") long feedNewsId);

    @Modifying
    @Query("INSERT INTO resource_content (resource_id, content_id) VALUES (:resource_id, :content_id)")
    void createResourceContent(@Param("resource_id") long resourceId, @Param("content_id") long contentId);

    @Modifying
    @Query("INSERT INTO feed_news_content (feed_news_id, content_id) VALUES (:feed_news_id, :content_id)")
    void createFeedNewsContent(@Param("feed_news_id") long feedNewsId, @Param("content_id") long contentId);
}
