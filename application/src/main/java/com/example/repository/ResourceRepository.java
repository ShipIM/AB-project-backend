package com.example.repository;

import com.example.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, QuerydslPredicateExecutor<Resource> {
}
