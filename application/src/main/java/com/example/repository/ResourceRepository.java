package com.example.repository;

import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long>, PagingAndSortingRepository<Resource, Long> {
    Page<Resource> findAllBySubjectIdAndResourceType(long subjectId, ResourceType resourceType, Pageable pageable);
}
