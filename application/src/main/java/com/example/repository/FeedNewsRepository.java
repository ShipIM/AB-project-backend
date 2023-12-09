package com.example.repository;

import com.example.model.entity.FeedNews;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedNewsRepository extends CrudRepository<FeedNews, Long>, PagingAndSortingRepository<FeedNews, Long> {
    @Query("select * from resource_jn " +
            "order by created_date DESC limit :page_size offset :page_number * :page_size")
    List<FeedNews> findAll(@Param("page_size") long pageSize,
                           @Param("page_number") long pageNumber);
}
