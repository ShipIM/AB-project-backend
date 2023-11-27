package com.example.repository;

import com.example.model.entity.Subject;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long>, PagingAndSortingRepository<Subject, Long> {
    @Query("select * from subject_jn " +
            "where course_id = :course " +
            "order by :sort limit :page_size offset :page_number * :page_size")
    List<Subject> findAllByCourseId(@Param("course") long courseId,
                                    @Param("sort") String sort,
                                    @Param("page_size") long pageSize,
                                    @Param("page_number") long pageNumber);

    @Query("select count(*) from subject_jn " +
            "where course_id = :course")
    long countAllByCourseId(@Param("course") long courseId);

}
