package com.example.repository;

import com.example.model.entity.Content;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends CrudRepository<Content, Long>, PagingAndSortingRepository<Content, Long> {
    @Query("select * from content_jn where resource_id = :resource order by :sort")
    List<Content> findAllByResourceId(@Param("resource") long resourceId,
                                      @Param("sort") String sort);
}
