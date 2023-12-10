package com.example.repository;

import com.example.model.entity.FeedNewsEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedNewsRepository extends CrudRepository<FeedNewsEntity, Long>, PagingAndSortingRepository<FeedNewsEntity, Long> {
    @Query("select * from feed_news_jn " +
            "order by created_date DESC limit :page_size offset :page_number * :page_size")
    List<FeedNewsEntity> findAll(@Param("page_size") long pageSize,
                                 @Param("page_number") long pageNumber);
}
