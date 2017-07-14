package com.bs.backend.repositories;

import com.bs.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u where u.login = :login and u.is_active = 1")   //and u.is_active = 1 ли корректно
    Users findByLogin(@Param("login") String login);

    @Query("SELECT u FROM Users u where u.login = :email and u.is_active = 1")
    List<Users> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("update Users u set u.password = :password where u.id = :userId")
    void updateUserPassword(@Param("userId") long userId, @Param("password") String password);
}
