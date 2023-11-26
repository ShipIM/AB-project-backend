package com.example.repository;

import com.example.model.entity.Content;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends CrudRepository<Content, Long>, PagingAndSortingRepository<Content, Long> {
    List<Content> findAllByResourceId(long resourceId, Sort sort);
}
