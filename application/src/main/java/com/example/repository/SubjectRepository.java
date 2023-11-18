package com.example.repository;

import com.example.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, QuerydslPredicateExecutor<Subject> {
}
