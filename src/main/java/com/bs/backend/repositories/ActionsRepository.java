package com.bs.backend.repositories;

import com.bs.backend.domain.Actions;
import com.bs.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {

    //перевірка чи можна по користувачу
    List<Actions> findAllByUsersOrderByDtFromAsc(Users user);

    List<Actions> findAllByOrderByDtFromAsc();
}
