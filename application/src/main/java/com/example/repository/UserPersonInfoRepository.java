package com.example.repository;

import com.example.model.entity.UserPersonalInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonInfoRepository extends CrudRepository<UserPersonalInfoEntity, Long> {
}
