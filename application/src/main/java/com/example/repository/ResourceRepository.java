package com.example.repository;

import com.example.model.entity.Resource;
import com.example.model.enumeration.ResourceType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long>, PagingAndSortingRepository<Resource, Long> {
    @Query("select * from resource_jn " +
            "where resource_type = :resource_type and subject_id = :subject " +
            "order by name limit :page_size offset :page_number * :page_size")
    List<Resource> findAllBySubjectIdAndResourceType(@Param("subject") long subjectId,
                                                     @Param("resource_type") ResourceType resourceType,
                                                     @Param("page_size") long pageSize,
                                                     @Param("page_number") long pageNumber);

    @Query("select count(*) from resource_jn " +
            "where resource_type = :resource_type and subject_id = :subject")
    long countAllBySubjectIdAndResourceType(@Param("subject") long subjectId,
                              @Param("resource_type") ResourceType resourceType);

}
