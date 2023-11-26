package com.example.repository;

import com.example.model.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>, PagingAndSortingRepository<Course, Long> {
}
