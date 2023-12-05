package com.example.repository;

import com.example.model.entity.Subject;
import com.example.model.entity.UserPersonalInfoEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPersonInfoRepository extends CrudRepository<UserPersonalInfoEntity, Long> {
    @Modifying
    @Query("INSERT INTO user_personal_info_jn (id) VALUES (:id)")
    void createEmpty(@Param("id") long id);
}
