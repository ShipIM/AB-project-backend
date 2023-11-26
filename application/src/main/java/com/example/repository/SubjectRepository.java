package com.example.repository;

import com.example.model.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long>, PagingAndSortingRepository<Subject, Long> {
    Page<Subject> findAllByCourseId(long courseId, Pageable pageable);
}
