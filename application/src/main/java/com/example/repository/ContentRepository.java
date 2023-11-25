package com.example.repository;

import com.example.model.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends CrudRepository<Content, Long>, PagingAndSortingRepository<Content, Long> {
    Page<Content> findAllByResourceId(long resourceId, Pageable pageable);
}
