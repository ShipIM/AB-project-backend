package com.example.repository;

import com.example.model.entity.User;
import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select * from user_jn where email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("update user_jn " +
            "set status = :status " +
            "where id = :user")
    void setUserStatus(@Param("user") long userId,
                       @Param("status") Status status);

    @Modifying
    @Query("update user_jn " +
            "set role = :role " +
            "where id = :user")
    void setUserRole(@Param("user") long userId,
                     @Param("role") Role role);

    @Query("select * from user_jn " +
            "where id != 0 " +
            "order by id limit :page_size offset :page_number * :page_size")
    List<User> findAll(@Param("page_size") long pageSize,
                       @Param("page_number") long pageNumber);

    @Query("select count(*) from user_jn where id != 0")
    long countAll();
}
